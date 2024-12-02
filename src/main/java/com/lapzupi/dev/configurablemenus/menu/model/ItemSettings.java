package com.lapzupi.dev.configurablemenus.menu.model;

import com.lapzupi.dev.chat.ChatUtil;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * @author sarhatabaot
 */
public class ItemSettings {
    private final ConfigurableMenusPlugin plugin;
    private String displayName;
    private String materialString;
    private int amount;
    private int customModelData;

    public ItemSettings(final String displayName, final String materialString, final int amount, final int customModelData) {
        this.displayName = ChatUtil.color(displayName);
        this.materialString = materialString;
        this.amount = amount;
        this.customModelData = customModelData;
        this.plugin = (ConfigurableMenusPlugin) Bukkit.getPluginManager().getPlugin("ConfigurableMenus");
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

    public int getCustomModelData() {
        return customModelData;
    }

    @Contract(" -> new")
    public @NotNull ItemStack getItem() throws MenuItem.InvalidMaterialException {
        if (materialString.contains(":")) {
            //assume this is an addon string
            final String[] split = materialString.split(":", 2);
            final String prefix = split[0];
            final String id = split[1];
            return this.plugin.getAddonManager().getItemStack(prefix,id);
        }


        Material material = Material.matchMaterial(materialString);
        if (material == null) {
            throw new MenuItem.InvalidMaterialException("Could not get material for %s".formatted(materialString));
        }

        return new ItemStack(material);
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

    @Override
    public String toString() {
        return "ItemSettings{" +
                "displayName='" + displayName + '\'' +
                ", materialString='" + materialString + '\'' +
                ", amount=" + amount +
                ", customModelData=" + customModelData +
                '}';
    }
}
