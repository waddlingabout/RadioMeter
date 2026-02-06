package me.waddle.RadioMeter.systems;

import com.google.crypto.tink.internal.Random;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.universe.Universe;
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
                Universe.get().sendMessage(Message.raw("Registered new Lootcapsule"));
                LootCapsuleRegistry registry = RadioMeter.getLootCapsuleRegistry();
                int globalX = x + (worldChunk.getX() * 32);
                int globalZ = z + (worldChunk.getZ() * 32);
                //register loot capsule
                registry.register(globalX,y,globalZ, generator.getFrequency());
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
                Universe.get().sendMessage(Message.raw("Registered new Lootcapsule"));

                //unregister loot capsule
                LootCapsuleRegistry registry = RadioMeter.getLootCapsuleRegistry();
                int globalX = x + (worldChunk.getX() * 32);
                int globalZ = z + (worldChunk.getZ() * 32);
                registry.unregister(globalX,y,globalZ);
            }
        }
    }

    @Override
    public Query getQuery() {
        return Query.and(BlockModule.BlockStateInfo.getComponentType(), LootCapsuleBlockComponent.getComponentType());
    }
}
