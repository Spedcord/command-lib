package xyz.spedcord.commandlib.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.dv8tion.jda.api.JDA;
import xyz.spedcord.commandlib.CommandLibSettings;
import xyz.spedcord.commandlib.command.defaults.HelpCommand;
import xyz.spedcord.commandlib.command.listener.CommandListener;

public class CommandRegistry {

    private final Set<Command> commands = new HashSet<>();
    private final CommandLibSettings settings;

    public CommandRegistry(final CommandLibSettings settings) {
        this.settings = settings;
    }

    public CommandRegistry registerDefaults() {
        return this.register(new HelpCommand(this));
    }

    public CommandRegistry register(final Command command) {
        this.commands.add(command);
        return this;
    }

    public CommandRegistry apply(final JDA jda) {
        jda.addEventListener(new CommandListener(this.settings, this));
        return this;
    }

    public Optional<Command> getCommand(final String name) {
        return this.commands.stream()
                .filter(command -> command.getName().equalsIgnoreCase(name))
                .findAny();
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(this.commands);
    }

    public CommandLibSettings getSettings() {
        return this.settings;
    }

}
