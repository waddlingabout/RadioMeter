package me.waddle.RadioMeter.commands.RadioCommands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.waddle.RadioMeter.components.RadioUserComponent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class FrequencyCommand extends AbstractPlayerCommand {
    private final RequiredArg<Integer> freqArg;

    public FrequencyCommand() {

        super("frequency", "Set frequency.");
        this.freqArg = withRequiredArg("number", "Number to set freqency to", ArgTypes.INTEGER);
        addAliases("f");
    }
        /**
         * @param commandContext
         * @param store
         * @param ref
         * @param playerRef
         * @param world
         */
    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store,
                           @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef,
                           @NonNullDecl World world) {
        world.execute( () -> {
            var radio = store.getComponent(ref, RadioUserComponent.getComponentType());
            if(radio == null){
                commandContext.sendMessage(Message.raw("No radio"));
                return;
            }
            int freq = freqArg.get(commandContext);
            radio.setFrequency(freq);
            commandContext.sendMessage(Message.raw("Frequency set to " + freq));
        }
        );

    }
}
