package gg.techtide.tidedonations.module.impl.donationeffects;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.donationeffects.listener.DonateListener;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import lombok.Getter;

@Getter
public class DonationEffectsModule extends DefaultDonationModule {

    private final boolean ggWaveEnabled, donateEnabled;

    private final int duration;

    public DonationEffectsModule(TideDonations plugin) {
        super(plugin, "donationeffects");

        this.donateEnabled = this.getConfig().getBoolean("hooks.donate");
        this.ggWaveEnabled = this.getConfig().getBoolean("hooks.ggwave");

        this.duration = this.getConfig().getInt("duration");
    }

    @Override
    public void onLoad() {
        new DonateListener(this);

        TideLogger.console("Donation Effects module has been loaded!");
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDisable() {

    }
}
