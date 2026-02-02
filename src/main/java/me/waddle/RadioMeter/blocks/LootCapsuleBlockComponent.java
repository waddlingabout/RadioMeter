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
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
import com.hypixel.hytale.server.core.modules.block.BlockModule;
import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;

import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class LootCapsuleBlockComponent
    extends ItemContainerState
        //implements Component<ChunkStore>
{
    public static final BuilderCodec<LootCapsuleBlockComponent> CODEC = BuilderCodec.builder(
                    LootCapsuleBlockComponent.class,
                    LootCapsuleBlockComponent::new
            )
            .append(new KeyedCodec<>("Custom", Codec.BOOLEAN), (state, o) -> state.custom = o, state -> state.custom)
            .add()
            .append(new KeyedCodec<>("AllowViewing", Codec.BOOLEAN), (state, o) -> state.allowViewing = o, state -> state.allowViewing)
            .add()
            .append(new KeyedCodec<>("Droplist", Codec.STRING), (state, o) -> state.droplist = o, state -> state.droplist)
            .add()
            .append(new KeyedCodec<>("Marker", WorldMapManager.MarkerReference.CODEC), (state, o) -> state.marker = o, state -> state.marker)
            .add()
            .append(new KeyedCodec<>("ItemContainer", SimpleItemContainer.CODEC), (state, o) -> state.itemContainer = o, state -> state.itemContainer)
            .add()
            .append(new KeyedCodec<>("Opened", Codec.BOOLEAN),
                    (state, value) -> state.opened = value,
                    state -> state.opened)
            .add()
            .append(new KeyedCodec<>("Frequency", Codec.INTEGER),
                    (state, value) -> state.frequency = value,
                    state -> state.frequency)
            .add()
            .build();

    public LootCapsuleBlockComponent() {
    }
    public LootCapsuleBlockComponent(boolean opened) {
        this.opened = opened;
    }

    private boolean opened = true;

    private int frequency;

    public boolean getOpened() {
        return opened;
    }
    private void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public void onOpen(@Nonnull Ref<EntityStore> ref, @Nonnull World world, @Nonnull Store<EntityStore> store) {
        IEventDispatcher<TreasureChestOpeningEvent, TreasureChestOpeningEvent> dispatcher = HytaleServer.get()
                .getEventBus()
                .dispatchFor(TreasureChestOpeningEvent.class, world.getName());
        if (dispatcher.hasListener()) {
            //dispatcher.dispatch(new TreasureChestOpeningEvent(this.objectiveUUID, this.chestUUID, ref, store));
        }



        this.setOpened(true);
    }


    private static ComponentType<ChunkStore, LootCapsuleBlockComponent> TYPE;

    public static void setComponentType(ComponentType<ChunkStore, LootCapsuleBlockComponent> type) {
        TYPE = type;
    }

    public static ComponentType<ChunkStore, LootCapsuleBlockComponent> getComponentType() {
        return TYPE;
    }
    @NullableDecl
    @Override
    public LootCapsuleBlockComponent clone() {
        return new LootCapsuleBlockComponent(this.opened);
    }

}