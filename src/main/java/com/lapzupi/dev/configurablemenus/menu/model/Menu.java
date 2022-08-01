package com.lapzupi.dev.configurablemenus.menu.model;

import dev.triumphteam.gui.guis.BaseGui;

import java.util.List;

/**
 * @author sarhatabaot
 */
/*todo, we could have Menu<T extends BaseGui> and then implement, GuiMenu extends Menu<Gui>
PaginatedMenu extends Menu<PaginatedGui>
StorageMenu extends Menu<StorageGui>
 */
public abstract class Menu<T extends BaseGui> {
    protected final String id;
    protected final String permission;
    protected String title;
    protected final MenuType type;
    protected int rows;
    private List<MenuItem> items;
    private List<MenuItem> fillers;
    /*
    default action
    open action
    close action
     */

    public Menu(final String id, final String permission, final String title, final MenuType type, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        this.id = id;
        this.permission = permission;
        this.title = title;
        this.type = type;
        this.rows = rows;
        this.items = items;
        this.fillers = fillers;
    }

    public String getId() {
        return id;
    }

    public abstract T getGuiFromType();

    public abstract Menu<T> getMenu();

}
