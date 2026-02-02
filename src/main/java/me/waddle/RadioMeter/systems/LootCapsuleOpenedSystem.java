package me.waddle.RadioMeter.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefChangeSystem;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class LootCapsuleOpenedSystem extends RefChangeSystem {
    /**
     * @return
     */
    @NonNullDecl
    @Override
    public ComponentType componentType() {
        return null;
    }

    /**
     * @param ref
     * @param component
     * @param store
     * @param commandBuffer
     */
    @Override
    public void onComponentAdded(@NonNullDecl Ref ref, @NonNullDecl Component component, @NonNullDecl Store store, @NonNullDecl CommandBuffer commandBuffer) {

    }

    /**
     * @param ref
     * @param component
     * @param t1
     * @param store
     * @param commandBuffer
     */
    @Override
    public void onComponentSet(@NonNullDecl Ref ref, @NullableDecl Component component, @NonNullDecl Component t1, @NonNullDecl Store store, @NonNullDecl CommandBuffer commandBuffer) {

    }

    /**
     * @param ref
     * @param component
     * @param store
     * @param commandBuffer
     */
    @Override
    public void onComponentRemoved(@NonNullDecl Ref ref, @NonNullDecl Component component, @NonNullDecl Store store, @NonNullDecl CommandBuffer commandBuffer) {

    }

    /**
     * @return
     */
    @NullableDecl
    @Override
    public Query getQuery() {
        return null;
    }
}
