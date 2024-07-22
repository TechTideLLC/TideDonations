package gg.techtide.tidedonations.listeners;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.revamped.abysslibrary.listener.SimpleTideListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles the join and quit events for SQL Storage and ensuring cross-server stability
 *
 * @author TechTide
 * @since 1.0.0
 */
public final class StorageJoinLeaveListener extends SimpleTideListener<TideDonations> {

    /**
     * Constructs a new StorageJoinLeaveListener
     *
     * @param plugin The tide experience plugin instance
     */
    public StorageJoinLeaveListener(final TideDonations plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final DonationPlayer profile = this.plugin.getStorage().get(player.getUniqueId());

        if (profile.isLoaded()) {
            return;
        }

        profile.load();
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final DonationPlayer profile = this.plugin.getStorage().get(player.getUniqueId());

        if (!profile.isLoaded()) {
            return;
        }

        profile.setLoaded(false);

        this.plugin.getStorage().save(profile);
        this.plugin.getStorage().cache().unregister(player.getUniqueId());
    }
}