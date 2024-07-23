package gg.techtide.tidedonations.module.impl.donate;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.donate.commands.DonateCommand;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.text.message.cache.MessageCache;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@Getter
public class DonateModule extends DefaultDonationModule {

    private final TideCommand<TideDonations, CommandSender> donateCommand = new DonateCommand(this);

    private final MessageCache messageCache;

    public DonateModule(final TideDonations plugin) {
        super(plugin, "donate");

        this.messageCache = new MessageCache(this.getConfig());
    }

    @Override
    public void onLoad() {
        this.loadMessages(this.messageCache, this.getConfig());

        this.registerCommands();

        TideLogger.console("Donate module has been loaded!");
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDisable() {
        this.unregisterCommands();
    }

    private void registerCommands() {
        this.donateCommand.register();
    }

    private void unregisterCommands() {
        this.donateCommand.unregister();
    }
}
