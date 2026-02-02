package me.waddle.RadioMeter.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.util.InventoryHelper;
import me.waddle.RadioMeter.RadioMeter;
import me.waddle.RadioMeter.RadioUserComponent;
import me.waddle.RadioMeter.registries.LootCapsuleInfo;
import me.waddle.RadioMeter.registries.LootCapsuleRegistry;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;



public class RadioPingSystem extends EntityTickingSystem<EntityStore> {


    @Override
    public void tick(float v, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {


        // player has a radio in hand
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);
        PlayerRef playerRef = archetypeChunk.getComponent(i, PlayerRef.getComponentType());
        TransformComponent transformComponent = archetypeChunk.getComponent(i, TransformComponent.getComponentType());


        if(playerRef == null) {
            return;
        }
        if(transformComponent == null) {
            return;
        }
        if(!isHoldingRadio(ref, store)) {
            return;
        }

        RadioUserComponent radioUserComponent = store.getComponent(ref, RadioUserComponent.getComponentType());

        // does the player have a radiocomponent?
        // add if not
        if(radioUserComponent == null) {
            commandBuffer.addComponent(ref, RadioUserComponent.getComponentType(), new RadioUserComponent(false, 0));
            return;
        }

        // is the radio on?
        if(!radioUserComponent.isActive()) {
            return;
        }


        // get the closest capsule within range

            // get the registry entries
        LootCapsuleRegistry registry = RadioMeter.getLootCapsuleRegistry();
        var lootcapsules = registry.snapshot();
            // get closest capsule. use caching? efficiency?

        Vector3d playerPos = transformComponent.getPosition();
        LootCapsuleInfo closestCapsule = this.findClosestCapsule(playerPos);
        if(closestCapsule == null) {
            return;
        }

        // check if frequency matches
            //freq comparison is simple
        if(radioUserComponent.getFrequency() != closestCapsule.frequency()) {
            return;
        }

        // send ping to player every x seconds if all conditions met
            //chat
        playerRef.sendMessage(Message.raw("Ping!"));
    }



    private boolean isHoldingRadio(Ref<EntityStore> ref, Store<EntityStore> store) {

        Player player = store.getComponent(ref, Player.getComponentType());
        if(player == null) {
            return false;
        }
        Inventory inventory = player.getInventory();
        ItemStack inHand = inventory.getItemInHand();
        if(inHand == null || inHand.isEmpty()) {
            return false;
        }
        String radioId = "Radio_Meter";

        return InventoryHelper.matchesItem(radioId, inHand);
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(
                PlayerRef.getComponentType()
        );
    }
    private LootCapsuleInfo findClosestCapsule(Vector3d playerPos) {
        LootCapsuleRegistry registry = RadioMeter.getLootCapsuleRegistry();
        var lootcapsules = registry.snapshot();

        double range = 1500.0; // TODO: pull from config
        double rangeSq = range * range;

        LootCapsuleInfo closest = null;
        double closestDistSq = Double.MAX_VALUE;

        double px = playerPos.x;
        double py = playerPos.y;
        double pz = playerPos.z;

        for (LootCapsuleInfo capsule : lootcapsules) {
            double distSq = distanceSquared(
                    playerPos,
                    new Vector3d(capsule.globalX(), capsule.globalY(), capsule.globalZ())
            );

            // outside detection range
            if (distSq > rangeSq) {
                continue;
            }

            if (distSq < closestDistSq) {
                closestDistSq = distSq;
                closest = capsule;
            }
        }

        return closest;
    }

    private static double distanceSquared(Vector3d a, Vector3d b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double dz = a.z - b.z;
        return dx * dx + dy * dy + dz * dz;
    }
}


