package xyz.spedcord.commandlib.guild;

public interface GuildSettingsProvider {

    GuildSettings getSettings(long guildId);

    void saveSettings(GuildSettings settings);

}
