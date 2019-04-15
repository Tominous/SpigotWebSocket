package de.werdasliesstistdoof.websocket.server.client;

import static de.werdasliesstistdoof.websocket.server.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.server.theard.DataReader;
import de.werdasliesstistdoof.websocket.server.util.Crypter;
import de.werdasliesstistdoof.websocket.server.util.Config;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private boolean authenticated = false;
    private boolean canWrite;
    private String name;
    private ConfigClient configClient;

    public Client(Socket client) {

        socket = client;

        Thread reader = new Thread(new DataReader(this));
        reader.start();
        ClientManager.threads.add(reader);

    }

    public void readData(String data) {

        if(data.startsWith("SWSP://")) {

            data = data.replaceFirst("SWSP://", "");

            if(isAuthenticated()) {

                if(data.startsWith("request.command")) {

                    if(configClient.isCanWrtie()) {

                        data = data.replaceFirst("request[.]command/", "");
                        System.out.println(">" + data);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data);

                    } else
                        sendData("send.message/" + getMessage("server.error.write"));

                }

            } else
                authenticate(data);

        }

    }

    public void sendData(String data) {

        try {

            OutputStream outputStream = getSocket().getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(Crypter.encode("SWSP://" + data) + "\n");
            writer.flush();

        }catch(IOException exception) {/*ERROR, no Exception, because Server-Crash*/}

    }

    public void authenticate(String data) {

        if(data.startsWith("request.authenticate")) {

            String[] params = data.replaceFirst("request[.]authenticate/", "").split(";#-_-#;");
            String name = params[0];
            String password = params[1];

            ConfigClient configClient = Config.getUser(name, password);

            if(configClient != null) {

                setAuthenticated(true);
                this.name = configClient.getName();
                this.configClient = configClient;
                this.canWrite = configClient.isCanWrtie();

                Bukkit.getConsoleSender().sendMessage(getMessage("socket.auth.success", socket.getInetAddress().getHostAddress(), String.valueOf(socket.getPort()), name, configClient.canWrite()));
                sendData("data.authenticate/" + name + ";#-_-#;" + canWrite);

            } else {

                setAuthenticated(false);
                this.name = "";
                this.configClient = null;
                this.canWrite = false;

                sendData("failed.authenticate");

            }

        }

    }


    public Socket getSocket() {
        return socket;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

}
