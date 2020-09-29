package xyz.spedcord.commandlib.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import xyz.spedcord.commandlib.CommandLibSettings;

public class CommandContext {

    private final Command command;
    private final User user;
    private final Guild guild;
    private final MessageChannel channel;
    private final CommandLibSettings settings;
    private final String label;
    private final String[] args;

    public CommandContext(final String label, final String[] args, final Command command, final User user, final Guild guild, final MessageChannel channel, final CommandLibSettings settings) {
        this.label = label;
        this.args = args;
        this.command = command;
        this.user = user;
        this.guild = guild;
        this.channel = channel;
        this.settings = settings;
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getArgs() {
        return this.args;
    }

    public Command getCommand() {
        return this.command;
    }

    public User getUser() {
        return this.user;
    }

    public Member getMember() {
        return (Member) this.getUser();
    }

    public Guild getGuild() {
        return this.guild;
    }

    public MessageChannel getChannel() {
        return this.channel;
    }

    public CommandLibSettings getSettings() {
        return this.settings;
    }

    public boolean isPrivate() {
        return this.getChannel() instanceof PrivateChannel;
    }

    public boolean isGuild() {
        return this.getChannel() instanceof TextChannel;
    }

}
