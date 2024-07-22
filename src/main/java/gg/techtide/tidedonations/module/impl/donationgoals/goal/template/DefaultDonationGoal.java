package gg.techtide.tidedonations.module.impl.donationgoals.goal.template;

import gg.techtide.tidedonations.module.impl.donationgoals.DonationGoalModule;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.DonationGoal;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.type.DonationGoalType;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.builders.ItemBuilder;
import gg.techtide.tidelib.revamped.abysslibrary.utils.PlayerUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class DefaultDonationGoal implements DonationGoal {

    private final DonationGoalType type;
    private final DonationGoalModule module;
    private final String identifier;

    public DefaultDonationGoal(DonationGoalType type, DonationGoalModule module, String identifier) {
        this.type = type;
        this.module = module;
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public int getRequiredAmount() {
        return this.module.getConfig().getInt("goals." + type.name().toLowerCase() + "." + this.identifier + ".required-amount");
    }

    @Override
    public String getGoalName() {
        return this.module.getConfig().getColoredString("goals." + type.name().toLowerCase() + "." + this.identifier + ".name");
    }

    @Override
    public String getRewards() {
        return this.module.getConfig().getColoredString("goals." + type.name().toLowerCase() + "." + this.identifier + ".reward-description");
    }

    @Override
    public List<String> getCommands() {
        return this.module.getConfig().getStringList("goals." + type.name().toLowerCase() + "." + this.identifier + ".commands");
    }

    public void claim(final Player player) {
        PlayerUtils.dispatchCommands(player, this.getCommands());

        final DonationPlayer donationPlayer = this.module.getProfile(player.getUniqueId());

        switch (this.type) {
            case COMMUNITY:
                donationPlayer.addClaimedCommunityGoal(this.getIdentifier());
                break;
            case PERSONAL:
                donationPlayer.addClaimedPersonalGoal(this.getIdentifier());
                break;
        }
    }
}
