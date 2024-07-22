package gg.techtide.tidedonations.player.storage;

import gg.techtide.tidedonations.TideDonations;
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
public final class PlayerSQLStorage extends SQLStorage<UUID, DonationPlayer> {

    /**
     * Constructs a new PlayerSQLStorage
     *
     * @param plugin The tide donation plugin instance
     */
    public PlayerSQLStorage(final TideDonations plugin) {
        super(UUID.class, DonationPlayer.class, "tidedonations", Credentials.from(plugin.getStorageConfig()));
    }

    /**
     * Constructs a new DonationPlayer
     *
     * @param key The player uuid
     * @return The constructed profile object
     */
    @Override
    public DonationPlayer constructValue(final UUID key) {
        return new DonationPlayer(key);
    }

}
