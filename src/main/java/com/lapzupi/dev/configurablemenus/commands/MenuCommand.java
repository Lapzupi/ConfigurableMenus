package com.lapzupi.dev.configurablemenus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.menu.MenuManager;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author sarhatabaot
 */
@CommandAlias(CommandsUtil.ALIAS)
public class MenuCommand extends BaseCommand {
    private final ConfigurableMenusPlugin plugin;
    private final MenuManager menuManager;

    public MenuCommand(final @NotNull ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
        this.menuManager = plugin.getMenuManager();
    }

    @Subcommand("menu")
    @CommandCompletion("@menus @players")
    @CommandPermission("cmenus.user.menu")
    public void onMenu(final CommandSender sender, final String menuId,@Optional final OnlinePlayer onlinePlayer) {
        if(onlinePlayer == null && sender instanceof ConsoleCommandSender) {
            sender.sendMessage("You must specify a player from console.");
            return;
        }

        if(!menuManager.containsMenu(menuId)) {
            sender.sendMessage("No such menu: %s".formatted(menuId));
            return;
        }

        Menu<?> menu = menuManager.getMenu(menuId);
        if(!sender.hasPermission(menu.getPermission())) {
            sender.sendMessage("You don't have permission %s for menu %s".formatted(menu.getPermission(), menu.getId()));
            return;
        }

        if(onlinePlayer == null) {
            menu.openMenu((Player) sender);
            return;
        }

        menu.openMenu(onlinePlayer.getPlayer());
    }
}
