package me.waddle.RadioMeter.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.waddle.RadioMeter.RadioMeter;
import me.waddle.RadioMeter.components.RadioUserComponent;
import me.waddle.RadioMeter.registries.LootCapsuleRegistry;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class DebugCommand extends AbstractPlayerCommand {

    private final OptionalArg<Boolean> listArg;

    public DebugCommand() {
        super("rmdebug", "Shows information about the radio meter mod.");


        this.listArg = withOptionalArg("list", "List all entries in the registry first", ArgTypes.BOOLEAN)
                .addAliases("ls");
    }

    /**
     * @param context
     * @param store
     * @param ref
     * @param playerRef
     * @param world
     */
    @Override
    protected void execute(@NonNullDecl CommandContext context, @NonNullDecl Store<EntityStore> store,
                           @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {


        Boolean list = listArg.get(context);
        //Skriva detta
        //Köpa biobiljetter
        //Sen maps
        if (list != null){
            LootCapsuleRegistry lootCapsuleRegistry = RadioMeter.getLootCapsuleRegistry();
            var snap = lootCapsuleRegistry.snapshot();
            var result = "----------------\n";
            for (var entry : snap){
                result += "Freq: " + entry.frequency() + " Pos: " +
                        " X: "+ entry.globalX() +" Y: "+ entry.globalY() +" Z: "+ entry.globalZ() + "\n";
            }
            result += "\n----------------";
            context.sendMessage(Message.raw(result));
        }
        var radio = store.getComponent(ref, RadioUserComponent.getComponentType());
        if(radio == null){
            context.sendMessage(Message.raw("No radio"));

            return;
        }
        context.sendMessage(Message.raw("Freq: " + radio.getFrequency().toString()));


    }

}
