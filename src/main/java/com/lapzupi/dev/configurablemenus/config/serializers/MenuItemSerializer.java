package com.lapzupi.dev.configurablemenus.config.serializers;

import com.lapzupi.dev.configurablemenus.menu.model.Duplicate;
import com.lapzupi.dev.configurablemenus.menu.model.ItemSettings;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sarhatabaot
 */
public class MenuItemSerializer implements TypeSerializer<MenuItem> {
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

        final List<Duplicate> duplicates = Duplicate.asListFromString(node.node(DUPLICATE).getString(""));
        final String displayName = node.node(DISPLAY_NAME).getString("");
        final String materialString = node.node(MATERIAL).getString("");
        final int amount = node.node(AMOUNT).getInt(1);
        final int customModelData = node.node(CUSTOM_MODEL_DATA).getInt(-1);
        final ItemSettings settings = new ItemSettings(displayName, materialString, amount, customModelData);
        return new MenuItem(row, index, settings, duplicates, onLeftClick, onShiftClick, onRightClick);
    }

    @Override
    public void serialize(final Type type, final @Nullable MenuItem obj, final ConfigurationNode node) throws SerializationException {
        //we don't use this
    }
}
