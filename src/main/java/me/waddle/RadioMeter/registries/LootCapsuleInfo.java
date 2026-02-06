package me.waddle.RadioMeter.registries;

public record LootCapsuleInfo(int globalX,
                              int globalY,
                              int globalZ,
                              int frequency) {
    public int getX() {
        return  globalX;
    }

    public int getY() {
        return globalY;
    }

    public int getZ() {
        return globalZ;
    }
}
