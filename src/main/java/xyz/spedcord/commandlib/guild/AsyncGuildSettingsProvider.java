package xyz.spedcord.commandlib.guild;

import java.util.concurrent.CompletableFuture;

public interface AsyncGuildSettingsProvider extends GuildSettingsProvider {

    CompletableFuture<GuildSettings> getSettingsAsync(long guildId);

    CompletableFuture<Void> saveSettingsAsync(GuildSettings settings);

    @Deprecated
    @Override
    default GuildSettings getSettings(final long guildId) {
        final CompletableFuture<GuildSettings> future = this.getSettingsAsync(guildId);
        while (!future.isDone()) {
        }
        return future.getNow(null);
    }

    @Deprecated
    @Override
    default void saveSettings(final GuildSettings settings) {
        final CompletableFuture<Void> future = this.saveSettingsAsync(settings);
        while (!future.isDone()) {
        }
    }

}
