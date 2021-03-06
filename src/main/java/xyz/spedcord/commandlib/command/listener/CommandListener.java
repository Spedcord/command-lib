package xyz.spedcord.commandlib.command.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import xyz.spedcord.commandlib.CommandLibSettings;
import xyz.spedcord.commandlib.command.Command;
import xyz.spedcord.commandlib.command.CommandContext;
import xyz.spedcord.commandlib.command.CommandRegistry;
import xyz.spedcord.commandlib.command.annotations.SubCommand;
import xyz.spedcord.commandlib.command.limit.LimitController;
import xyz.spedcord.commandlib.guild.GuildSettings;

public class CommandListener extends ListenerAdapter {

    private final CommandLibSettings settings;
    private final CommandRegistry commandRegistry;

    public CommandListener(final CommandLibSettings settings, final CommandRegistry commandRegistry) {
        this.settings = settings;
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onMessageReceived(@Nonnull final MessageReceivedEvent event) {
        if (!this.settings.isAllowBots() && event.getAuthor().isBot()) {
            return;
        }

        final Message message = event.getMessage();
        final String content = message.getContentRaw();
        final MessageChannel channel = message.getChannel();
        final User user = event.getAuthor();
        final Guild guild = channel instanceof TextChannel ? ((TextChannel) channel).getGuild() : null;
        final boolean isGuild = guild != null;

        if (isGuild) {
            this.handleGuildMessage(message, content, (TextChannel) channel, user, guild);
        } else {
            this.handlePrivateMessage(message, content, (PrivateChannel) channel, user);
        }
    }

    private void handleGuildMessage(final Message message, final String content, final TextChannel channel, final User user, final Guild guild) {
        if (this.settings.getSettingsProvider() == null) {
            return;
        }
        final GuildSettings guildSettings = this.settings.getSettingsProvider().getSettings(guild.getIdLong());

        String prefix = null;
        if (Arrays.stream(guildSettings.getPrefixes()).anyMatch(content::startsWith)) {
            final Optional<String> optional = Arrays.stream(guildSettings.getPrefixes())
                    .filter(content::startsWith)
                    .findAny();
            if (optional.isPresent()) {
                prefix = optional.get();
            }
        } else if (guildSettings.isAllowMention() && content.matches("<@!?\\d+>( )?.*")
                && content.substring(0, content.indexOf(">")).replaceAll("[<@!>]", "").equals(guild.getSelfMember().getId())) {
            prefix = content.substring(0, content.indexOf(">") + 1);
        } else if (content.startsWith(this.settings.getFallbackPrefix()) && guildSettings.getPrefixes().length == 0) {
            prefix = this.settings.getFallbackPrefix();
        }

        if (prefix == null) {
            return;
        }
        final String commandStr = content.substring(prefix.length()).trim();
        this.handleFurther(commandStr, user, guild, channel);
    }

    private void handlePrivateMessage(final Message message, final String content, final PrivateChannel channel, final User user) {
        if (!content.startsWith(this.settings.getFallbackPrefix())) {
            return;
        }

        final String commandStr = content.substring(this.settings.getFallbackPrefix().length()).trim();
        this.handleFurther(commandStr, user, null, channel);
    }

    private void handleFurther(final String commandStr, final User user, final Guild guild, final MessageChannel channel) {
        final String[] commandArr = commandStr.split("\\s+");
        final String commandLabel = commandArr[0];
        final String[] commandArgs = Arrays.copyOfRange(commandArr, 1, commandArr.length);

        final Optional<Command> commandOptional = this.commandRegistry.getCommand(commandLabel);
        if (!commandOptional.isPresent()) {
            if (this.settings.isSendUnknownCommandMessage()) {
                channel.sendMessage(this.settings.getUnknownCommandMessage().get()).queue();
            }
            return;
        }

        final Command command = commandOptional.get();
        final CommandContext context = new CommandContext(commandLabel, commandArgs, command, user, guild, channel, this.settings);

        final Optional<Method> applicableMethod = Arrays.stream(command.getClass().getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> method.getParameterTypes()[0] == CommandContext.class)
                .filter(method -> method.isAnnotationPresent(SubCommand.class))
                .filter(method -> {
                    final SubCommand annotation = method.getAnnotation(SubCommand.class);
                    if (annotation.isDefault()) {
                        return true;
                    }

                    if (!this.settings.getPermissionProvider().hasPermission(annotation.requiredPerms(), context)) {
                        return false;
                    }

                    return this.matchArgs(annotation, Arrays.stream(commandArgs).filter(Objects::nonNull).toArray(String[]::new));
                })
                .sorted(Comparator.comparingInt(value -> value.getAnnotation(SubCommand.class).isDefault() ? 1 : 0))
                .findFirst();
        if (!applicableMethod.isPresent()) {
            command.fallbackHandler(context);
            return;
        }

        final Method method = applicableMethod.get();
        context.setSubCommand(method.getAnnotation(SubCommand.class));

        final String[] flags = context.getSubCommand().parseFlags()
                ? new String[0]
                : Arrays.stream(commandArgs)
                .filter(s -> s.matches("--?[A-Za-z0-9]+"))
                .toArray(String[]::new);
        context.setFlags(flags);
        if (context.getSubCommand().parseFlags()) {
            context.setArgs(Arrays.stream(commandArgs)
                    .filter(s -> !s.matches("--?[A-Za-z0-9]+"))
                    .toArray(String[]::new));
        }

        if (LimitController.isLimited(context)) {
            channel.sendMessage(this.settings.getLimitMessage().get()).queue();
            return;
        }
        LimitController.limit(context);

        try {
            method.setAccessible(true);
            method.invoke(command, context);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private boolean matchArgs(final SubCommand annotation, final String[] commandArgs) {
        if (annotation.args().equals("")) {
            return commandArgs.length == 0;
        }

        final String[] annotationArgs = annotation.args().split("\\s+");

        int n = 0;
        argLoop:
        for (int i = 0; i < annotationArgs.length; i++) {
            String annotationArg = annotationArgs[i];
            if (!(annotationArg.startsWith("<") && annotationArg.endsWith(">"))
                    && !(annotationArg.startsWith("[") && annotationArg.endsWith("]"))) {
                return false;
            }

            if (commandArgs.length <= i + n) {
                if (!(annotationArg.startsWith("[") && annotationArg.endsWith("]"))) {
                    return false;
                }
            }

            annotationArg = annotationArg.substring(1, annotationArg.length() - 1);
            String arg = commandArgs[i + n];
            while (annotation.parseFlags() && arg.matches("--?[A-Za-z0-9]+")) {
                if (i + n == commandArgs.length - 1) {
                    continue argLoop;
                }
                arg = commandArgs[i + ++n];
            }

            if (!annotationArg.matches(arg)) {
                return false;
            }
        }

        return true;
    }

}
