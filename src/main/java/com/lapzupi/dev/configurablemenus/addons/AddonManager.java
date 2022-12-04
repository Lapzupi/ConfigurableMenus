package com.lapzupi.dev.configurablemenus.addons;

import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import me.clip.placeholderapi.util.FileUtil;
import me.clip.placeholderapi.util.Futures;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;

public class AddonManager {
    private static final String ADDON_FOLDER = "addons";
    private final ConfigurableMenusPlugin plugin;
    private final File folder;
    private final Map<String, Addon> addonMap;

    private final Map<String, Boolean> loadingMap;

    public AddonManager(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder(), ADDON_FOLDER);
        this.addonMap = new HashMap<>();
        this.loadingMap = new HashMap<>();

        if (!this.folder.exists() && !this.folder.mkdirs()) {
            plugin.getLogger().warning("Could not create addons folder.");
        }
    }


    public ItemStack getItemStack(final String prefix, final String id) {
        if (!addonMap.containsKey(prefix)) {

            if (!loadingMap.getOrDefault(prefix, true)) {
                plugin.getLogger().warning(() -> "No such prefix %s, did you install the addon?".formatted(prefix));
            }
            return new ItemStack(Material.AIR);
        }

        final Addon addon = addonMap.get(prefix);
        if (!(addon instanceof ItemAddon itemAddon)) {
            return new ItemStack(Material.AIR);
        }
        return itemAddon.getItemStack(id);
    }

    @Nullable
    public ActionAddon getActionAddon(final String prefix) {
        Addon addon = this.addonMap.get(prefix);
        if(addon instanceof ActionAddon actionAddon)
            return actionAddon;
        return null;
    }

    public boolean registerAddon(final @NotNull Addon addon) {
        final String prefix = addon.getPrefix().toLowerCase(Locale.ROOT);
        if (!addon.canRegister()) {
            this.loadingMap.put(prefix, true);
            return false;
        }

        this.loadingMap.put(prefix, false);
        this.addonMap.put(prefix, addon);
        if (addon instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
        return true;

    }

    public Optional<Addon> registerAddon(final @NotNull Class<? extends Addon> addon) {
        try {
            final Addon addonInstance = createAddonInstance(addon);

            if (addonInstance == null) {
                return Optional.empty();
            }

            Objects.requireNonNull(addonInstance.getAuthor(), "The expansion author is null!");
            Objects.requireNonNull(addonInstance.getPrefix(), "The expansion identifier is null!");
            Objects.requireNonNull(addonInstance.getVersion(), "The expansion version is null!");

            if (!addonInstance.canRegister()) {
                plugin.getLogger().warning(() -> "Cannot load expansion %s due to an unknown issue.".formatted(addonInstance.getPrefix()));
                this.loadingMap.put(addonInstance.getPrefix(), true);
                return Optional.empty();
            }

            if (addonInstance instanceof Listener listener) {
                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }

            this.addonMap.put(addonInstance.getPrefix(), addonInstance);
            return Optional.of(addonInstance);
        } catch (LinkageError | NullPointerException ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load addon class %s%s".formatted(addon.getSimpleName(), ex.getMessage()), ex);
        }

        return Optional.empty();
    }

    @Nullable
    public Addon createAddonInstance(@NotNull final Class<? extends Addon> clazz) throws LinkageError {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (final Exception ex) {
            if (ex.getCause() instanceof LinkageError cause) {
                throw cause;
            }

            plugin.getLogger().warning("There was an issue with loading an addon.");
            return null;
        }
    }


    public CompletableFuture<List<Class<? extends Addon>>> findLocalAddons() {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) {
            plugin.debug("Could not find any files in folder %s".formatted(folder.getName()));
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        return Arrays.stream(files)
                .map(this::findAddonInFile)
                .collect(Futures.collector());
    }

    public CompletableFuture<Class<? extends Addon>> findAddonInFile(File file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final Class<? extends Addon> addonClass = FileUtil.findClass(file, Addon.class);

                if (addonClass == null) {
                    plugin.getLogger().warning("Failed to load addon from file: %s".formatted(file.getName()));
                    return null;
                }

                return addonClass;
            } catch (final VerifyError ex) {
                plugin.getLogger().severe("Failed to load addon class %s".formatted(file.getName()));
                plugin.getLogger().severe("Cause: %s %s".formatted(ex.getClass().getSimpleName(), ex.getMessage()));
                return null;
            } catch (final Exception ex) {
                throw new CompletionException(ex);
            }
        });
    }

    private void registerAll() {
        Futures.onMainThread(plugin, findLocalAddons(), (classes, exception) -> {
            if (exception != null) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load class files of addon.", exception);
                return;
            }

            final List<Addon> registered = classes.stream()
                    .filter(Objects::nonNull)
                    .map(this::registerAddon)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();


            final String message = "%s new addons registered! (%s total)".formatted(registered.size(), addonMap.keySet().size());
            plugin.getLogger().info(message);
        });
    }

    public void load() {
        registerAll();
        for (Map.Entry<String, Addon> entry : addonMap.entrySet()) {
            if (entry.getValue() instanceof Listener listener) {
                Bukkit.getPluginManager().registerEvents(listener, plugin);
            }
        }
    }

    public Map<String, Addon> getAddonMap() {
        return addonMap;
    }
}
