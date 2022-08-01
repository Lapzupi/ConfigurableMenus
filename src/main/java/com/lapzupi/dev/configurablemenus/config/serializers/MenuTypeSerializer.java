package com.lapzupi.dev.configurablemenus.config.serializers;

import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

/**
 * @author sarhatabaot
 */
public class MenuTypeSerializer implements TypeSerializer<MenuType> {
    @Override
    public MenuType deserialize(final Type type, final @NotNull ConfigurationNode node) throws SerializationException {
        return MenuType.valueOf(node.getString("CHEST").toUpperCase());
    }

    @Override
    public void serialize(final Type type, @Nullable final MenuType obj, final ConfigurationNode node) throws SerializationException {
        //we don't use this
    }
}
