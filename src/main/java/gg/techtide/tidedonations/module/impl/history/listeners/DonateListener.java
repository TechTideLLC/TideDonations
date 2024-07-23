package gg.techtide.tidedonations.module.impl.history.listeners;

import gg.techtide.tidedonations.module.impl.donate.event.DonateStartEvent;
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
    public void onGGWave(final GGWaveStartEvent ggWaveEvent) {
        final Player player = ggWaveEvent.getPlayer();
        final HistoryPlayer profile = this.getModule().getStorage().get(player.getUniqueId());

        profile.addHistory(DonationHistoryWrapper.build(ggWaveEvent, this.getModule()));
    }

    @EventHandler
    public void onDonate(final DonateStartEvent donateEvent) {
        final Player player = donateEvent.getPlayer();
        final HistoryPlayer profile = this.getModule().getStorage().get(player.getUniqueId());

        profile.addHistory(DonationHistoryWrapper.build(donateEvent, this.getModule()));
    }
}
