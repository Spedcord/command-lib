package xyz.spedcord.commandlib.permission;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import xyz.spedcord.commandlib.command.CommandContext;

public class DefaultPermissionProvider implements PermissionProvider {

    @Override
    public boolean hasPermission(final PermissionLevel permissionLevel, final CommandContext context) {
        return permissionLevel != PermissionLevel.ADMIN || (!context.isGuild()
                || context.getMember().hasPermission((GuildChannel) context.getChannel(), Permission.ADMINISTRATOR));
    }

}
