package gg.techtide.tidedonations.player;


import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidelib.revamped.abysslibrary.storage.id.Id;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
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
    private List<String> claimedPersonalGoals = new LinkedList<>();
    private List<String> claimedCommunityGoals = new LinkedList<>();

    private transient boolean loaded = false;

    public void load() {
        if (this.loaded) {
            return;
        }

        this.loaded = true;
    }

    public void addClaimedPersonalGoal(final String goal) {
        this.claimedPersonalGoals.add(goal);
    }

    public void addClaimedCommunityGoal(final String goal) {
        this.claimedCommunityGoals.add(goal);
    }

}
