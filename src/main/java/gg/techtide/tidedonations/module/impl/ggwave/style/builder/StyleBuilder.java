package gg.techtide.tidedonations.module.impl.ggwave.style.builder;

import gg.techtide.tidedonations.module.impl.ggwave.style.GGWaveStyle;

import java.util.List;

public class StyleBuilder implements GGWaveStyle {

    private final String name;
    private final List<String> potentialOptions;

    private StyleBuilder(String name, List<String> potentialOptions) {
        this.name = name;
        this.potentialOptions = potentialOptions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getPotentialOptions() {
        return potentialOptions;
    }

    // Builder class
    public static class Builder {
        private String name;
        private List<String> potentialOptions;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPotentialOptions(List<String> potentialOptions) {
            this.potentialOptions = potentialOptions;
            return this;
        }

        public GGWaveStyle build() {
            if (name == null || potentialOptions == null) {
                throw new IllegalStateException("Name and potential options cannot be null");
            }
            return new StyleBuilder(name, potentialOptions);
        }
    }
}
