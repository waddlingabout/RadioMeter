package me.waddle.RadioMeter.components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
            .append(new KeyedCodec<>("TargetFrequency", Codec.INTEGER), (o, i) -> o.targetFrequency = i, o -> o.targetFrequency)
            .add()
            .append(new KeyedCodec<>("TargetPosition", Vector3d.CODEC), (o, v) -> o.targetPosition = v, o -> o.targetPosition)
            .add()
            .build();

    private boolean isActive = false;
    private Integer frequency = 0;
    private Integer targetFrequency =-1;
    private Vector3d targetPosition = null;
    private float pingAccumulatorSeconds = 0f;

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

    public Integer getTargetFrequency() {
        return targetFrequency;
    }
    public void setTargetFrequency(Integer targetFrequency) {
        this.targetFrequency = targetFrequency;
    }

    public Vector3d getTargetPosition() {
        return targetPosition;
    }
    public void setTargetPosition(Vector3d targetPosition) {
        this.targetPosition = targetPosition;
    }

    public double getDistanceToTarget(Vector3d playerPosition) {
        if (playerPosition == null || targetPosition == null) {
            return -1d;
        }
        double dx = playerPosition.x - targetPosition.x;
        double dy = playerPosition.y - targetPosition.y;
        double dz = playerPosition.z - targetPosition.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float getPingAccumulatorSeconds() {
        return pingAccumulatorSeconds;
    }

    public void setPingAccumulatorSeconds(float pingAccumulatorSeconds) {
        this.pingAccumulatorSeconds = pingAccumulatorSeconds;
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
        RadioUserComponent clone = new RadioUserComponent(this.isActive, this.frequency);
        clone.targetFrequency = this.targetFrequency;
        clone.pingAccumulatorSeconds = this.pingAccumulatorSeconds;
        return clone;
    }
}
