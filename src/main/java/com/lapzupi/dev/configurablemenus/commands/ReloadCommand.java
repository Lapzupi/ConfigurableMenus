package com.lapzupi.dev.configurablemenus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author sarhatabaot
 */
@CommandAlias(CommandsUtil.ALIAS)
public class ReloadCommand extends BaseCommand {
    private final ConfigurableMenusPlugin plugin;

    public ReloadCommand(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("reload")
    @CommandPermission("cmenus.admin.reload")
    public void onReload(final @NotNull CommandSender sender) {
        plugin.onReload();
        sender.sendMessage("Reloaded %s.".formatted(plugin.getName()));

    }
}
