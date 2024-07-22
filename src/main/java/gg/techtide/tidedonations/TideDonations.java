package gg.techtide.tidedonations;

import gg.techtide.tidedonations.api.TideDonationAPI;
import gg.techtide.tidedonations.listeners.StorageJoinLeaveListener;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidedonations.module.impl.donationgoals.DonationGoalModule;
import gg.techtide.tidedonations.module.impl.ggwave.GGWaveModule;
import gg.techtide.tidedonations.module.registry.ModuleRegistry;
import gg.techtide.tidedonations.player.DonationPlayer;
import gg.techtide.tidedonations.player.storage.PlayerJsonStorage;
import gg.techtide.tidedonations.player.storage.PlayerSQLStorage;
import gg.techtide.tidelib.patterns.registry.Registry;
import gg.techtide.tidelib.revamped.abysslibrary.config.TideConfig;
import gg.techtide.tidelib.revamped.abysslibrary.menu.listeners.MenuClickListener;
import gg.techtide.tidelib.revamped.abysslibrary.menu.listeners.MenuCloseListener;
import gg.techtide.tidelib.revamped.abysslibrary.plugin.TidePlugin;
import gg.techtide.tidelib.revamped.abysslibrary.storage.common.CommonStorageImpl;
import gg.techtide.tidelib.revamped.abysslibrary.storage.type.StorageType;
import lombok.Getter;

/**
 * The tide donation plugin main class
 *
 * @author TechTide
 * @since 1.0.0
 */

import java.util.UUID;
import java.util.stream.Stream;

@Getter
public final class TideDonations extends TidePlugin {

    private final TideConfig modulesConfig = this.getYml("modules");

    private final TideConfig storageConfig = this.getYml("storage");
    private final StorageType storageType = StorageType.valueOf(this.storageConfig.getString("storage.type"));

    private final Registry<String, DonationModule> moduleRegistry = new ModuleRegistry();

    private CommonStorageImpl<UUID, DonationPlayer> storage;

    private static TideDonationAPI api;

    @Override
    public String pluginName() {
        return "TideDonations";
    }

    @Override
    public void onEnable() {

        this.loadStorage();
        this.loadDefaultModules();

        TideDonations.api = new TideDonationAPI(this);
    }

    @Override
    public void onDisable() {
        this.storage.write();
    }

    private void loadStorage() {

        switch (this.storageType) {
            case JSON: {
                this.storage = new CommonStorageImpl<>(new PlayerJsonStorage(this));
                break;
            }

            case SQL: {
                this.storage = new CommonStorageImpl<>(new PlayerSQLStorage(this));

                new StorageJoinLeaveListener(this);
                break;
            }
        }

    }

    private void loadDefaultModules()  {
        Stream.of(
                new GGWaveModule(this),
                new DonationGoalModule(this)
        ).filter(DonationModule::isEnabled).forEach(module -> {
            module.onLoad();
            this.moduleRegistry.register(module.getName(), module);
        });
    }

    public static TideDonationAPI getAPI() {
        return api;
    }
}
