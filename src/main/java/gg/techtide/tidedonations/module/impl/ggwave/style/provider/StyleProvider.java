package gg.techtide.tidedonations.module.impl.ggwave.style.provider;

import org.bukkit.Bukkit;

public enum StyleProvider {

    COSMETICS,
    DEFAULT;

    public static StyleProvider getProvider() {

        if (Bukkit.getPluginManager().isPluginEnabled("TideCosmetics")) {
            return COSMETICS;
        }

        return DEFAULT;
    }
}
