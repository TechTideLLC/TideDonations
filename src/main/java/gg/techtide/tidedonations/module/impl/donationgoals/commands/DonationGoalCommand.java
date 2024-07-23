package gg.techtide.tidedonations.module.impl.donationgoals.commands;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidedonations.module.impl.donationgoals.DonationGoalModule;
import gg.techtide.tidedonations.module.impl.donationgoals.goal.type.DonationGoalType;
import gg.techtide.tidedonations.module.impl.donationgoals.menus.DonationGoalCategoryMenu;
import gg.techtide.tidedonations.module.impl.donationgoals.menus.category.DonationGoalMenu;
import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.impl.ggwave.commands.sub.GGWaveStartCommand;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.command.context.CommandContext;
import gg.techtide.tidelib.revamped.abysslibrary.config.wrapper.CommandSettingsWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonationGoalCommand extends TideCommand<TideDonations, Player> {

    /**
     * Constructs a new DonateCommand
     *
     * @param module The GGWave module
     *
     */

    private final DonationGoalModule module;
    private final DonationGoalCategoryMenu menu;

    public DonationGoalCommand(final DonationGoalModule module) {
        super(
                module.getPlugin(),
                new CommandSettingsWrapper(module.getConfig().getCommandName("command"), module.getConfig().getCommandAliases("command")),
                Player.class
        );

        this.ignoreSubCommands(true);

        this.module = module;
        this.menu = new DonationGoalCategoryMenu(module);
    }

    @Override
    public void execute(CommandContext<Player> commandContext) {

        this.menu.open(commandContext.getSender());
    }
}
