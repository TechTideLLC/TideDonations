package gg.techtide.tidedonations.module.impl.ggwave;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.ggwave.listener.ChatListener;
import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidedonations.module.impl.ggwave.style.builder.StyleBuilder;
import gg.techtide.tidedonations.module.impl.ggwave.style.provider.StyleProvider;
import gg.techtide.tidedonations.module.impl.ggwave.style.registry.StyleRegistry;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import gg.techtide.tidelib.patterns.registry.Registry;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.logging.Level;

@Getter
public class GGWaveModule extends DefaultDonationModule {

    private final StyleProvider styleProvider;
    private final Registry<String, GGWaveStyle> styleRegistry;

    private GGWaveStyle defaultStyle;

    @Getter @Setter
    private boolean active;

    public GGWaveModule(final TideDonations plugin) {
        super(plugin, "ggwave");

        this.styleProvider = StyleProvider.getProvider();
        this.styleRegistry = new StyleRegistry();
    }

    @Override
    public void onLoad() {
        this.loadStyles();

        new ChatListener(this);
    }

    @Override
    public void onReload() {
        // Reload the module
    }

    @Override
    public void onDisable() {
        // Disable the module
    }

    private void loadStyles() {

        final GGWaveStyle style = new StyleBuilder.Builder()
                .setName("DEFAULT")
                .setPotentialOptions(this.getConfig().getColoredStringList("styles.default-ggwave-list"))
                .build();

        this.styleRegistry.register(style.getName(), style);

        switch (this.styleProvider) {
            case COSMETICS:
                TideLogger.log(Level.ALL, "TideCosmetics found! Loading the GGWave styles from TideCosmetics");

                // TODO: Add the TideCosmetics implementation

                this.defaultStyle = this.styleRegistry.get(Objects.requireNonNull(this.getConfig().getString("styles.style"))).orElse(style);
                break;
            case DEFAULT:
                this.defaultStyle = style;
                break;
        }
    }

}
