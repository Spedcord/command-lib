package xyz.spedcord.commandlib.guild;

public class GuildSettings {

    private final long guildId;
    private String[] prefixes;
    private boolean allowMention;

    public GuildSettings(final long guildId, final String[] prefixes, final boolean allowMention) {
        this.guildId = guildId;
        this.prefixes = prefixes;
        this.allowMention = allowMention;
    }

    public static GuildSettings createDefault(final long guildId) {
        return new GuildSettings(guildId, new String[0], true);
    }

    public long getGuildId() {
        return this.guildId;
    }

    public String[] getPrefixes() {
        return this.prefixes;
    }

    public void setPrefixes(final String[] prefixes) {
        this.prefixes = prefixes;
    }

    public boolean isAllowMention() {
        return this.allowMention;
    }

    public void setAllowMention(final boolean allowMention) {
        this.allowMention = allowMention;
    }

}
