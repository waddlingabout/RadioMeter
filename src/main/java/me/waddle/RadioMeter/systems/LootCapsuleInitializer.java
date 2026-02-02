package me.waddle.RadioMeter.systems;

import com.google.crypto.tink.internal.Random;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import me.waddle.RadioMeter.RadioMeter;
import me.waddle.RadioMeter.blocks.LootCapsuleBlockComponent;
import me.waddle.RadioMeter.registries.LootCapsuleRegistry;

import javax.annotation.Nonnull;

public class LootCapsuleInitializer extends RefSystem<ChunkStore> {

    @Override
    public void onEntityAdded(@Nonnull Ref ref, @Nonnull AddReason reason, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo) commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
        if (info == null) return;

        LootCapsuleBlockComponent generator = (LootCapsuleBlockComponent) commandBuffer.getComponent(ref, LootCapsuleBlockComponent.getComponentType());
        if (generator != null) {
            int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
            int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
            int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
            int frequency = Random.randInt(20);
            generator.setFrequency(frequency);

            WorldChunk worldChunk = (WorldChunk) commandBuffer.getComponent(info.getChunkRef(), WorldChunk.getComponentType());
            if (worldChunk != null) {
                LootCapsuleRegistry registry = RadioMeter.getLootCapsuleRegistry();
                //register loot capsule
                registry.register(x,y,z, generator.getFrequency());
            }
        }
    }

    @Override
    public void onEntityRemove(@Nonnull Ref ref, @Nonnull RemoveReason reason, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo) commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
        if (info == null) return;

        LootCapsuleBlockComponent generator = (LootCapsuleBlockComponent) commandBuffer.getComponent(ref, LootCapsuleBlockComponent.getComponentType());
        if (generator != null) {
            int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
            int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
            int z = ChunkUtil.zFromBlockInColumn(info.getIndex());

            WorldChunk worldChunk = (WorldChunk) commandBuffer.getComponent(info.getChunkRef(), WorldChunk.getComponentType());
            if (worldChunk != null) {
                //unregister loot capsule
                LootCapsuleRegistry registry = RadioMeter.getLootCapsuleRegistry();
                //register loot capsule
                registry.unregister(x,y,z);
            }
        }
    }

    @Override
    public Query getQuery() {
        return Query.and(BlockModule.BlockStateInfo.getComponentType(), LootCapsuleBlockComponent.getComponentType());
    }
}
