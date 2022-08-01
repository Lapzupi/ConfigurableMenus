package com.lapzupi.dev.configurablemenus.menu;

import com.lapzupi.dev.configurablemenus.config.MenuConfigurate;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sarhatabaot
 */
public class MenuManager {
    private final Map<String, MenuConfigurate> menuConfigurateMap;
    private final Map<String, Menu<?>> menuMap;

    public MenuManager() {
        this.menuMap = new HashMap<>();
        this.menuConfigurateMap = new HashMap<>();
    }

    public void clear() {
        this.menuMap.clear();
    }

    public Menu<?> getMenu(final String id) {
        return menuMap.get(id);
    }

    public void registerMenu(final Menu<?> menu) {
        this.menuMap.put(menu.getId(), menu);
    }

    public void registerConfigurate(final String menuId, final MenuConfigurate configurate) {
        this.menuConfigurateMap.put(menuId,configurate);
    }

    public Set<String> getMenuIds() {
        return this.menuMap.keySet();
    }

    public boolean containsMenu(final String menuId) {
        return menuMap.containsKey(menuId);
    }

    public boolean containsConfigurate(final String menuId) {
        return menuConfigurateMap.containsKey(menuId);
    }

    public MenuConfigurate getConfigurate(final String menuId) {
        return menuConfigurateMap.get(menuId);
    }
}
