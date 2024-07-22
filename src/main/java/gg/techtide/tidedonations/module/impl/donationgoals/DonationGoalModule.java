package gg.techtide.tidedonations.module.impl.donationgoals;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import lombok.Getter;

@Getter
public class DonationGoalModule extends DefaultDonationModule {

    public long currentBalanceDonated = 0L;

    public DonationGoalModule(TideDonations plugin) {
        super(plugin, "donationgoal");

        this.calculateAmountDonated();
    }

    @Override
    public void onLoad() {
        TideLogger.console("Donation Goal module has been loaded!");
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDisable() {

    }

    private void calculateAmountDonated() {
        this.getPlugin().getStorage().allValues().forEach(profile -> {
            currentBalanceDonated += profile.getAmountDonated();
        });
    }
}
