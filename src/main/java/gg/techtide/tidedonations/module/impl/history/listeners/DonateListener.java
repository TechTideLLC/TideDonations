package gg.techtide.tidedonations.module.impl.history.listeners;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveStartEvent;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import gg.techtide.tidedonations.module.impl.history.player.HistoryPlayer;
import gg.techtide.tidedonations.module.impl.history.wrapper.DonationHistoryWrapper;
import gg.techtide.tidedonations.module.listener.ModuleListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class DonateListener extends ModuleListener<HistoryModule> {

    public DonateListener(final HistoryModule module) {
        super(module.getPlugin(), module);
    }

    @EventHandler
    public void onDonate(final GGWaveStartEvent event) {
        final Player player = event.getPlayer();
        final HistoryPlayer profile = this.getModule().getStorage().get(player.getUniqueId());

        profile.addHistory(DonationHistoryWrapper.build(event, this.getModule()));
    }
}
