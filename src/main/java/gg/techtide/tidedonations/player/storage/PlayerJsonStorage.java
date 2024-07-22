package gg.techtide.tidedonations.player.storage;

import gg.techtide.tidedonations.TideDonations;
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
public final class PlayerJsonStorage extends JsonStorage<UUID, DonationPlayer> {

    /**
     * Constructs a new PlayerJsonStorage
     *
     * @param plugin The tide donation plugin instance
     */
    public PlayerJsonStorage(final TideDonations plugin) {
        super(Files.file("players.json", plugin), DonationPlayer.class, new EclipseRegistry<>());
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
