package xyz.spedcord.commandlib;

import xyz.spedcord.commandlib.guild.GuildSettingsProvider;

public class CommandLibSettingsBuilder {

    private final CommandLibSettings settings = new CommandLibSettings("!", false, null);

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

    public CommandLibSettings build() {
        if (this.settings.getSettingsProvider() == null) {
            throw new IllegalStateException("Cannot construct settings object without a GuildSettingsProvider");
        }
        return this.settings;
    }

}
