package com.lapzupi.dev.configurablemenus;

import com.github.sarhatabaot.kraken.core.chat.ChatUtil;
import dev.triumphteam.gui.components.util.Legacy;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author sarhatabaot
 */
public class FormattingUtil {

    public static @NotNull String format(final Player player, final String text) {
        String finalText = text;
        if(PlaceholderAPI.containsPlaceholders(text)) {
            finalText = PlaceholderAPI.setPlaceholders(player, text);
        }

        //MiniMessage
        if(finalText.contains("<") || finalText.contains(">")) {
            MiniMessage miniMessage = MiniMessage.miniMessage();
            finalText = Legacy.SERIALIZER.serialize(miniMessage.deserialize(finalText));
        }

        //LegacyFormatting
        if(finalText.contains("&")) {
            finalText = ChatUtil.color(finalText);
        }
        return finalText;
    }

}
