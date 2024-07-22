package gg.techtide.tidedonations.module.impl.ggwave;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.impl.ggwave.commands.GGWaveCommand;
import gg.techtide.tidedonations.module.impl.ggwave.listener.ChatListener;
import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import gg.techtide.tidedonations.module.impl.ggwave.style.builder.StyleBuilder;
import gg.techtide.tidedonations.module.impl.ggwave.style.provider.StyleProvider;
import gg.techtide.tidedonations.module.impl.ggwave.style.registry.StyleRegistry;
import gg.techtide.tidedonations.module.template.DefaultDonationModule;
import gg.techtide.tidelib.logger.TideLogger;
import gg.techtide.tidelib.patterns.registry.Registry;
import gg.techtide.tidelib.revamped.abysslibrary.command.TideCommand;
import gg.techtide.tidelib.revamped.abysslibrary.text.message.cache.MessageCache;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

@Getter
public class GGWaveModule extends DefaultDonationModule {

    private final StyleProvider styleProvider;
    private final Registry<String, GGWaveStyle> styleRegistry;

    private GGWaveStyle defaultStyle;

    private final TideCommand<TideDonations, CommandSender> ggwaveCommand = new GGWaveCommand(this);

    @Getter @Setter
    private boolean active;

    private final MessageCache messageCache;

    private final long ggwaveDuration;

    private final List<UUID> rewardedPlayers = new LinkedList<>();

    public GGWaveModule(final TideDonations plugin) {
        super(plugin, "ggwave");

        this.styleProvider = StyleProvider.getProvider();
        this.styleRegistry = new StyleRegistry();

        this.messageCache = new MessageCache(this.getConfig());

        this.ggwaveDuration = this.getConfig().getLong("ggwave-duration");

    }

    @Override
    public void onLoad() {
        this.loadMessages(this.messageCache, this.getConfig());

        this.loadStyles();
        this.registerCommands();

        new ChatListener(this);

        TideLogger.console("GGWave module has been loaded!");
    }

    @Override
    public void onReload() {
        this.unregisterCommands();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        this.unregisterCommands();
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

    private void registerCommands() {
        this.ggwaveCommand.register();
    }

    private void unregisterCommands() {
        this.ggwaveCommand.unregister();
    }

}
