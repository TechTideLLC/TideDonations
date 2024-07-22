package gg.techtide.tidedonations.module.impl.ggwave.event;

import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GGWaveMessageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final boolean firstJoin;

    @Setter
    private GGWaveStyle style;

    public GGWaveMessageEvent(Player player, boolean firstJoin, GGWaveStyle style) {
        this.player = player;
        this.firstJoin = firstJoin;
        this.style = style;
    }

    public Player getPlayer() {
        return player;
    }

    public GGWaveStyle getStyle() {
        return style;
    }

    public boolean isFirstJoin() {
        return firstJoin;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
