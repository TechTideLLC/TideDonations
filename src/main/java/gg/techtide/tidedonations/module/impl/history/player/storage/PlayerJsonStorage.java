package gg.techtide.tidedonations.module.impl.history.player.storage;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import gg.techtide.tidedonations.module.impl.history.player.HistoryPlayer;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidelib.patterns.registry.impl.EclipseRegistry;
import gg.techtide.tidelib.revamped.abysslibrary.storage.json.JsonStorage;
import gg.techtide.tidelib.revamped.abysslibrary.utils.file.Files;

import java.util.UUID;

/**
 * The json storage implementation for profile data
 *
 * @author TechTide
 * @since 1.0.0
 */
public final class PlayerJsonStorage extends JsonStorage<UUID, HistoryPlayer> {

    /**
     * Constructs a new PlayerJsonStorage
     *
     * @param module The history module instance
     */
    public PlayerJsonStorage(final HistoryModule module) {
        super(Files.file("history.json", module.getPlugin()), HistoryPlayer.class, new EclipseRegistry<>());
    }

    /**
     * Constructs a new HistoryPlayer
     *
     * @param key The player uuid
     * @return The constructed profile object
     */
    @Override
    public HistoryPlayer constructValue(final UUID key) {
        return new HistoryPlayer(key);
    }

}
