package com.lapzupi.dev.configurablemenus.menu;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * @author sarhatabaot
 */
public class MenuItem {
    private final int row;
    private final int index;
    private String displayName;
    private String materialString; //String so we can get ia/hdb stuff
    private int amount;
    private String duplicate;
    private Integer customModelData;
    private List<String> onClick;
    private List<String> onShiftClick;

    public MenuItem(final int row, final int index, final String displayName, final String materialString, final int amount, final String duplicate, final int customModelData, final List<String> onClick) {
        this.row = row;
        this.index = index;
        this.displayName = displayName;
        this.materialString = materialString;
        this.amount = amount;
        this.duplicate = duplicate;
        this.customModelData = customModelData;
        this.onClick = onClick;
    }

    public GuiItem getAsGuiItem() throws InvalidMaterialException {
        ItemBuilder builder = ItemBuilder.from(getItemFromMaterialString())
                .amount(amount);
        if (!displayName.isEmpty()) {
            builder.name(Component.text(displayName));
        }
        if (customModelData != null) {
            builder.model(customModelData);
        }

        if (!onClick.isEmpty()) {

        }

        if (!onShiftClick.isEmpty()) {

        }

        return builder.asGuiItem();
    }

    @Contract(" -> new")
    private @NotNull ItemStack getItemFromMaterialString() throws InvalidMaterialException {
        if (materialString.contains("base64:")) {
            final String base64texture = materialString.split(":")[1];
            //test it's actually a valid base64 texture..
            return getFromBase64(base64texture);
        }
        if (materialString.contains("head:")) {
            final String playerName = materialString.split(":")[1];
            return getFromPlayerHead(playerName);
        }
        if (materialString.contains("hdb:")) {
            final String headId = materialString.split(":")[1];
            return getFromHdb(headId);
        }

        if (materialString.contains("itemsadder:")) {
            final String namespace = materialString.split(":")[1];
            final String itemName = materialString.split(":")[2];
            return getFromItemsAdder(namespace, itemName);
        }

        if (materialString.contains("oraxen:")) {
            final String id = materialString.split(":")[1];
            return getFromOraxen(id);
        }

        Material material = Material.matchMaterial(materialString);
        if (material == null) {
            throw new InvalidMaterialException("Could not get material for %s".formatted(materialString));
        }
        return new ItemStack(material);
    }

    private ItemStack getFromItemsAdder(final String namespace, final String itemName) {
        return ItemUtils.getFromItemsAdder(namespace, itemName);
    }


    private ItemStack getFromOraxen(final String id) {
        return ItemUtils.getFromOraxen(id);
    }

    private ItemStack getFromHdb(final String headId) {
        return ItemUtils.getFromHdb(headId);
    }

    private ItemStack getFromPlayerHead(final String playerName) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        NBTItem nbti = new NBTItem(head);

        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", playerName);

        return nbti.getItem();
    }

    private ItemStack getFromBase64(final String base64) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        NBTItem nbti = new NBTItem(head);

        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Id", UUID.randomUUID().toString());

        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value", base64);

        return nbti.getItem();
    }


    public static class InvalidMaterialException extends Exception {
        public InvalidMaterialException(final String message) {
            super(message);
        }
    }
}
