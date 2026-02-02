package me.waddle.RadioMeter;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.waddle.RadioMeter.blocks.LootCapsuleBlockComponent;
import me.waddle.RadioMeter.registries.LootCapsuleInfo;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class RadioUserComponent implements Component<EntityStore> {

    @Nonnull
    public static final BuilderCodec<RadioUserComponent> CODEC = BuilderCodec.builder(RadioUserComponent.class, RadioUserComponent::new)
            // Map the integer 'societyNodeId' field.
            // (o, i) -> o.societyNodeId = i: Setter lambda.
            // o -> o.societyNodeId: Getter lambda.
            .append(new KeyedCodec<>("IsActive", Codec.BOOLEAN), (o, i) -> o.isActive = i, o -> o.isActive)
            .add()
            .append(new KeyedCodec<>("Frequency", Codec.INTEGER), (o, i) -> o.frequency = i, o -> o.frequency)
            .add()
            .build();

    private boolean isActive = false;
    private Integer frequency = 0;

    public void setActive(boolean active) {
        isActive = active;
    }
    public boolean isActive() {
        return isActive;
    }
    public Integer getFrequency() {
        return frequency;
    }
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public RadioUserComponent() {}
    public RadioUserComponent(boolean isActive, Integer frequency) {
        this.isActive = isActive;
        this.frequency = frequency;
    }

    private static ComponentType<EntityStore, RadioUserComponent> TYPE;

    public static void setComponentType(ComponentType<EntityStore, RadioUserComponent> type) {
        TYPE = type;
    }

    public static ComponentType<EntityStore, RadioUserComponent> getComponentType() {
        return TYPE;
    }


    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new RadioUserComponent(this.isActive, this.frequency);
    }
}
