package gg.techtide.tidedonations.module.impl.history.player.storage;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import gg.techtide.tidedonations.module.impl.history.player.HistoryPlayer;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.storage.credentials.Credentials;
import gg.techtide.tidelib.revamped.abysslibrary.storage.sql.SQLStorage;

import java.util.UUID;

/**
 * The sql storage implementation for profile data
 *
 * @author TechTide
 * @since 1.0.0
 */
public final class PlayerSQLStorage extends SQLStorage<UUID, HistoryPlayer> {

    /**
     * Constructs a new PlayerJsonStorage
     *
     * @param module The history module instance
     */
    public PlayerSQLStorage(final HistoryModule module) {
        super(UUID.class, HistoryPlayer.class, "tidedonationshistory", Credentials.from(module.getPlugin().getStorageConfig()));
    }

    /**
     * Constructs a new DonationPlayer
     *
     * @param key The player uuid
     * @return The constructed profile object
     */
    @Override
    public HistoryPlayer constructValue(final UUID key) {
        return new HistoryPlayer(key);
    }

}
