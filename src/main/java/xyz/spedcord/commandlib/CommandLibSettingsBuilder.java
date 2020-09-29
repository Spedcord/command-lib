package xyz.spedcord.commandlib;

import java.util.function.Supplier;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import xyz.spedcord.commandlib.guild.GuildSettingsProvider;
import xyz.spedcord.commandlib.permission.DefaultPermissionProvider;
import xyz.spedcord.commandlib.permission.PermissionProvider;

public class CommandLibSettingsBuilder {

    private final CommandLibSettings settings = new CommandLibSettings(
            "!",
            false,
            true,
            null,
            new DefaultPermissionProvider(),
            () -> new MessageBuilder().append("Unknown command").build(),
            () -> new MessageBuilder().append("Please wait before executing this command again!").build()
    );

    public CommandLibSettingsBuilder fallbackPrefix(final String prefix) {
        this.settings.setFallbackPrefix(prefix);
        return this;
    }

    public CommandLibSettingsBuilder allowBots(final boolean value) {
        this.settings.setAllowBots(value);
        return this;
    }

    public CommandLibSettingsBuilder settingsProvider(final GuildSettingsProvider settingsProvider) {
        this.settings.setSettingsProvider(settingsProvider);
        return this;
    }

    public CommandLibSettingsBuilder permissionsProvider(final PermissionProvider permissionProvider) {
        this.settings.setPermissionProvider(permissionProvider);
        return this;
    }

    public CommandLibSettingsBuilder sendUnknownCommandMessage(final boolean value) {
        this.settings.setSendUnknownCommandMessage(value);
        return this;
    }

    public CommandLibSettingsBuilder unknownCommandMessage(final Supplier<Message> messageSupplier) {
        this.settings.setUnknownCommandMessage(messageSupplier);
        return this;
    }

    public CommandLibSettings build() {
        if (this.settings.getSettingsProvider() == null) {
            throw new IllegalStateException("Cannot construct settings object without a GuildSettingsProvider");
        }
        return this.settings;
    }

}
