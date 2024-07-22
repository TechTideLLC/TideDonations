package gg.techtide.tidedonations.api;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidedonations.player.DonationPlayer;

import java.util.Optional;
import java.util.UUID;

/**
 * The API class for the donation plugin
 *
 * @author TechTide
 * @since 1.0.0
 */

public class TideDonationAPI {

    private final TideDonations plugin;

    public TideDonationAPI(final TideDonations plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers a module with the plugin
     *
     * @param module The module to register
     */
    public void registerModule(final DonationModule module) {

    }

    /**
     * Gets a player profile from the storage
     *
     * @param playerUUID The player's uuid
     * @return The player's profile. If profile does not exist, a new one will be created.
     */

    public DonationPlayer getProfile(final UUID playerUUID) {
        return this.plugin.getStorage().get(playerUUID);
    }
}
