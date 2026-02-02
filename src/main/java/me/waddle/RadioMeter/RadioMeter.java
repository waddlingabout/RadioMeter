package me.waddle.RadioMeter;

import com.hypixel.hytale.component.ComponentRegistry;
import com.hypixel.hytale.component.ComponentRegistryProxy;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import me.waddle.RadioMeter.blocks.LootCapsuleBlockComponent;
import me.waddle.RadioMeter.commands.ExampleCommand;
import me.waddle.RadioMeter.events.ExampleEvent;
import me.waddle.RadioMeter.registries.LootCapsuleRegistry;
import me.waddle.RadioMeter.systems.LootCapsuleInitializer;
import me.waddle.RadioMeter.systems.LootCapsuleOpenedSystem;
import me.waddle.RadioMeter.systems.RadioPingSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class RadioMeter extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private static LootCapsuleRegistry lootCapsuleRegistry = new LootCapsuleRegistry();

    public RadioMeter(@NonNullDecl JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());

    }

    @Override
    protected void setup() {
        super.setup();

        var entityRegistry = getEntityStoreRegistry();


        var lootCapsuleType = getChunkStoreRegistry().registerComponent(
                LootCapsuleBlockComponent.class,
                "LootCapsule_Data",
                LootCapsuleBlockComponent.CODEC
        );
        LootCapsuleBlockComponent.setComponentType(lootCapsuleType);

        var radioUserType = getEntityStoreRegistry().registerComponent(
                RadioUserComponent.class,
                "RadioUser_Data",
                RadioUserComponent.CODEC
        );
        RadioUserComponent.setComponentType(radioUserType);

        getChunkStoreRegistry().registerSystem(new LootCapsuleInitializer());
        //getChunkStoreRegistry().registerSystem(new LootCapsuleOpenedSystem());

        getEntityStoreRegistry().registerSystem(new RadioPingSystem());



        LOGGER.atInfo().log("Setting up plugin " + this.getName());
    }

    public static LootCapsuleRegistry getLootCapsuleRegistry() {
        return lootCapsuleRegistry;
    }

}