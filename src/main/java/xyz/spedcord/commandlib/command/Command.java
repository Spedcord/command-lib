package xyz.spedcord.commandlib.command;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public abstract class Command {

    private final String name;
    private final String[] additionalLabels;

    public Command(final String name, final String... additionalLabels) {
        this.name = name;
        this.additionalLabels = additionalLabels;
    }

    protected Command(final String name) {
        this.name = name;
        this.additionalLabels = new String[0];
    }

    public void fallbackHandler(final CommandContext context) {
        context.getChannel().sendMessage("Unhandled command '" + this.name + "'").queue();
    }

    public Message info() {
        return new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setColor(Color.GRAY)
                        .setTitle("Command Info")
                        .setDescription("`" + this.name + "`")
                        .build())
                .build();
    }

    public String getName() {
        return this.name;
    }

    public String[] getAdditionalLabels() {
        return this.additionalLabels;
    }

}
