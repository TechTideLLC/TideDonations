package gg.techtide.tidedonations.module.impl.donationgoals.menus;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.donationgoals.DonationGoalModule;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.type.DonationGoalType;
import gg.techtide.tidedonations.module.impl.donationgoals.menus.category.DonationGoalMenu;
import gg.techtide.tidelib.revamped.abysslibrary.PlaceholderReplacer;
import gg.techtide.tidelib.revamped.abysslibrary.builders.ItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.menu.TideInventory;
import gg.techtide.tidelib.revamped.abysslibrary.menu.item.MenuItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.menu.templates.TideGenericMenu;
import gg.techtide.tidelib.revamped.abysslibrary.utils.Utils;
import org.bukkit.entity.Player;

public class DonationGoalCategoryMenu extends TideGenericMenu<TideDonations> {

    private final DonationGoalModule module;
    private final MenuItemBuilder communityGoalItem, personalGoalItem;

    public DonationGoalCategoryMenu(final DonationGoalModule module) {
        super(module.getPlugin(), module.getConfig(), "menus.category-menu.");

        this.communityGoalItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menus.category-menu.items.community-goals"), module.getConfig().getInt("menus.category-menu.items.community-goals.slot"));
        this.personalGoalItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menus.category-menu.items.personal-goals"), module.getConfig().getInt("menus.category-menu.items.personal-goals.slot"));

        this.module = module;
    }

    @Override
    public void open(Player player) {

        final TideInventory menuBuilder = this.createBase();

        final PlaceholderReplacer replacer = new PlaceholderReplacer()
                .addPlaceholder("%personal-total%", Utils.format(this.module.getProfile(player.getUniqueId()).getAmountDonated()))
                .addPlaceholder("%community-total%", Utils.format(this.module.getCurrentBalanceDonated()));

        menuBuilder.registerItem(this.communityGoalItem.getSlot(), this.communityGoalItem.getItem().parse(replacer));
        menuBuilder.registerItem(this.personalGoalItem.getSlot(), this.personalGoalItem.getItem().parse(replacer));

        menuBuilder.registerClickEvent(this.communityGoalItem.getSlot(), event -> {
            new DonationGoalMenu(this.module, DonationGoalType.COMMUNITY).open(player, 0);
        });

        menuBuilder.registerClickEvent(this.personalGoalItem.getSlot(), event -> {
            new DonationGoalMenu(this.module, DonationGoalType.PERSONAL).open(player, 0);
        });

        player.openInventory(menuBuilder.build());
    }
}
