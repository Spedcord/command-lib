package xyz.spedcord.commandlib;

import xyz.spedcord.commandlib.guild.GuildSettingsProvider;
import xyz.spedcord.commandlib.permission.PermissionProvider;

public class CommandLibSettings {

    private String fallbackPrefix;
    private boolean allowBots;
    private GuildSettingsProvider settingsProvider;
    private PermissionProvider permissionProvider;

    public CommandLibSettings(final String fallbackPrefix, final boolean allowBots, final GuildSettingsProvider settingsProvider, final PermissionProvider permissionProvider) {
        this.fallbackPrefix = fallbackPrefix;
        this.allowBots = allowBots;
        this.settingsProvider = settingsProvider;
        this.permissionProvider = permissionProvider;
    }

    public boolean isAllowBots() {
        return this.allowBots;
    }

    void setAllowBots(final boolean allowBots) {
        this.allowBots = allowBots;
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

    public void setSettingsProvider(final GuildSettingsProvider settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    public PermissionProvider getPermissionProvider() {
        return this.permissionProvider;
    }

    public void setPermissionProvider(final PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }

}
