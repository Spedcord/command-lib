package xyz.spedcord.commandlib;

import java.util.function.Supplier;
import net.dv8tion.jda.api.entities.Message;
import xyz.spedcord.commandlib.guild.GuildSettingsProvider;
import xyz.spedcord.commandlib.permission.PermissionProvider;

public class CommandLibSettings {

    private String fallbackPrefix;
    private boolean allowBots;
    private boolean sendUnknownCommandMessage;
    private GuildSettingsProvider settingsProvider;
    private PermissionProvider permissionProvider;
    private Supplier<Message> unknownCommandMessage;
    private Supplier<Message> limitMessage;

    public CommandLibSettings(final String fallbackPrefix, final boolean allowBots, final boolean sendUnknownCommandMessage, final GuildSettingsProvider settingsProvider, final PermissionProvider permissionProvider, final Supplier<Message> unknownCommandMessage, final Supplier<Message> limitMessage) {
        this.fallbackPrefix = fallbackPrefix;
        this.allowBots = allowBots;
        this.sendUnknownCommandMessage = sendUnknownCommandMessage;
        this.settingsProvider = settingsProvider;
        this.permissionProvider = permissionProvider;
        this.unknownCommandMessage = unknownCommandMessage;
        this.limitMessage = limitMessage;
    }

    public boolean isAllowBots() {
        return this.allowBots;
    }

    void setAllowBots(final boolean allowBots) {
        this.allowBots = allowBots;
    }

    public boolean isSendUnknownCommandMessage() {
        return this.sendUnknownCommandMessage;
    }

    void setSendUnknownCommandMessage(final boolean sendUnknownCommandMessage) {
        this.sendUnknownCommandMessage = sendUnknownCommandMessage;
    }

    public String getFallbackPrefix() {
        return this.fallbackPrefix;
    }

    void setFallbackPrefix(final String fallbackPrefix) {
        this.fallbackPrefix = fallbackPrefix;
    }

    public GuildSettingsProvider getSettingsProvider() {
        return this.settingsProvider;
    }

    void setSettingsProvider(final GuildSettingsProvider settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    public PermissionProvider getPermissionProvider() {
        return this.permissionProvider;
    }

    void setPermissionProvider(final PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }

    public Supplier<Message> getUnknownCommandMessage() {
        return this.unknownCommandMessage;
    }

    void setUnknownCommandMessage(final Supplier<Message> unknownCommandMessage) {
        this.unknownCommandMessage = unknownCommandMessage;
    }

    public Supplier<Message> getLimitMessage() {
        return this.limitMessage;
    }

    void setLimitMessage(final Supplier<Message> limitMessage) {
        this.limitMessage = limitMessage;
    }

}
