package me.waddle.RadioMeter;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.meta.state.ItemContainerState;
import me.waddle.RadioMeter.blocks.LootCapsuleBlockComponent;
import me.waddle.RadioMeter.blocks.LootCapsuleBlockState;
import me.waddle.RadioMeter.commands.DebugCommand;
import me.waddle.RadioMeter.commands.RadioCommands.RadioCommandCollection;
import me.waddle.RadioMeter.components.RadioUserComponent;
import me.waddle.RadioMeter.registries.LootCapsuleRegistry;
import me.waddle.RadioMeter.systems.LootCapsuleInitializer;
import me.waddle.RadioMeter.systems.RadioFindClosestSystem;
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

        var lootCapsuleType = getChunkStoreRegistry().registerComponent(
                LootCapsuleBlockComponent.class,
                "LootCapsule_Component",
                LootCapsuleBlockComponent.CODEC
        );
        LootCapsuleBlockComponent.setComponentType(lootCapsuleType);

        var lootCapsuleState = getBlockStateRegistry().registerBlockState(LootCapsuleBlockState.class, "LootCapsule_State",
                LootCapsuleBlockState.CODEC, ItemContainerState.ItemContainerStateData.class,
                ItemContainerState.ItemContainerStateData.CODEC);


        var radioUserType = getEntityStoreRegistry().registerComponent(
                RadioUserComponent.class,
                "RadioUser_Data",
                RadioUserComponent.CODEC
        );
        RadioUserComponent.setComponentType(radioUserType);


        getChunkStoreRegistry().registerSystem(new LootCapsuleInitializer());
        //getChunkStoreRegistry().registerSystem(new LootCapsuleOpenedSystem());



        getEntityStoreRegistry().registerSystem(new RadioFindClosestSystem());
        getEntityStoreRegistry().registerSystem(new RadioPingSystem());

        getCommandRegistry().registerCommand(new DebugCommand());
        getCommandRegistry().registerCommand(new RadioCommandCollection());


        LOGGER.atInfo().log("Setting up plugin " + this.getName());
    }

    public static LootCapsuleRegistry getLootCapsuleRegistry() {
        return lootCapsuleRegistry;
    }

}