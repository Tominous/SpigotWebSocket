package de.werdasliesstistdoof.websocket.server.theard;

import static de.werdasliesstistdoof.websocket.server.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.server.main.Socket;
import de.werdasliesstistdoof.websocket.server.client.Client;
import de.werdasliesstistdoof.websocket.server.client.ClientManager;
import org.bukkit.Bukkit;

import java.io.IOException;

public class ServerConnect extends Thread {

    @Override
    public void run() {

        while(!Socket.server.getServer().isClosed()) {

            try {

                java.net.Socket socket = Socket.server.getServer().accept();

                Bukkit.getConsoleSender().sendMessage(getMessage("socket.new.connection", socket.getInetAddress().getHostAddress(), String.valueOf(socket.getPort())));

                Client client = new Client(socket);
                ClientManager.connected.add(client);

            }catch(IOException exception) {

                Bukkit.getConsoleSender().sendMessage(getMessage("server.error.reading"));

            }

        }

    }
}
