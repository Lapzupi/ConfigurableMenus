package com.lapzupi.dev.configurablemenus.config.serializers;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.PaginatedMenu;
import dev.triumphteam.gui.guis.PaginatedGui;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class PaginatedMenuSerializer extends AbstractMenuSerializer<PaginatedGui> {
    @Override
    public Menu<PaginatedGui> getMenu(final String id, final String permission, final String title, final MenuType menuType, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        return new PaginatedMenu(id,permission,title,rows,items,fillers);
    }
}
