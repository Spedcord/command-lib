package xyz.spedcord.commandlib.command.limit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import xyz.spedcord.commandlib.command.CommandContext;

public class LimitController {

    private static final Map<CommandContext, Long> limitedMap = new HashMap<>();

    private LimitController() {
    }

    public static boolean isLimited(final CommandContext context) {
        final Optional<CommandContext> optional = limitedMap.keySet().stream()
                .filter(ctx -> ctx.getSubCommand() == context.getSubCommand())
                .findAny();
        if (!optional.isPresent()) {
            return false;
        }

        final CommandContext key = optional.get();
        final Limit limit = new Limit(key.getSubCommand().limitMode(), key.getSubCommand().limitMillis());

        switch (limit.getLimitMode()) {
            case USER:
                if (key.getUser().getIdLong() != context.getUser().getIdLong()) {
                    return false;
                }
                break;
            case CHANNEL:
                if (key.getChannel().getIdLong() != context.getChannel().getIdLong()) {
                    return false;
                }
                break;
            case GUILD:
                if (!(key.isGuild() && context.isGuild() && key.getGuild().getIdLong() == context.getGuild().getIdLong())) {
                    return false;
                }
                break;
            case GLOBAL:
                return false;
        }

        final Long timestamp = limitedMap.get(key);
        final boolean muted = (System.currentTimeMillis() - timestamp) <= limit.getMillis();
        if (!muted) {
            limitedMap.remove(key);
        }
        return muted;
    }

    public static void limit(final CommandContext context) {
        if (context.getSubCommand().limitMillis() <= 0) {
            return;
        }
        limitedMap.put(context, System.currentTimeMillis());
    }

}
