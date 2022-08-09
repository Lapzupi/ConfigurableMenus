package com.lapzupi.dev.configurablemenus.config;

import com.github.sarhatabaot.kraken.core.config.HoconConfigurateFile;
import com.github.sarhatabaot.kraken.core.config.Transformation;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;

/**
 * @author sarhatabaot
 */
public class SettingsConfigurate extends HoconConfigurateFile<ConfigurableMenusPlugin> {
    private boolean debug;
    private int version;
    private boolean loadExampleMenus;

    public SettingsConfigurate(@NotNull final ConfigurableMenusPlugin plugin) throws ConfigurateException {
        super(plugin, "", "settings.conf", "");
    }

    @Override
    protected void initValues() throws ConfigurateException {
        this.debug = rootNode.node("debug").getBoolean(false);
        this.version = rootNode.node("version").getInt();
        this.loadExampleMenus = rootNode.node("extract-example-menus").getBoolean(true);
    }

    @Override
    protected void builderOptions() {
        //nothing
    }

    @Override
    protected Transformation getTransformation() {
        return null;
    }

    public boolean isDebug() {
        return debug;
    }

    public int getVersion() {
        return version;
    }

    public boolean extractedDefaultMenus() {
        return loadExampleMenus;
    }
}
