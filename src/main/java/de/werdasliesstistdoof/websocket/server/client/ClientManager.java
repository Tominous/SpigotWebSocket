package de.werdasliesstistdoof.websocket.server.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClientManager {

    public static List<Client> connected = new LinkedList<>();
    public static List<Thread> threads = new ArrayList<>();

    public static void broadcast(String message) {

        connected.forEach(client -> {

            if(client.isAuthenticated())
                client.sendData("send.message/" + message);

        });

    }

    public static void closeAll() {

        threads.forEach(thread -> thread.interrupt());

    }

}
