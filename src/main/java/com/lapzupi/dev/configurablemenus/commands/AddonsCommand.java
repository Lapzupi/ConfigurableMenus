package com.lapzupi.dev.configurablemenus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.github.sarhatabaot.kraken.core.chat.ChatUtil;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.addons.ItemAddon;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sarhatabaot
 */
@CommandAlias(CommandsUtil.ALIAS)
public class AddonsCommand extends BaseCommand {

    private final ConfigurableMenusPlugin plugin;

    public AddonsCommand(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("addons")
    @CommandPermission("cmenus.admin.addons")
    public void onAddons(final CommandSender sender) {
        final String addonFormat = "%s (v%s)";
        List<String> message = new ArrayList<>();
        for(Map.Entry<String, ItemAddon> entry: plugin.getAddonManager().getAddonMap().entrySet()) {
            message.add(addonFormat.formatted(entry.getKey(), entry.getValue().getVersion()));
        }

        ChatUtil.sendMessage(sender,StringUtils.join(message,", "));
    }
}
