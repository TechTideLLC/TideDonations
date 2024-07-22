package gg.techtide.tidedonations.module.impl.history.menus;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import gg.techtide.tidedonations.module.impl.history.player.HistoryPlayer;
import gg.techtide.tidedonations.module.impl.history.wrapper.DonationHistoryWrapper;
import gg.techtide.tidelib.revamped.abysslibrary.PlaceholderReplacer;
import gg.techtide.tidelib.revamped.abysslibrary.builders.ItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.builders.PageBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.menu.TideInventory;
import gg.techtide.tidelib.revamped.abysslibrary.menu.item.MenuItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.menu.templates.TidePageMenu;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DonationHistoryMenu extends TidePageMenu<TideDonations> {

    private final HistoryModule module;

    private final List<Integer> historySlots;

    private final ItemBuilder historyItem;
    private final MenuItemBuilder currentPageItem, nextPageItem, previousPageItem;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a");

    public DonationHistoryMenu(final HistoryModule module) {
        super(module.getPlugin(), module.getConfig(), "menu.");

        this.historySlots = module.getConfig().getIntegerList("menu.history-slots");

        this.historyItem = new ItemBuilder(module.getConfig(), "menu.items.history");

        this.currentPageItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menu.items.current-page"), module.getConfig().getInt("menu.items.current-page.slot"));
        this.nextPageItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menu.items.next-page"), module.getConfig().getInt("menu.items.next-page.slot"));
        this.previousPageItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menu.items.previous-page"), module.getConfig().getInt("menu.items.previous-page.slot"));

        this.module = module;
    }

    @Override
    public void open(Player player, int page) {

        final TideInventory menuBuilder = this.createBase();
        final HistoryPlayer historyPlayer = this.module.getStorage().get(player.getUniqueId());

        final PlaceholderReplacer pageReplacer = new PlaceholderReplacer()
                .addPlaceholder("%page%", String.valueOf(page + 1));

        menuBuilder.registerItem(this.currentPageItem.getSlot(), this.currentPageItem.getItem().parse(pageReplacer));
        menuBuilder.registerItem(this.nextPageItem.getSlot(), this.nextPageItem.getItem().parse(pageReplacer));
        menuBuilder.registerItem(this.previousPageItem.getSlot(), this.previousPageItem.getItem().parse(pageReplacer));

        final PageBuilder<DonationHistoryWrapper> pageBuilder = new PageBuilder<>(this.sortByMostRecent(historyPlayer.getHistory()), this.historySlots.size());

        menuBuilder.registerClickEvent(this.previousPageItem.getSlot(), event -> {
            if (page - 1 > -1) {
                this.open(player, page - 1);
            }
        });

        menuBuilder.registerClickEvent(this.nextPageItem.getSlot(), event -> {
            if (pageBuilder.hasPage(page + 1)) {
                this.open(player, page + 1);
            }
        });

        final List<DonationHistoryWrapper> donationGoals = pageBuilder.getPage(page);
        int index = 0;

        for (final int slot : this.historySlots) {
            if (index >= donationGoals.size()) {
                break;
            }

            final DonationHistoryWrapper historyWrapper = donationGoals.get(index);
            final PlaceholderReplacer replacer = new PlaceholderReplacer()
                    .addPlaceholder("%time%", historyWrapper.getTimestamp())
                    .addPlaceholder("%item%", historyWrapper.getDonationItem())
                    .addPlaceholder("%amount%", String.valueOf(historyWrapper.getPrice()));

            menuBuilder.registerItem(slot, this.historyItem.parse(replacer));
            index++;
        }

        player.openInventory(menuBuilder.build());
    }

    public List<DonationHistoryWrapper> sortByMostRecent(List<DonationHistoryWrapper> donations) {
        List<DonationHistoryWrapper> sortedList = new ArrayList<>(donations);

        sortedList.sort((d1, d2) -> {
            try {
                String timestamp1 = removeSuffix(d1.getTimestamp());
                String timestamp2 = removeSuffix(d2.getTimestamp());

                LocalDateTime dateTime1 = LocalDateTime.parse(timestamp1, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(timestamp2, formatter);

                ZoneId zoneId = ZoneId.of(this.module.getTimezone());
                ZonedDateTime zonedDateTime1 = dateTime1.atZone(zoneId);
                ZonedDateTime zonedDateTime2 = dateTime2.atZone(zoneId);

                return zonedDateTime2.compareTo(zonedDateTime1);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                return 0;
            }
        });

        return sortedList;
    }

    private String removeSuffix(String timestamp) {
        return timestamp.replaceAll("(\\d+)(st|nd|rd|th)", "$1");
    }
}
