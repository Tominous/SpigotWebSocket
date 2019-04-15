package de.werdasliesstistdoof.websocket.server.util;

import org.apache.commons.lang.LocaleUtils;
import org.bukkit.Bukkit;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Bundel {

    public static void init() {

        try {

            System.out.println(getMessage("config.success.language", LocaleUtils.toLocale(Config.getLanguage()).toString()));

        }catch(NullPointerException exception) {

            Bukkit.broadcastMessage(getMessage("config.error.language", Locale.ENGLISH));
            Config.setLanguage("en");


        }

    }

    public static String getMessage(String key, Locale language, String... arguments) {

        if(language == Locale.ENGLISH)
            language = Locale.ROOT;

        return new MessageFormat(ResourceBundle.getBundle("SpigotWebSocketMessages", language).getString(key)).format(arguments);

    }

    public static String getMessage(String key, String... arguments) {

        return getMessage(key, LocaleUtils.toLocale(Config.getLanguage()), arguments);

    }

}
