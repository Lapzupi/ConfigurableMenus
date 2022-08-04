package com.lapzupi.dev.configurablemenus.config.serializers;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import dev.triumphteam.gui.guis.BaseGui;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @author sarhatabaot
 */
public abstract class AbstractMenuSerializer<T extends BaseGui> implements TypeSerializer<Menu<T>> {
    protected static final String ID = "id";
    protected static final String PERMISSION = "permission";
    protected static final String TITLE = "title";
    protected static final String TYPE = "type";
    protected static final String ROWS = "rows";
    protected static final String ITEMS = "items";
    protected static final String FILLERS = "fillers";

    public abstract Menu<T> getMenu(final String id, final String permission, final String title, final MenuType menuType, final int rows, final List<MenuItem> items, final List<MenuItem> fillers);

    @Override
    public Menu<T> deserialize(final Type type, final @NotNull ConfigurationNode node) throws SerializationException {
        final String id = node.node(ID).getString();
        final String permission = node.node(PERMISSION).getString("menus." + id);
        final String title = node.node(TITLE).getString("");
        final MenuType menuType = node.node(TYPE).get(MenuType.class, MenuType.CHEST);
        final int rows = node.node(ROWS).getInt(menuType.getGuiType().getLimit());
        final List<MenuItem> items = node.node(ITEMS).getList(MenuItem.class, Collections.emptyList());
        final List<MenuItem> fillers = node.node(FILLERS).getList(MenuItem.class, Collections.emptyList());

        return getMenu(id, permission, title, menuType, rows, items, fillers);
    }

    @Override
    public void serialize(final Type type, @Nullable final Menu obj, final ConfigurationNode node) throws SerializationException {
        //we don't use this
    }
}
