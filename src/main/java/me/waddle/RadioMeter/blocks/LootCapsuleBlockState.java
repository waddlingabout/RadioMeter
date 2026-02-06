package me.waddle.RadioMeter.blocks;

import com.hypixel.hytale.builtin.adventure.objectives.events.TreasureChestOpeningEvent;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class LootCapsuleBlockState extends ItemContainerState
{
    public static final BuilderCodec<LootCapsuleBlockState> CODEC = BuilderCodec.builder(
                    LootCapsuleBlockState.class,
                    LootCapsuleBlockState::new)
            .append(new KeyedCodec<>("Opened", Codec.BOOLEAN),
                    (state, value) -> state.opened = value,
                    state -> state.opened)
            .add()
            .append(new KeyedCodec<>("Frequency", Codec.INTEGER),
                    (state, value) -> state.frequency = value,
                    state -> state.frequency)
            .add()
            .build();

    public LootCapsuleBlockState() {
    }

    private boolean opened = true;
    public boolean getOpened() {
        return opened;
    }
    private void setOpened(boolean opened) {
        this.opened = opened;
    }

    private int frequency;
    public int getFrequency() {
        return frequency;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public void onOpen(@Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nonnull Store<EntityStore> store) {
        world.execute(() -> {
            var player = store.getComponent(ref, PlayerRef.getComponentType());
            if (player != null) {
                world.sendMessage(Message.raw("Opened by: " + player.getUsername()));
            }
            else {
                world.sendMessage(Message.raw("Loot Capsule Opened!"));
            }
        });

        this.setOpened(true);
    }

}