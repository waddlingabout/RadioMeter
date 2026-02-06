package me.waddle.RadioMeter.registries;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class LootCapsuleRegistry {

    // this whole system has to be changed to understand what world it's working in but for this jam there is no time
    private final Set<LootCapsuleInfo> activeCapsules = ConcurrentHashMap.newKeySet();

    public void register(int x, int y, int z, int frequency) {
        activeCapsules.add(new LootCapsuleInfo(x, y, z, frequency));
    }
    public void unregister(int x, int y, int z) {
        activeCapsules.removeIf(c ->
                c.getX() == x &&
                        c.getY() == y &&
                        c.getZ() == z
        );
    }

    public Set<LootCapsuleInfo> snapshot() {
        return Set.copyOf(activeCapsules);
    }

    public String getAllCapsulesString() {
        var list = snapshot();

        return list.toString();
    }
}
