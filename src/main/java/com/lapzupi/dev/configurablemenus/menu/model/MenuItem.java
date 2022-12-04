package com.lapzupi.dev.configurablemenus.menu.model;

import com.github.sarhatabaot.kraken.core.chat.ChatUtil;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.addons.ActionAddon;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class MenuItem {
    private final int row;
    private final int column;
    private ItemSettings settings;
    private List<Duplicate> duplicate;
    private List<String> onLeftClick;
    private List<String> onShiftClick;
    private List<String> onRightClick;

    public MenuItem(final int row, final int column, final ItemSettings settings, final List<Duplicate> duplicate, final List<String> onLeftClick, final List<String> onShiftClick, final List<String> onRightClick) {
        this.row = row;
        this.column = column;
        this.settings = settings;
        this.duplicate = duplicate;
        this.onLeftClick = onLeftClick;
        this.onShiftClick = onShiftClick;
        this.onRightClick = onRightClick;
    }

    public GuiItem getAsGuiItem() {
        ItemStack baseItemStack;
        try {
            baseItemStack = settings.getItem();
        } catch (InvalidMaterialException e) {
            //log
            baseItemStack = new ItemStack(Material.AIR);
        }

        ItemBuilder builder = ItemBuilder.from(baseItemStack)
                .amount(settings.getAmount());

        if (!settings.getDisplayName().isEmpty()) {
            builder.name(Component.text(settings.getDisplayName()));
        }

        if (settings.getCustomModelData() != -1) {
            builder.model(settings.getCustomModelData());
        }

        GuiItem guiItem = builder.asGuiItem();

        if (!onLeftClick.isEmpty() || !onShiftClick.isEmpty() || !onRightClick.isEmpty()) {
            guiItem.setAction(new ClickAction());

        }
        return guiItem;
    }

    public class ClickAction implements GuiAction<InventoryClickEvent> {
        private final ConfigurableMenusPlugin plugin = (ConfigurableMenusPlugin) JavaPlugin.getProvidingPlugin(ConfigurableMenusPlugin.class);

        @Override
        public void execute(final @NotNull InventoryClickEvent event) {
            if (!(event.getWhoClicked() instanceof Player player)) {
                return;
            }
            if (event.getClick().isLeftClick()) {
                onClick(player, onLeftClick);
                event.setCancelled(true);
                return;
            }

            if (event.getClick().isRightClick()) {
                onClick(player, onRightClick);
                event.setCancelled(true);
                return;
            }

            if (event.getClick().isShiftClick()) {
                onClick(player, onShiftClick);
                event.setCancelled(true);
            }
        }

        //abstract this as well at some point, enabling people to add their own actions if they want.
        private void onClick(final Player player, final List<String> actions) {
            for (String string : actions) {
                final String prefix = string.split(":")[0];
                final String args = string.split(":")[1];
                ActionAddon actionAddon = this.plugin.getAddonManager().getActionAddon(prefix);
                if(actionAddon != null) {
                    actionAddon.onClick(player,args);
                }
//                if (string.startsWith("command:")) {
//                    runCommand(player, string.split(":")[1]);
//                } else if (string.startsWith("open-link:")) {
//                    openLink(player, string.split(":")[1]);
//                } else if (string.startsWith("message:")) {
//                    message(player, string.split(":")[1]);
//                }
            }
        }

        @Deprecated(since = "0.2.1", forRemoval = true)
        private void runCommand(final Player player, final String command) {
            final String formattedCommand = ChatUtil.format(player,command);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
        }

        @Deprecated(since = "0.2.1", forRemoval = true)
        private void openLink(final Player player, final String link) {
            message(player,link);
        }

        @Deprecated(since = "0.2.1", forRemoval = true)
        private void message(final Player player, final String message) {
            ChatUtil.sendMessage(player, ChatUtil.format(player,message));
        }
    }


    public static class InvalidMaterialException extends Exception {
        public InvalidMaterialException(final String message) {
            super(message);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ItemSettings getSettings() {
        return settings;
    }

    public List<Duplicate> getDuplicate() {
        return duplicate;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "row=" + row +
                ", column=" + column +
                ", settings=" + settings +
                ", duplicate=" + duplicate +
                ", onLeftClick=" + onLeftClick +
                ", onShiftClick=" + onShiftClick +
                ", onRightClick=" + onRightClick +
                '}';
    }
}
