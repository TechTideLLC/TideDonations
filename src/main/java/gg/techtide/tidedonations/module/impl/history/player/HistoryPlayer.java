package gg.techtide.tidedonations.module.impl.history.player;

import gg.techtide.tidedonations.module.impl.history.wrapper.DonationHistoryWrapper;
import gg.techtide.tidelib.revamped.abysslibrary.storage.id.Id;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
public class HistoryPlayer {

    @Id
    private final UUID uuid;
    private List<DonationHistoryWrapper> history = new LinkedList<>();

    private transient boolean loaded = false;

    public void load() {
        if (this.loaded) {
            return;
        }

        this.loaded = true;
    }

    public void addHistory(DonationHistoryWrapper historyWrapper) {
        this.history.add(historyWrapper);
    }
}
