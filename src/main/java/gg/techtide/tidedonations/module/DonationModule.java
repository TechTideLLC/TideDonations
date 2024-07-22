package gg.techtide.tidedonations.module;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidelib.revamped.abysslibrary.config.TideConfig;

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
}
