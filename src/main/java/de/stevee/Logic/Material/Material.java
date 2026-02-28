package de.stevee.Logic.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Material {
    private final String id;
    private int requiredTier;
    private Set<FLAGS> flags = new HashSet<>();

    public Material(String id) {
        this.id = id;
    }

    public Material addFlag(FLAGS flags) {
        return addFlags(flags);
    }

    public Material setRequiredTier(int requiredTier) {
        this.requiredTier = requiredTier;
        return this;
    }

    public int getRequiredTier() {
        return requiredTier;
    }

    public Material addFlags(FLAGS... flags) {
        this.flags.addAll(Arrays.stream(flags).toList());
        return this;
    }

    public boolean hasFlag(FLAGS flag) {
        return flags.contains(flag);
    }

    public String id() {
        return id;
    }
    public Set<FLAGS> flags() {
        return flags;
    }

    public enum FLAGS {
        HAND_CRAFTABLE(""),
        ORE("Ore"),
        INGOT("Ingot"),
        ROD("Rod"),
        PLATE("Plate"),
        SHEET("Sheet"),
        WIRE("Wire"),
        FINE_WIRE("Fine Wire"),
        FOIL("Foil"),
        DUST("Dust");

        private final String displayName;
        FLAGS(String displayName) {
            this.displayName = displayName;
        }
        public String displayName() {
            return displayName;
        }
    }
}
