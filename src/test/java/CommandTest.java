import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import xyz.spedcord.commandlib.CommandLibSettings;
import xyz.spedcord.commandlib.CommandLibSettingsBuilder;
import xyz.spedcord.commandlib.command.CommandRegistry;
import xyz.spedcord.commandlib.guild.GuildSettings;
import xyz.spedcord.commandlib.guild.GuildSettingsProvider;

public class CommandTest {


    public static void main(final String[] args) {
        final String botToken = System.getenv("BOT_TOKEN");

        final JDA jda;
        try {
            jda = JDABuilder.create(botToken, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS)).build().awaitReady();
        } catch (final InterruptedException | LoginException e) {
            e.printStackTrace();
            return;
        }

        final CommandLibSettings settings = new CommandLibSettingsBuilder()
                .allowBots(false)
                .fallbackPrefix("!")
                .settingsProvider(new GuildSettingsProvider() {
                    @Override
                    public GuildSettings getSettings(final long guildId) {
                        return GuildSettings.createDefault(guildId);
                    }

                    @Override
                    public void saveSettings(final GuildSettings settings) {
                    }
                })
                .build();
        final CommandRegistry commandRegistry = new CommandRegistry(settings);
        commandRegistry.apply(jda);
        commandRegistry.registerDefaults();
    }

}
