package gg.techtide.tidedonations.module.impl.donationeffects.listener;

import gg.techtide.tidedonations.module.impl.donationeffects.DonationEffectsModule;
import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveStartEvent;
import gg.techtide.tidedonations.module.listener.ModuleListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;

public class DonateListener extends ModuleListener<DonationEffectsModule> {

    private final Map<PotionEffectType, Integer> effects;

    public DonateListener(DonationEffectsModule module) {
        super(module.getPlugin(), module);

        this.effects = Maps.mutable.empty();

        module.getConfig().getStringList("effects").forEach(effect -> {
            final String[] split = effect.split(";");
            this.effects.put(PotionEffectType.getByName(split[0]), Integer.parseInt(split[1]));
        });
    }

    @EventHandler
    public void onGGWaveStart(GGWaveStartEvent event) {
        if (this.getModule().isGgWaveEnabled()) {
            this.applyEffects();
        }
    }

    // TODO: Add Donate command support

    public void applyEffects() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.effects.forEach((effect, level) -> {
                player.addPotionEffect(effect.createEffect(this.getModule().getDuration() * 20, level - 1));
            });
        });
    }
}
