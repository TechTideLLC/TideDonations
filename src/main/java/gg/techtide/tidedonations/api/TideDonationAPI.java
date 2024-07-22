package gg.techtide.tidedonations.api;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidedonations.player.DonationPlayer;

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
        this.plugin.getModuleRegistry().register(module.getName(), module);
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

    /**
     * Saves a player profile to the storage
     * @param player The player's profile to save
     */

    public void saveProfile(final DonationPlayer player) {
        this.plugin.getStorage().save(player);
    }

    /**
     * Gets a module from the plugin
     * @param name The name of the module
     * @return The module if it exists, otherwise null
     */

    public DonationModule getDonationModule(final String name) {
        return this.plugin.getModuleRegistry().get(name).orElse(null);
    }

    /**
     * Registers a style with the ggwave module
     * @param style The style to register
     */

    public void registerStyle(final GGWaveStyle style) {

        final GGWaveModule module = (GGWaveModule) this.plugin.getModuleRegistry().get("ggwave").get();

        module.getStyleRegistry().register(style.getName(), style);
    }
}
