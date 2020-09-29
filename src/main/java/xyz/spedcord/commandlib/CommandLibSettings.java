package xyz.spedcord.commandlib;

import xyz.spedcord.commandlib.guild.GuildSettingsProvider;

public class CommandLibSettings {

    private String fallbackPrefix;
    private boolean allowBots;
    private GuildSettingsProvider settingsProvider;

    public CommandLibSettings(final String fallbackPrefix, final boolean allowBots, final GuildSettingsProvider settingsProvider) {
        this.fallbackPrefix = fallbackPrefix;
        this.allowBots = allowBots;
        this.settingsProvider = settingsProvider;
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

}
