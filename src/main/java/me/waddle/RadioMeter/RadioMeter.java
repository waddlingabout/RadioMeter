package me.waddle.RadioMeter;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import me.waddle.RadioMeter.commands.ExampleCommand;
import me.waddle.RadioMeter.events.ExampleEvent;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class RadioMeter extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public RadioMeter(@NonNullDecl JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());

    }

    @Override
    protected void setup() {
        super.setup();

        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
        LOGGER.atInfo().log("Setting up plugin " + this.getName());
    }

}