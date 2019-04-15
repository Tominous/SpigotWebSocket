package de.werdasliesstistdoof.websocket.client.util;

import org.apache.commons.lang.LocaleUtils;
import org.bukkit.Bukkit;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Bundel {

    static Locale language;

    public static void init(String lang) {

        if(lang.equalsIgnoreCase("de"))
            language = Locale.GERMAN;
        else
            language = Locale.ROOT;

        try {

            System.out.println(getMessage("config.success.language", language.toString().equalsIgnoreCase("") ? "en" : language.toString()));

        }catch(NullPointerException exception) {

            Bukkit.broadcastMessage(getMessage("config.error.language", language));
            language = Locale.ROOT;

        }

    }

    public static String getMessage(String key, Locale language, String... arguments) {

        if(language == Locale.ENGLISH)
            language = Locale.ROOT;

        return new MessageFormat(ResourceBundle.getBundle("SpigotWebSocketMessages", language).getString(key)).format(arguments);

    }

    public static String getMessage(String key, String... arguments) {

        return getMessage(key, language, arguments);

    }

}

