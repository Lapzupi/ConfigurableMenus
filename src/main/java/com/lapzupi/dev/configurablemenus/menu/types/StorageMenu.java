package com.lapzupi.dev.configurablemenus.menu.types;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.StorageGui;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class StorageMenu extends Menu<StorageGui> {
    public StorageMenu(final String id, final String permission, final String title, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        super(id, permission, title, MenuType.STORAGE, rows, items, fillers);
    }

    @Override
    public StorageGui createGuiFromType() {
        return Gui.storage()
                .rows(rows)
                .title(Component.text(title))
                .create();
    }

    @Override
    public Menu<StorageGui> getMenu() {
        return this;
    }
}
