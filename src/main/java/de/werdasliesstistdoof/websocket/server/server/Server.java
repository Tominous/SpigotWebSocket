package de.werdasliesstistdoof.websocket.server.server;

import static de.werdasliesstistdoof.websocket.server.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.server.client.ClientManager;
import de.werdasliesstistdoof.websocket.server.main.Socket;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private ServerSocket server;

    public Server(int port) throws IOException {

        server = new ServerSocket(port);
        Bukkit.getConsoleSender().sendMessage(getMessage("server.success.started"));

    }

    public void close() throws IOException {

        server.close();

        ClientManager.closeAll();
        Socket.serverConnect.interrupt();


    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

}