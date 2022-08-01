package com.lapzupi.dev.configurablemenus.menu;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;

import java.util.Map;
import java.util.Set;

/**
 * @author sarhatabaot
 */
public class MenuManager {
    private Map<String, Menu<?>> menuMap;

    public Menu<?> getMenu(final String id) {
        return menuMap.get(id);
    }

    public void registerMenu(final Menu<?> menu) {
        this.menuMap.put(menu.getId(),menu);
    }

    public Set<String> getMenuIds() {
        return this.menuMap.keySet();
    }
}
