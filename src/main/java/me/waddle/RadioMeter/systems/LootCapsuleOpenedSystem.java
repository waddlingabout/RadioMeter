package me.waddle.RadioMeter.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefChangeSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import me.waddle.RadioMeter.RadioMeter;
import me.waddle.RadioMeter.blocks.LootCapsuleBlockComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class LootCapsuleOpenedSystem extends RefChangeSystem<ChunkStore, LootCapsuleBlockComponent> {

    /**
     * @return
     */
    @NonNullDecl
    @Override
    public ComponentType<ChunkStore, LootCapsuleBlockComponent> componentType() {
        return null;
    }

    /**
     * @param ref
     * @param lootCapsuleBlockComponent
     * @param store
     * @param commandBuffer
     */
    @Override
    public void onComponentAdded(@NonNullDecl Ref<ChunkStore> ref, @NonNullDecl LootCapsuleBlockComponent lootCapsuleBlockComponent, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {

    }

    /**
     * @param ref
     * @param lootCapsuleBlockComponent
     * @param t1
     * @param store
     * @param commandBuffer
     */
    @Override
    public void onComponentSet(@NonNullDecl Ref<ChunkStore> ref, @NullableDecl LootCapsuleBlockComponent lootCapsuleBlockComponent, @NonNullDecl LootCapsuleBlockComponent t1, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {
        //Universe.get().sendMessage(Message.raw("Loot Capsule Opened!"));
    }

    /**
     * @param ref
     * @param lootCapsuleBlockComponent
     * @param store
     * @param commandBuffer
     */
    @Override
    public void onComponentRemoved(@NonNullDecl Ref<ChunkStore> ref, @NonNullDecl LootCapsuleBlockComponent lootCapsuleBlockComponent, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {

    }

    /**
     * @return
     */
    @NullableDecl
    @Override
    public Query<ChunkStore> getQuery() {
        //return Query.and(LootCapsuleBlockComponent.getComponentType());
        return null;
    }
}
