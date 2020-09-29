package xyz.spedcord.commandlib.command.defaults;

import java.awt.Color;
import java.util.Optional;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.EmbedBuilder;
import xyz.spedcord.commandlib.command.Command;
import xyz.spedcord.commandlib.command.CommandContext;
import xyz.spedcord.commandlib.command.CommandRegistry;
import xyz.spedcord.commandlib.command.annotations.SubCommand;

public class HelpCommand extends Command {

    private final CommandRegistry commandRegistry;

    public HelpCommand(final CommandRegistry commandRegistry) {
        super("help");
        this.commandRegistry = commandRegistry;
    }

    @SubCommand(isDefault = true, args = "[[A-Za-z0-9-_]+]")
    public void handleCommand(final CommandContext context) {
        if (context.getArgs().length > 0) {
            final Optional<Command> command = this.commandRegistry.getCommand(context.getArgs()[0]);
            if (!command.isPresent()) {
                context.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("Command Help")
                        .setDescription("Could not find a command labeled '" + context.getArgs()[0] + "'")
                        .setColor(Color.GRAY)
                        .build()).queue();
                return;
            }

            context.getChannel().sendMessage(command.get().info()).queue();
            return;
        }

        final String commandStr = this.commandRegistry.getCommands().stream()
                .map(command -> command.getName() + (command.getAdditionalLabels().length == 0 ? ""
                        : "[" + String.join(", ", command.getAdditionalLabels()) + "]"))
                .collect(Collectors.joining(", "));
        final int commands = this.commandRegistry.getCommands().size();

        context.getChannel().sendMessage(new EmbedBuilder()
                .setTitle("Command Help")
                .setDescription("There are " + commands + " available commands:\n```" + commandStr + "```")
                .setColor(Color.GRAY)
                .build()).queue();
    }

}
