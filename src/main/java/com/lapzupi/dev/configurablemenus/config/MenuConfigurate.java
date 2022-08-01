package com.lapzupi.dev.configurablemenus.config;

import com.github.sarhatabaot.kraken.core.config.HoconConfigurateFile;
import com.github.sarhatabaot.kraken.core.config.Transformation;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.config.serializers.AbstractMenuSerializer;
import com.lapzupi.dev.configurablemenus.config.serializers.GuiMenuSerializer;
import com.lapzupi.dev.configurablemenus.config.serializers.MenuItemSerializer;
import com.lapzupi.dev.configurablemenus.config.serializers.MenuTypeSerializer;
import com.lapzupi.dev.configurablemenus.config.serializers.PaginatedMenuSerializer;
import com.lapzupi.dev.configurablemenus.config.serializers.StorageMenuSerializer;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.GuiMenu;
import com.lapzupi.dev.configurablemenus.menu.types.PaginatedMenu;
import com.lapzupi.dev.configurablemenus.menu.types.StorageMenu;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;

/**
 * @author sarhatabaot
 */
/*
 Single menu file
 */
public class MenuConfigurate extends HoconConfigurateFile<ConfigurableMenusPlugin> {
    private Menu<?> menu;

    public MenuConfigurate(@NotNull final ConfigurableMenusPlugin plugin, final String resourcePath, final String fileName) throws ConfigurateException {
        super(plugin, resourcePath, fileName, "menus");
    }

    @Override
    protected void initValues() throws ConfigurateException {
        final MenuType menuType = rootNode.node("menu").node("type").get(MenuType.class, MenuType.CHEST);

        switch (menuType) {
            case PAGINATED -> this.menu = rootNode.node("menu").get(PaginatedMenu.class);
            case STORAGE -> this.menu = rootNode.node("menu").get(StorageMenu.class);
            //case SCROLLING
            default -> this.menu = rootNode.node("menu").get(GuiMenu.class);
        }
    }

    @Override
    protected void builderOptions() {
        loaderBuilder.defaultOptions(options -> options.serializers(builder ->
                builder.registerExact(MenuType.class, new MenuTypeSerializer())
                        .registerExact(MenuItem.class, new MenuItemSerializer())
                        .registerExact(GuiMenu.class, new GuiMenuSerializer())
                        .registerExact(PaginatedMenu.class, new PaginatedMenuSerializer())
                        .registerExact(StorageMenu.class, new StorageMenuSerializer())));
    }

    @Override
    protected Transformation getTransformation() {
        return null;
    }

    public Menu<?> getMenu() {
        return menu;
    }

}
