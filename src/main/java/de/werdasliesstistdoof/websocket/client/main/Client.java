package de.werdasliesstistdoof.websocket.client.main;

import static de.werdasliesstistdoof.websocket.client.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.client.thread.ConsoleReader;
import de.werdasliesstistdoof.websocket.client.thread.SocketReader;
import de.werdasliesstistdoof.websocket.client.util.Bundel;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private static boolean loggedIn = false;
    private static Socket socket;

    public static void main(String[] args) {

        String server = "localhost";
        Integer port = 25566;

        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("de"))
                Bundel.init("de");
            else
                Bundel.init("en");
        } else if(args.length == 2) {

            server = args[0];
            try {
                port = Integer.parseInt(args[1]);
            }catch(NumberFormatException exception) {
                System.out.println(getMessage("client.error.port"));
            }

        } else if(args.length == 3) {

            server = args[0];
            try {
               port = Integer.parseInt(args[1]);
            }catch(NumberFormatException exception) {
                System.out.println(getMessage("client.error.port"));
            }

            if (args[2].equalsIgnoreCase("de"))
                Bundel.init("de");
            else
                Bundel.init("en");

        } else
            Bundel.init("en");

        try {

            socket = new Socket(server, port);

        }catch(IOException exception) {

            System.out.println(getMessage("client.error.refused"));

        }

        Thread consoleReader = new Thread(new ConsoleReader());
        consoleReader.start();
        Thread socketReader = new Thread(new SocketReader());
        socketReader.start();


        System.out.println(getMessage("client.login"));

    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        Client.loggedIn = loggedIn;
    }

    public static Socket getSocket() {
        return socket;
    }
}
