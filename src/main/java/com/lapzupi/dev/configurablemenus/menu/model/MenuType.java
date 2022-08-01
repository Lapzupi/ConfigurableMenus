package com.lapzupi.dev.configurablemenus.menu.model;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import dev.triumphteam.gui.guis.ScrollingGui;
import dev.triumphteam.gui.guis.StorageGui;

/**
 * @author sarhatabaot
 */
public enum MenuType {
    CHEST(Gui.class, GuiType.CHEST),
    WORKBENCH(Gui.class, GuiType.WORKBENCH),
    BREWING(Gui.class, GuiType.BREWING),
    DISPENSER(Gui.class, GuiType.DISPENSER),
    HOPPER(Gui.class, GuiType.HOPPER),
    PAGINATED(PaginatedGui.class, GuiType.CHEST),
    STORAGE(StorageGui.class, GuiType.CHEST),
    SCROLLING(ScrollingGui.class, GuiType.CHEST);
    private final Class<? extends BaseGui> gui;
    private final GuiType guiType;

    MenuType(final Class<? extends BaseGui> gui, final GuiType guiType) {
        this.gui = gui;
        this.guiType = guiType;
    }

    public Class<? extends BaseGui> getGui() {
        return gui;
    }

    public GuiType getGuiType() {
        return guiType;
    }
}
