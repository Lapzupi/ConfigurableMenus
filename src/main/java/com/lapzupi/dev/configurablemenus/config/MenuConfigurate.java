package com.lapzupi.dev.configurablemenus.config;

import com.github.sarhatabaot.kraken.core.config.HoconConfigurateFile;
import com.github.sarhatabaot.kraken.core.config.Transformation;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.menu.model.Duplicate;
import com.lapzupi.dev.configurablemenus.menu.model.ItemSettings;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.GuiMenu;
import com.lapzupi.dev.configurablemenus.menu.types.PaginatedMenu;
import com.lapzupi.dev.configurablemenus.menu.types.StorageMenu;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        this.menu = rootNode.node("menu").get(Menu.class);
    }

    @Override
    protected void builderOptions() {
        loaderBuilder.defaultOptions(options -> options.serializers(builder ->
                builder.registerExact(MenuType.class, new MenuTypeSerializer())
                        .registerExact(MenuItem.class, new MenuItemSerializer())
                        .registerExact(Menu.class, new MenuSerializer())));
    }

    @Override
    protected Transformation getTransformation() {
        return null;
    }

    public Menu<?> getMenu() {
        return menu;
    }

    public static class MenuSerializer implements TypeSerializer<Menu<?>> {
        private static final String ID = "id";
        private static final String PERMISSION = "permission";
        private static final String TITLE = "title";
        private static final String TYPE = "type";
        private static final String ROWS = "rows";
        private static final String ITEMS = "items";
        private static final String FILLERS = "fillers";

        @Override
        public Menu<?> deserialize(final Type type, final @NotNull ConfigurationNode node) throws SerializationException {
            final String id = node.node(ID).getString();
            final String permission = node.node(PERMISSION).getString("menus." + id);
            final String title = node.node(TITLE).getString("");
            final MenuType menuType = node.node(TYPE).get(MenuType.class, MenuType.CHEST);
            final int rows = node.node(ROWS).getInt(menuType.getGuiType().getLimit());
            final List<MenuItem> items = node.node(ITEMS).getList(MenuItem.class);
            final List<MenuItem> fillers = node.node(FILLERS).getList(MenuItem.class);

            switch (menuType) {
                case PAGINATED -> {
                    return new PaginatedMenu(id, permission, title, rows, items, fillers);
                }
                case STORAGE -> {
                    return new StorageMenu(id, permission, title, rows, items, fillers);

                }
                //case SCROLLING -> not implemented yet
                default -> {
                    //workbench, chest, hopper, dispenser, brewing
                    return new GuiMenu(id, permission, title, menuType, rows, items, fillers);
                }
            }

        }

        @Override
        public void serialize(final Type type, @Nullable final Menu obj, final ConfigurationNode node) throws SerializationException {
            //we don't use this
        }
    }

    public static class MenuItemSerializer implements TypeSerializer<MenuItem> {
        private static final String ROW = "row";
        private static final String INDEX = "index";
        private static final String ON_LEFT_CLICK = "on-left-click";
        private static final String ON_RIGHT_CLICK = "on-right-click";
        private static final String ON_SHIFT_CLICK = "on-shift-click";

        private static final String DISPLAY_NAME = "display-name";
        private static final String MATERIAL = "material";
        private static final String AMOUNT = "amount";
        private static final String CUSTOM_MODEL_DATA = "custom-model-data";
        private static final String DUPLICATE = "duplicate";


        @Override
        public MenuItem deserialize(final Type type, final @NotNull ConfigurationNode node) throws SerializationException {
            final int row = node.node(ROW).getInt();
            final int index = node.node(INDEX).getInt();
            final List<String> onLeftClick = node.node(ON_LEFT_CLICK).getList(String.class);
            final List<String> onShiftClick = node.node(ON_SHIFT_CLICK).getList(String.class);
            final List<String> onRightClick = node.node(ON_RIGHT_CLICK).getList(String.class);

            final List<Duplicate> duplicates = new ArrayList<>();
            for (String string : node.node(DUPLICATE).getString("").split(",")) {
                if (Duplicate.isDuplicateString(string))
                    duplicates.add(Duplicate.fromString(string));
            }

            final String displayName = node.node(DISPLAY_NAME).getString("");
            final String materialString = node.node(MATERIAL).getString();
            final int amount = node.node(AMOUNT).getInt(1);
            final Integer customModelData = node.node(CUSTOM_MODEL_DATA).get(Integer.class, (Integer) null);
            final ItemSettings settings = new ItemSettings(displayName, materialString, amount, customModelData);
            return new MenuItem(row, index, settings, duplicates, onLeftClick, onShiftClick, onRightClick);
        }

        @Override
        public void serialize(final Type type, final @Nullable MenuItem obj, final ConfigurationNode node) throws SerializationException {
            //we don't use this
        }
    }

    public static class MenuTypeSerializer implements TypeSerializer<MenuType> {
        @Override
        public MenuType deserialize(final Type type, final @NotNull ConfigurationNode node) throws SerializationException {
            return MenuType.valueOf(node.getString("CHEST").toUpperCase());
        }

        @Override
        public void serialize(final Type type, @Nullable final MenuType obj, final ConfigurationNode node) throws SerializationException {
            //we don't use this
        }
    }
}
