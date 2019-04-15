package de.werdasliesstistdoof.websocket.server.main;

import static de.werdasliesstistdoof.websocket.server.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.server.client.ClientManager;
import de.werdasliesstistdoof.websocket.server.server.Server;
import de.werdasliesstistdoof.websocket.server.theard.ServerConnect;
import de.werdasliesstistdoof.websocket.server.util.Bundel;
import de.werdasliesstistdoof.websocket.server.util.LogAppender;
import de.werdasliesstistdoof.websocket.server.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Socket extends JavaPlugin {

    public static Server server;
    public static Thread serverConnect;

    private static Socket plugin;

    private static final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();

    @Override
    public void onEnable() {

        plugin = this;

        getCommand("crypter").setExecutor(new de.werdasliesstistdoof.websocket.server.command.Crypter());
        getCommand("crypter").setTabCompleter(new de.werdasliesstistdoof.websocket.server.command.Crypter());

        getConfig().addDefault("port", 25566);
        getConfig().addDefault("language", "de");
        getConfig().addDefault("users", new HashMap<>());
        getConfig().addDefault("writes", new ArrayList<>());
        getConfig().options().header("English: This is the Config. Here can you edit the Users, the Port and the Permissions.\nport: 25566\nusers:\n  Rasmus: [Crypted Password]\nwrites:\n- Rasmus\n\nTo encrypt you password use /crypter <password>\n\n\n" +
                                     "German: Dies ist die Konfiguration. Hier kannst du die Benutzer, den Port sowie die Permissions setzten\nport: 25566\nusers:\n  Rasmus: [Verschlüsseltes Password]\nwrites:\n- Rasmus\n\nUm dein Password zu verschlüsseln nutze /crypter <password>");

        getConfig().options().copyDefaults(true);

        saveConfig();

        new Config();
        Bundel.init();

        try {

            server = new Server((int) getConfig().get("port"));

        }catch(IOException exception) {

            Bukkit.getConsoleSender().sendMessage(getMessage("server.error.starting"));
            exception.printStackTrace();

        }

        serverConnect = new Thread(new ServerConnect());
        serverConnect.start();
        LogAppender appender = new LogAppender();
        logger.addAppender(appender);

    }

    @Override
    public void onDisable() {

        ClientManager.connected.forEach(client -> client.sendData("close.socket"));

       try {

           server.close();

       }catch(IOException exception) {

           System.out.println(getMessage("server.error.stopping"));
           exception.printStackTrace();

       }

    }

    public static Socket getPlugin() {
        return plugin;
    }

}
