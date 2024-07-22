package gg.techtide.tidedonations.player;


import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidelib.revamped.abysslibrary.storage.id.Id;
import lombok.Data;

import java.util.UUID;

/**
 * The player profile object for the donation plugin
 *
 * @author TechTide
 * @since 1.0.0
 */

@Data
public class DonationPlayer {

    @Id
    private final UUID uuid;
    private int amountDonated;
    private String style;

    private transient boolean loaded = false;

    public void load() {
        if (this.loaded) {
            return;
        }

        this.loaded = true;
    }

}
