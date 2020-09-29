package xyz.spedcord.commandlib.permission;

import xyz.spedcord.commandlib.command.CommandContext;

public interface PermissionProvider {

    boolean hasPermission(PermissionLevel permissionLevel, CommandContext context);

}
