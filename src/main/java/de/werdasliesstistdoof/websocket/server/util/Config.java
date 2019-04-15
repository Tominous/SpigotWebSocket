package de.werdasliesstistdoof.websocket.server.util;

import static de.werdasliesstistdoof.websocket.server.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.server.client.ConfigClient;
import de.werdasliesstistdoof.websocket.server.main.Socket;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private static Socket plugin;
    private static Map users = new HashMap();
    private static List<ConfigClient> configClient = new ArrayList<>();
    private static List<String> writesNames = new ArrayList<>();
    private static String language;

    public Config() {

        plugin = Socket.getPlugin();
        reloadConfig();

    }

    public static void reloadConfig() {

        try {

            for (String key : plugin.getConfig().getConfigurationSection("users").getKeys(false)) {
                users.put(key, plugin.getConfig().get("users." + key));
            }
            writesNames = (ArrayList) plugin.getConfig().getList("writes");
            users.forEach((user, password) -> configClient.add(new ConfigClient((String) user, (String) password)));
            writesNames.forEach(name -> {

                ConfigClient configClient1 = getUser(name);
                configClient.remove(configClient1);
                configClient1.setCanWrtie(true);
                configClient.add(configClient1);

            });

            language = (String) plugin.getConfig().get("language");

            Bukkit.getConsoleSender().sendMessage(getMessage("config.success.loaded"));

        }catch(NullPointerException exception) {

            System.out.println(getMessage("config.error.empty"));
            exception.printStackTrace();

        }
    }

    public static List<ConfigClient> getConfigClient() {
        return configClient;
    }

    private static ConfigClient configClientFound;
    public static ConfigClient getUser(String name, String password) {

        configClientFound = null;

        getConfigClient().forEach(configClient -> {

            if(configClient.getName().equalsIgnoreCase(name) && configClient.isPassword(password))
                configClientFound = configClient;

        });

        return configClientFound;

    }

    public static ConfigClient getUser(String name) {

        configClientFound = null;

        getConfigClient().forEach(configClient -> {

            if(configClient.getName().equalsIgnoreCase(name))
                configClientFound = configClient;

        });

        return configClientFound;

    }

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        plugin.getConfig().set("language", language);
        Config.language = language;
    }
}
