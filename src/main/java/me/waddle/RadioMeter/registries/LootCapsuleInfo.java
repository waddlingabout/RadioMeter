package me.waddle.RadioMeter.registries;

import com.hypixel.hytale.math.vector.Vector3d;

public record LootCapsuleInfo(int globalX,
                              int globalY,
                              int globalZ,
                              int frequency) {
    public int getFrequency() {
        return frequency;
    }

    public int getX() {
        return  globalX;
    }

    public int getY() {
        return globalY;
    }

    public int getZ() {
        return globalZ;
    }

    public Vector3d getPosition() {
        return new Vector3d(globalX, globalY, globalZ);
    }
}
