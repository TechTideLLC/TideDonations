package gg.techtide.tidedonations.module.impl.ggwave.commands;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.impl.ggwave.commands.sub.GGWaveStartCommand;
import gg.techtide.tidelib.logger.TideLogger;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.command.context.CommandContext;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GGWaveCommand extends TideCommand<TideDonations, CommandSender> {

    /**
     * Constructs a new GGWaveCommand
     *
     * @param module The GGWave module
     *
     */

    private final GGWaveModule module;

    public GGWaveCommand(final GGWaveModule module) {
        super(
                module.getPlugin(),
                module.getConfig().getCommandSettings("command"),
                CommandSender.class
        );

        this.register(new GGWaveStartCommand(this, module));

        this.ignoreSubCommands(true);

        this.module = module;
    }

    @Override
    public void execute(final CommandContext<CommandSender> context) {
        if (!(context.getSender() instanceof Player) && context.getArguments().length == 0) {
            TideLogger.console("You need to specify a sub-command to execute this command.");
            return;
        }

        final CommandSender sender = context.getSender();

        if (!sender.hasPermission("tidedonations.ggwave")) {
            this.module.getMessageCache().getMessage("messages.no-permission").send(sender);
            return;
        }

        this.module.getMessageCache().getMessage("messages.admin-help").send(sender);
    }
}
