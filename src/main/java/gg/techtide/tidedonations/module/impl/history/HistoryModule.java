package gg.techtide.tidedonations.module.impl.history;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.history.commands.DonationHistoryCommand;
import gg.techtide.tidedonations.module.impl.history.listeners.DonateListener;
import gg.techtide.tidedonations.module.impl.history.listeners.StorageJoinLeaveListener;
import gg.techtide.tidedonations.module.impl.history.player.HistoryPlayer;
import gg.techtide.tidedonations.module.impl.history.player.storage.PlayerJsonStorage;
import gg.techtide.tidedonations.module.impl.history.player.storage.PlayerSQLStorage;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.storage.common.CommonStorageImpl;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class HistoryModule extends DefaultDonationModule {

    private CommonStorageImpl<UUID, HistoryPlayer> storage;

    private final TideCommand<TideDonations, Player> historyCommand = new DonationHistoryCommand(this);

    private final String timezone;

    public HistoryModule(TideDonations plugin) {
        super(plugin, "history");

        this.timezone = this.getConfig().getString("timezone");
    }

    @Override
    public void onLoad() {
        this.loadStorage();
        this.registerCommands();

        new StorageJoinLeaveListener(this);
        new DonateListener(this);

        TideLogger.console("Donation History module has been loaded!");
    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDisable() {
        this.storage.write();

        this.unregisterCommands();
    }

    private void loadStorage() {

        switch (this.getPlugin().getStorageType()) {
            case JSON: {
                this.storage = new CommonStorageImpl<>(new PlayerJsonStorage(this));
                break;
            }

            case SQL: {
                this.storage = new CommonStorageImpl<>(new PlayerSQLStorage(this));

                new StorageJoinLeaveListener(this);
                break;
            }
        }
    }

    private void registerCommands() {
        this.historyCommand.register();
    }

    private void unregisterCommands() {
        this.historyCommand.unregister();
    }
}
