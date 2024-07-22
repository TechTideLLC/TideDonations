package gg.techtide.tidedonations.module.impl.ggwave.listener;

import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveMessageEvent;
import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidedonations.module.listener.ModuleListener;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ChatListener extends ModuleListener<GGWaveModule> {

    private final boolean rewardEnabled;
    private final List<String> commands;

    public ChatListener(final GGWaveModule module) {
        super(module.getPlugin(), module);

        this.rewardEnabled = module.getConfig().getBoolean("reward.enabled");
        this.commands = module.getConfig().getStringList("reward.commands");
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

        GGWaveStyle style = this.getModule().getStyleRegistry().get(donationPlayer.getStyle()).orElse(this.getModule().getDefaultStyle());

        GGWaveMessageEvent messageEvent = new GGWaveMessageEvent(player, false, style);
        this.plugin.getServer().getPluginManager().callEvent(messageEvent);

        event.setMessage(messageEvent.getStyle().getPotentialOptions().get(ThreadLocalRandom.current().nextInt(messageEvent.getStyle().getPotentialOptions().size())));

        if (this.rewardEnabled && !this.getModule().getRewardedPlayers().contains(player.getUniqueId())) {
            this.getModule().getRewardedPlayers().add(player.getUniqueId());
            PlayerUtils.dispatchCommands(player, this.commands);
        }
    }
}
