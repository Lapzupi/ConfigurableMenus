package com.lapzupi.dev.configurablemenus.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.github.sarhatabaot.kraken.core.chat.ChatUtil;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.addons.ItemAddon;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sarhatabaot
 */
@CommandAlias(CommandsUtil.ALIAS + " addons")
public class AddonsCommand extends BaseCommand {

    private final ConfigurableMenusPlugin plugin;

    public AddonsCommand(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
    }

    @Default
    @CommandPermission("cmenus.admin.addons")
    public void onAddons(final CommandSender sender) {
        List<Component> components = new ArrayList<>();
        for (Map.Entry<String, ItemAddon> entry : plugin.getAddonManager().getAddonMap().entrySet()) {
            final TextComponent textComponent = Component.text()
                    .content(entry.getKey()).append(Component.text(" (v%s)".formatted(entry.getValue().getVersion())))
                    .hoverEvent(
                            HoverEvent.showText(
                                    Component.text()
                                            .append(Component.text().decoration(TextDecoration.BOLD, true).content(entry.getKey()))
                                            .append(Component.text("\n"))
                                            .append(Component.text("Author: " + entry.getValue().getAuthor()))
                                            .append(Component.text("\n"))
                                            .append(Component.text("Website: " + entry.getValue().getUrl()))
                                            .append(Component.text("\n"))
                                            .append(Component.text("Click to go to website."))
                            )
                    )
                    .clickEvent(ClickEvent.openUrl(entry.getValue().getUrl()))
                    .build();

            components.add(textComponent);
        }

        try (BukkitAudiences audiences = plugin.adventure()) {
            final Component finalMessage = ChatUtil.join(components.iterator(), ", ");
            if(finalMessage != null) {
                audiences.sender(sender).sendMessage(finalMessage);
            }
        }
    }

}
