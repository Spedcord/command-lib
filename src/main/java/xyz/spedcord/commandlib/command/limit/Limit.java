package xyz.spedcord.commandlib.command.limit;

public class Limit {

    public static final Limit NONE = new Limit(LimitMode.GLOBAL, 0);

    private final LimitMode limitMode;
    private final long millis;

    public Limit(final LimitMode limitMode, final long millis) {
        this.limitMode = limitMode;
        this.millis = millis;
    }

    public LimitMode getLimitMode() {
        return this.limitMode;
    }

    public long getMillis() {
        return this.millis;
    }

}
