package com.lapzupi.dev.configurablemenus.addons;

import com.github.sarhatabaot.kraken.core.chat.ChatUtil;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import me.clip.placeholderapi.util.FileUtil;
import me.clip.placeholderapi.util.Futures;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
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

    public AddonManager(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder(), ADDON_FOLDER);

        if (!this.folder.exists() && !this.folder.mkdirs()) {
            plugin.getLogger().warning("Could not create addons folder.");
        }
    }

    private Map<String, ItemAddon> addonMap;

    public ItemStack getItemStack(final String prefix, final String id) {
        return addonMap.get(prefix).getItemStack(id);
    }

    public boolean registerAddon(final @NotNull ItemAddon addon) {
        final String prefix = addon.getPrefix().toLowerCase(Locale.ROOT);
        if (!addon.canRegister())
            return false;

        this.addonMap.put(prefix, addon);
        if (addon instanceof Listener listener) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
        return true;

    }

    public Optional<ItemAddon> registerAddon(final @NotNull Class<? extends ItemAddon> addon) {
        try {
            final ItemAddon itemAddon = createAddonInstance(addon);

            if(itemAddon == null){
                return Optional.empty();
            }

            Objects.requireNonNull(itemAddon.getAuthor(), "The expansion author is null!");
            Objects.requireNonNull(itemAddon.getPrefix(), "The expansion identifier is null!");
            Objects.requireNonNull(itemAddon.getVersion(), "The expansion version is null!");

            if (!itemAddon.canRegister()) {
                plugin.getLogger().warning("Cannot load expansion %s due to an unknown issue.".formatted(itemAddon.getPrefix()));
                return Optional.empty();
            }

            return Optional.of(itemAddon);
        } catch (LinkageError | NullPointerException ex) {
            final String reason;

            if (ex instanceof LinkageError) {
                reason = " (Is a dependency missing?)"; 
            } else {
                reason = " - One of its properties is null which is not allowed!";
            }

            plugin.getLogger().log(Level.SEVERE, "Failed to load addon class %s%s".formatted(addon.getSimpleName(), reason), ex);
        }

        return Optional.empty();
    }

    @Nullable
    public ItemAddon createAddonInstance(@NotNull final Class<? extends ItemAddon > clazz) throws LinkageError {
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


    public CompletableFuture<List<Class<? extends ItemAddon>>> findLocalAddons() {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        return Arrays.stream(files)
                .map(this::findAddonInFile)
                .collect(Futures.collector());
    }

    public CompletableFuture<Class<? extends ItemAddon>> findAddonInFile(File file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final Class<? extends ItemAddon> addonClass = FileUtil.findClass(file, ItemAddon.class);

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

    private void registerAll(@NotNull final CommandSender sender) {
        Futures.onMainThread(plugin, findLocalAddons(), (classes, exception) -> {
            if (exception != null) {
                plugin.getLogger().log(Level.SEVERE,"Failed to load class files of addon.", exception);
                return;
            }

            final List<ItemAddon> registered = classes.stream()
                    .filter(Objects::nonNull)
                    .map(this::registerAddon)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            final String message = "%s addons registered!".formatted(registered.size());

            ChatUtil.sendMessage(sender, message);
        });
    }
}
