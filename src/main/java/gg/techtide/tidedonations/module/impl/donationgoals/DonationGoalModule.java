package gg.techtide.tidedonations.module.impl.donationgoals;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.donationgoals.commands.DonationGoalCommand;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.DonationGoal;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.template.DefaultDonationGoal;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.type.DonationGoalType;
import gg.techtide.tidedonations.module.impl.donationgoals.listener.DonationListener;
import gg.techtide.tidedonations.module.impl.donationgoals.registry.GoalRegistry;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import gg.techtide.tidelib.patterns.registry.Registry;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class DonationGoalModule extends DefaultDonationModule {

    public long currentBalanceDonated = 0L;

    private final Registry<String, DefaultDonationGoal> communityGoalRegistry, personalGoalRegistry;

    private final TideCommand<TideDonations, Player> donationGoalCommand = new DonationGoalCommand(this);

    public DonationGoalModule(TideDonations plugin) {
        super(plugin, "donationgoal");

        this.calculateAmountDonated();

        this.communityGoalRegistry = new GoalRegistry();
        this.personalGoalRegistry = new GoalRegistry();
    }

    @Override
    public void onLoad() {

        this.loadGoals();
        this.registerCommands();

        new DonationListener(this);

        TideLogger.console("Donation Goal module has been loaded!");
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDisable() {
        this.unregisterCommands();
    }

    private void calculateAmountDonated() {
        this.getPlugin().getStorage().allValues().forEach(profile -> currentBalanceDonated += profile.getAmountDonated());
    }

    private void loadGoals() {

        this.getConfig().getConfigurationSection("goals.personal").getKeys(false).forEach(identifier -> {

            final DefaultDonationGoal goal = new DefaultDonationGoal(DonationGoalType.PERSONAL, this, identifier);
            this.personalGoalRegistry.register(identifier, goal);
        });

        this.getConfig().getConfigurationSection("goals.community").getKeys(false).forEach(identifier -> {

            final DefaultDonationGoal goal = new DefaultDonationGoal(DonationGoalType.COMMUNITY, this, identifier);
            this.communityGoalRegistry.register(identifier, goal);
        });
    }

    public void addDonation(final long amount) {
        this.currentBalanceDonated += amount;
    }

    private void registerCommands() {
        this.donationGoalCommand.register();
    }

    private void unregisterCommands() {
        this.donationGoalCommand.unregister();
    }
}
