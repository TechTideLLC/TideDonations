package gg.techtide.tidedonations.module.template;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidelib.revamped.abysslibrary.config.TideConfig;
import lombok.Getter;

/**
 * The class to create modules based on configuration values;
 *
 * @since 1.0.0
 */

public abstract class DefaultDonationModule implements DonationModule {

   private final TideDonations plugin;
   private final String name;

   private final TideConfig config;

   public DefaultDonationModule(final TideDonations plugin, final String name) {
       this.plugin = plugin;
       this.name = name;

       this.config = this.plugin.getYml("modules/" + this.name);
   }

    @Override
    public TideDonations getPlugin() {
        return this.plugin;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public TideConfig getConfig() {
        return this.config;
    }

    @Override
    public boolean isEnabled() {
        return this.plugin.getModulesConfig().getBoolean(this.name);
    }
}
