package me.waddle.RadioMeter.commands.RadioCommands;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;

public class RadioCommandCollection extends AbstractCommandCollection {

    public RadioCommandCollection () {
        super("radio", "Radio commands");
        addSubCommand(new FrequencyCommand());
        addSubCommand(new ToggleCommand());
        addSubCommand(new TargetCommand());
    }
}
