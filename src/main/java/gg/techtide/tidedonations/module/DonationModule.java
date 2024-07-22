package gg.techtide.tidedonations.module;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.config.TideConfig;
import gg.techtide.tidelib.revamped.abysslibrary.text.message.cache.MessageCache;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Iterator;
import java.util.UUID;

/**
 * The class to implement modules for the donation plugin.
 *
 * @author TechTide
 * @since 1.0.0
 */

public interface DonationModule {

    String getName();

    TideDonations getPlugin();

    TideConfig getConfig();

    boolean isEnabled();

    void onLoad();
    void onReload();
    void onDisable();

    default void loadMessages(MessageCache cache, FileConfiguration config) {
        config.getConfigurationSection("messages").getKeys(false).forEach(key -> {
            cache.loadMessage("messages." + key);
        });
    }

    default DonationPlayer getProfile(final UUID uuid) {
        return this.getPlugin().getStorage().get(uuid);
    }
}
