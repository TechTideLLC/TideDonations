package gg.techtide.tidedonations.module.impl.donationgoals.listener;

import gg.techtide.tidedonations.module.impl.donationgoals.DonationGoalModule;
import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveStartEvent;
import gg.techtide.tidedonations.module.listener.ModuleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class DonationListener extends ModuleListener<DonationGoalModule> {

    public DonationListener(DonationGoalModule module) {
        super(module.getPlugin(), module);
    }

    @EventHandler
    public void onGGWaveStart(final GGWaveStartEvent event) {
        final int amount = event.getAmount();
        this.getModule().addDonation(amount);
    }
}
