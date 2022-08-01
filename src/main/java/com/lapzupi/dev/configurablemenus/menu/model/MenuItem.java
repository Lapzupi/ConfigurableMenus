package com.lapzupi.dev.configurablemenus.menu.model;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class MenuItem {
    private final int row;
    private final int index;
    private ItemSettings settings;
    private String duplicate;
    private List<String> onClick;
    private List<String> onShiftClick;

    public MenuItem(final int row, final int index, final ItemSettings settings, final String duplicate, final List<String> onClick, final List<String> onShiftClick) {
        this.row = row;
        this.index = index;
        this.settings = settings;
        this.duplicate = duplicate;
        this.onClick = onClick;
        this.onShiftClick = onShiftClick;
    }

    public GuiItem getAsGuiItem() throws InvalidMaterialException {
        ItemBuilder builder = ItemBuilder.from(settings.getItemFromMaterialString())
                .amount(settings.getAmount());
        if (!settings.getDisplayName().isEmpty()) {
            builder.name(Component.text(settings.getDisplayName()));
        }
        if (settings.getCustomModelData() != null) {
            builder.model(settings.getCustomModelData());
        }

        if (!onClick.isEmpty()) {

        }

        if (!onShiftClick.isEmpty()) {

        }

        return builder.asGuiItem();
    }


    public static class InvalidMaterialException extends Exception {
        public InvalidMaterialException(final String message) {
            super(message);
        }
    }

    public int getRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }

    public ItemSettings getSettings() {
        return settings;
    }

    public String getDuplicate() {
        return duplicate;
    }
}
