package gg.techtide.tidedonations.module.impl.donationgoals.menus.category;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.donationgoals.DonationGoalModule;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.DonationGoal;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.template.DefaultDonationGoal;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.type.DonationGoalType;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.PlaceholderReplacer;
import gg.techtide.tidelib.revamped.abysslibrary.builders.ItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.builders.PageBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.menu.TideInventory;
import gg.techtide.tidelib.revamped.abysslibrary.menu.item.MenuItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.menu.templates.TidePageMenu;
import gg.techtide.tidelib.revamped.abysslibrary.utils.Utils;
import org.bukkit.entity.Player;

import java.util.*;

public class DonationGoalMenu extends TidePageMenu<TideDonations> {

    private final DonationGoalModule module;
    private final DonationGoalType type;

    private final List<Integer> rewardSlots;
    private final ItemBuilder claimedItem, unclaimedItem, lockedItem;
    private final MenuItemBuilder currentPageItem, nextPageItem, previousPageItem;

    public DonationGoalMenu(final DonationGoalModule module, final DonationGoalType type) {
        super(module.getPlugin(), module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.");

        this.rewardSlots = module.getConfig().getIntegerList("menus." + type.name().toLowerCase() + "-donation-menu.reward-slots");

        this.claimedItem = new ItemBuilder(module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.items.claimed");
        this.unclaimedItem = new ItemBuilder(module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.items.claimable");
        this.lockedItem = new ItemBuilder(module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.items.locked");

        this.currentPageItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.items.current-page"), module.getConfig().getInt("menus." + type.name().toLowerCase() + "-donation-menu.items.current-page.slot"));
        this.nextPageItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.items.next-page"), module.getConfig().getInt("menus." + type.name().toLowerCase() + "-donation-menu.items.next-page.slot"));
        this.previousPageItem = new MenuItemBuilder(new ItemBuilder(module.getConfig(), "menus." + type.name().toLowerCase() + "-donation-menu.items.previous-page"), module.getConfig().getInt("menus." + type.name().toLowerCase() + "-donation-menu.items.previous-page.slot"));

        this.module = module;
        this.type = type;
    }

    @Override
    public void open(Player player, int page) {

        final TideInventory menuBuilder = this.createBase();
        final DonationPlayer donationPlayer = this.module.getProfile(player.getUniqueId());

        final PageBuilder<DefaultDonationGoal> pageBuilder = new PageBuilder<>(this.getGoals(this.type), this.rewardSlots.size());

        final PlaceholderReplacer replacer = new PlaceholderReplacer()
                .addPlaceholder("%personal-total%", Utils.format(donationPlayer.getAmountDonated()))
                .addPlaceholder("%community-total%", Utils.format(this.module.getCurrentBalanceDonated()))
                .addPlaceholder("%page%", String.valueOf(page + 1));

        menuBuilder.registerItem(this.currentPageItem.getSlot(), this.currentPageItem.getItem().parse(replacer));
        menuBuilder.registerItem(this.nextPageItem.getSlot(), this.nextPageItem.getItem().parse(replacer));
        menuBuilder.registerItem(this.previousPageItem.getSlot(), this.previousPageItem.getItem().parse(replacer));

        menuBuilder.registerItem(this.previousPageItem.getSlot(), this.previousPageItem.getItem().parse(replacer));
        menuBuilder.registerPlayerInventoryClickEvent(this.previousPageItem.getSlot(), event -> {
            if (page - 1 > -1) {
                this.open(player, page - 1);
            }
        });

        menuBuilder.registerItem(this.nextPageItem.getSlot(), this.nextPageItem.getItem().parse(replacer));
        menuBuilder.registerPlayerInventoryClickEvent(this.nextPageItem.getSlot(), event -> {
            if (pageBuilder.hasPage(page + 1)) {
                this.open(player, page + 1);
            }
        });

        menuBuilder.registerItem(this.currentPageItem.getSlot(), this.currentPageItem.getItem().parse(replacer));

        final List<DefaultDonationGoal> donationGoals = pageBuilder.getPage(page);
        int index = 0;

        for (final int slot : this.rewardSlots) {

            if (index >= donationGoals.size()) {
                break;
            }

            final DefaultDonationGoal donationGoal = donationGoals.get(index);

            final boolean hasRequiredAmount = this.hasRequiredAmount(donationGoal, donationPlayer, this.type);
            final boolean hasClaimed = this.hasClaimed(donationGoal, donationPlayer, this.type);

            index++;

            menuBuilder.registerPlayerInventoryClickEvent(slot, event -> {

                if (hasClaimed) return;
                if (!hasRequiredAmount) return;

                donationGoal.claim(player);

                this.open(player, page);
            });

            if (!hasRequiredAmount) {
                menuBuilder.registerItem(slot, this.lockedItem.parse(replacer
                                .addPlaceholder("%goal-name%", donationGoal.getGoalName())
                                .addPlaceholder("%reward%", donationGoal.getRewards())
                                .addPlaceholder("%required-amount%", Utils.format(donationGoal.getRequiredAmount()))));
                continue;
            }

            if (hasClaimed) {
                menuBuilder.registerItem(slot, this.claimedItem.parse(replacer
                        .addPlaceholder("%goal-name%", donationGoal.getGoalName())
                        .addPlaceholder("%reward%", donationGoal.getRewards())
                        .addPlaceholder("%required-amount%", Utils.format(donationGoal.getRequiredAmount()))));
                continue;
            }

            menuBuilder.registerItem(slot, this.unclaimedItem.parse(replacer
                    .addPlaceholder("%goal-name%", donationGoal.getGoalName())
                    .addPlaceholder("%reward%", donationGoal.getRewards())
                    .addPlaceholder("%required-amount%", Utils.format(donationGoal.getRequiredAmount()))));
        }

        player.openInventory(menuBuilder.build());
    }

    public List<DefaultDonationGoal> getGoals(final DonationGoalType type) {
        final List<DefaultDonationGoal> goals = new LinkedList<>();

        switch (type) {
            case COMMUNITY:
                goals.addAll(this.module.getCommunityGoalRegistry().values());
                break;
            case PERSONAL:
                goals.addAll(this.module.getPersonalGoalRegistry().values());
                break;
            default:
                return goals;
        }

        goals.sort(Comparator.comparingDouble(DefaultDonationGoal::getRequiredAmount));

        return goals;
    }

    public boolean hasRequiredAmount(final DonationGoal goal, final DonationPlayer player, final DonationGoalType type) {

        switch (type) {
            case COMMUNITY:
                return player.getAmountDonated() >= goal.getRequiredAmount();
            case PERSONAL:
                return this.module.currentBalanceDonated >= goal.getRequiredAmount();
            default:
                return false;
        }
    }

    public boolean hasClaimed(final DonationGoal goal, final DonationPlayer player, final DonationGoalType type) {

        switch (type) {
            case COMMUNITY:
                return player.getClaimedCommunityGoals().contains(goal.getIdentifier());
            case PERSONAL:
                return player.getClaimedPersonalGoals().contains(goal.getIdentifier());
            default:
                return false;
        }
    }
}
