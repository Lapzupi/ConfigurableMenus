package com.lapzupi.dev.configurablemenus.config.serializers;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.StorageMenu;
import dev.triumphteam.gui.guis.StorageGui;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class StorageMenuSerializer extends AbstractMenuSerializer<StorageGui> {
    @Override
    public Menu<StorageGui> getMenu(final String id, final String permission, final String title, final MenuType menuType, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        return new StorageMenu(id,permission,title,rows,items,fillers);
    }
}
