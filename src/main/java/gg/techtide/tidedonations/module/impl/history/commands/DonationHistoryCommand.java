package gg.techtide.tidedonations.module.impl.history.commands;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import gg.techtide.tidedonations.module.impl.history.menus.DonationHistoryMenu;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.command.context.CommandContext;
import gg.techtide.tidelib.revamped.abysslibrary.config.wrapper.CommandSettingsWrapper;
import org.bukkit.entity.Player;

public class DonationHistoryCommand extends TideCommand<TideDonations, Player> {

    /**
     * Constructs a new History Command
     */

    private final DonationHistoryMenu menu;

    public DonationHistoryCommand(final HistoryModule module) {
        super(
                module.getPlugin(),
                new CommandSettingsWrapper(module.getConfig().getCommandName("command"), module.getConfig().getCommandAliases("command")),
                Player.class
        );

        this.ignoreSubCommands(true);

        this.menu = new DonationHistoryMenu(module);
    }

    @Override
    public void execute(CommandContext<Player> commandContext) {

        this.menu.open(commandContext.getSender(), 0);
    }
}
