package de.stevee.Logic.Energy;

public class Energy {
    private static long production = 1;
    private static long stored = 0;
    private static long batteryStorage = 0;


    public static long getStored() {
        return stored;
    }

    public static long getProduction() {
        return production;
    }

    public static void tick() {
        stored = Math.min(stored + production, batteryStorage + production);
   }

    public static void setStored(long stored) {
        Energy.stored = stored;
    }

    public static void subStored(long amount) {
        stored -= amount;
    }
}
