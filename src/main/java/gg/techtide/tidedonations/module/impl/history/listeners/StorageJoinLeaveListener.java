package gg.techtide.tidedonations.module.impl.history.listeners;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import gg.techtide.tidedonations.module.impl.history.player.HistoryPlayer;
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
     * @param module The history module instance
     */

    private final HistoryModule module;

    public StorageJoinLeaveListener(final HistoryModule module) {
        super(module.getPlugin());

        this.module = module;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final HistoryPlayer profile = this.module.getStorage().get(player.getUniqueId());

        if (profile.isLoaded()) {
            return;
        }

        profile.load();
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final HistoryPlayer profile = this.module.getStorage().get(player.getUniqueId());

        if (!profile.isLoaded()) {
            return;
        }

        profile.setLoaded(false);

        this.module.getStorage().save(profile);
        this.module.getStorage().cache().unregister(player.getUniqueId());
    }
}