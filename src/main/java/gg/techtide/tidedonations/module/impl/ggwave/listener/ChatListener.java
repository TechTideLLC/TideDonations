package gg.techtide.tidedonations.module.impl.ggwave.listener;

import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidedonations.module.listener.ModuleListener;
import gg.techtide.tidedonations.player.DonationPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.ThreadLocalRandom;

public class ChatListener extends ModuleListener<GGWaveModule> {

    public ChatListener(final GGWaveModule module) {
        super(module.getPlugin(), module);
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {

        if (!this.getModule().isActive()) {
            return;
        }

        if (!event.getMessage().toLowerCase().contains("gg")) {
            return;
        }

        final Player player = event.getPlayer();
        final DonationPlayer donationPlayer = this.plugin.getStorage().get(player.getUniqueId());

        final GGWaveStyle style = this.getModule().getStyleRegistry().get(donationPlayer.getStyle()).orElse(this.getModule().getDefaultStyle());

        event.setMessage(style.getPotentialOptions().get(ThreadLocalRandom.current().nextInt(style.getPotentialOptions().size())));
    }
}
