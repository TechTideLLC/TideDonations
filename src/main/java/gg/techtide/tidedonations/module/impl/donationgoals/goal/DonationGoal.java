package gg.techtide.tidedonations.module.impl.donationgoals.goal;

import gg.techtide.tidelib.revamped.abysslibrary.builders.ItemBuilder;

import java.util.List;

public interface DonationGoal {

    String getIdentifier();
    int getRequiredAmount();
    String getGoalName();
    String getRewards();
    List<String> getCommands();
}
