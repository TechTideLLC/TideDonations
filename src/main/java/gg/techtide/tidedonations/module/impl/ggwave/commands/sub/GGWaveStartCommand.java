package gg.techtide.tidedonations.module.impl.ggwave.commands.sub;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.impl.ggwave.commands.GGWaveCommand;
import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveEndEvent;
import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveStartEvent;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.PlaceholderReplacer;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.command.context.CommandContext;
import gg.techtide.tidelib.revamped.abysslibrary.command.context.ContextArgumentType;
import gg.techtide.tidelib.revamped.abysslibrary.command.sub.TideSubCommand;
import gg.techtide.tidelib.revamped.abysslibrary.scheduler.impl.SyncScheduler;
import gg.techtide.tidelib.revamped.abysslibrary.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eclipse.collections.api.factory.Sets;

import java.util.stream.Collectors;

/**
 * The take sub command implementation to be attached to the ExperienceCommand
 *
 * @author TechTide
 * @since 1.0.0
 */
public final class GGWaveStartCommand extends TideSubCommand<TideDonations, CommandSender, TideCommand<TideDonations, CommandSender>> {

    /**
     * Constructs a new TakeSubCommand
     *
     * @param command The experience command instance
     */

    private final GGWaveModule module;

    public GGWaveStartCommand(final GGWaveCommand command, final GGWaveModule module) {
        super(command, command.getWrapper().getSubCommands().get("start"));

        this.addTabCompleteArgument(0, ContextArgumentType.AS_PLAYER, Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toSet()));
        this.addTabCompleteArgument(1, ContextArgumentType.AS_INTEGER, Sets.mutable.of("1", "10", "100"));

        this.require(2);

        this.module = module;
    }

    @Override
    public void execute(final CommandContext<CommandSender> context) {
        final CommandSender sender = context.getSender();

        final Player target = context.asPlayer(0);

        if (target == null) {
            this.module.getMessageCache().sendMessage(sender, "messages.invalid-player");
            return;
        }

        final int amount = context.asInt(1);

        if (amount <= 0) {
            this.module.getMessageCache().sendMessage(sender, "messages.invalid-amount");
            return;
        }

        final StringBuilder builder = new StringBuilder();

        for (int i = 2; i < context.getArguments().length; i++) {
            if (!builder.toString().isEmpty()) {
                builder.append(" ");
            }

            builder.append(context.getArguments()[i]);
        }

        final PlaceholderReplacer replacer = new PlaceholderReplacer()
                .addPlaceholder("%player%", target.getName())
                .addPlaceholder("%package%", builder.toString())
                .addPlaceholder("%amount%", Utils.format(amount));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            this.module.getMessageCache().sendMessage(onlinePlayer, "messages.donate", replacer);
        }

        final DonationPlayer targetDonationProfile = this.module.getPlugin().getStorage().get(target.getUniqueId());

        targetDonationProfile.setAmountDonated(targetDonationProfile.getAmountDonated() + amount);

        GGWaveStartEvent startEvent = new GGWaveStartEvent(builder.toString(), target, amount);
        Bukkit.getServer().getPluginManager().callEvent(startEvent);

        if (!this.module.isActive()) {

            this.module.setActive(true);
            this.module.getRewardedPlayers().clear();

            GGWaveEndEvent endEvent = new GGWaveEndEvent(builder.toString(), target, amount);
            Bukkit.getServer().getPluginManager().callEvent(endEvent);

            new SyncScheduler().runLater(() -> module.setActive(false), module.getGgwaveDuration() * 20L);
        }
    }
}
