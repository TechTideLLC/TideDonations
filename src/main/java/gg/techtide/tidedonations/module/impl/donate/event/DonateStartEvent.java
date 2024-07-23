package gg.techtide.tidedonations.module.impl.donate.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DonateStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String info;
    private final Player player;
    private final int amount;

    public DonateStartEvent(String info, Player player, int amount) {
        this.info = info;
        this.player = player;
        this.amount = amount;
    }

    public String getInfo() {
        return info;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
