package com.lapzupi.dev.configurablemenus.menu.model;

import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.GuiItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author sarhatabaot
 */
public abstract class Menu<T extends BaseGui> {
    protected final String id;
    protected final String permission;
    protected String title;
    protected final MenuType type;
    protected int rows;
    private List<MenuItem> items;
    private List<MenuItem> fillers;

    protected T gui;
    /*
    default action
    open action
    close action
     */

    public Menu(final String id, final String permission, final String title, final MenuType type, final int rows, final @NotNull List<MenuItem> items, final List<MenuItem> fillers) throws GuiException {
        this.id = id;
        this.permission = permission;
        this.title = title;
        this.type = type;
        this.rows = rows;
        this.items = items;
        this.fillers = fillers;

        this.gui = createGuiFromType();

        for (MenuItem menuItem : items) {
            GuiItem guiItem = menuItem.getAsGuiItem();
            this.gui.setItem(menuItem.getRow(), menuItem.getColumn(), guiItem);

            if (menuItem.getDuplicate() != null && !menuItem.getDuplicate().isEmpty()) {
                for (Duplicate duplicate : menuItem.getDuplicate()) {
                    for (int i = duplicate.rangeMin(); i <= duplicate.rangeMax(); i++) {
                        this.gui.setItem(duplicate.row(), i, guiItem);
                    }
                }
            }

        }

        if (!fillers.isEmpty()) {
            this.gui.getFiller().fill(fillers.stream().map(MenuItem::getAsGuiItem).toList());
        }
    }

    public String getId() {
        return id;
    }

    public abstract T createGuiFromType();

    public abstract Menu<T> getMenu();

    public void openMenu(final Player player) {
        if(PlaceholderAPI.containsPlaceholders(gui.getTitle())) {
            final String papiTitle = gui.getTitle();
            gui.updateTitle(PlaceholderAPI.setPlaceholders(player,papiTitle));
        }


        gui.open(player);
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", permission='" + permission + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", rows=" + rows +
                ", items=" + items +
                ", fillers=" + fillers +
                ", gui=" + gui +
                '}';
    }
}
