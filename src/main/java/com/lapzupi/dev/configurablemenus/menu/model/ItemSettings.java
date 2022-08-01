package com.lapzupi.dev.configurablemenus.menu.model;

import com.lapzupi.dev.configurablemenus.menu.ItemUtils;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author sarhatabaot
 */
public class ItemSettings {
    private String displayName;
    private String materialString;
    private int amount;
    private Integer customModelData;

    public ItemSettings(final String displayName, final String materialString, final int amount, final Integer customModelData) {
        this.displayName = displayName;
        this.materialString = materialString;
        this.amount = amount;
        this.customModelData = customModelData;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMaterialString() {
        return materialString;
    }

    public int getAmount() {
        return amount;
    }

    public Integer getCustomModelData() {
        return customModelData;
    }

    /*
    TODO
    Consider abstracting this whole process, to make it accessible by adding "addons".
    An addon will have a "prefix" i.e. "base64" or "itemsadder".
    Add this feature once the plugin is more fully fledged out.
     */
    @Contract(" -> new")
    public @NotNull ItemStack getItem() throws MenuItem.InvalidMaterialException {
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
            throw new MenuItem.InvalidMaterialException("Could not get material for %s".formatted(materialString));
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

    public ItemSettings setDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemSettings setMaterialString(final String materialString) {
        this.materialString = materialString;
        return this;
    }

    public ItemSettings setAmount(final int amount) {
        this.amount = amount;
        return this;
    }

    public ItemSettings setCustomModelData(final Integer customModelData) {
        this.customModelData = customModelData;
        return this;
    }
}
