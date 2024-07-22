package gg.techtide.tidedonations.module.listener;

import gg.techtide.tidedonations.TideDonations;
import gg.techtide.tidedonations.module.DonationModule;
import gg.techtide.tidelib.revamped.abysslibrary.listener.SimpleTideListener;
import lombok.Getter;

@Getter
public abstract class ModuleListener<T extends DonationModule> extends SimpleTideListener<TideDonations> {

    private final T module;

    public ModuleListener(final TideDonations plugin, final T module) {
        super(plugin, true);

        this.module = module;
    }
}
