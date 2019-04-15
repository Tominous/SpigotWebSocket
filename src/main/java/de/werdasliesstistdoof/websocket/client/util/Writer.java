package de.werdasliesstistdoof.websocket.client.util;

import static de.werdasliesstistdoof.websocket.client.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.server.util.Crypter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Writer {

    public static void send(String data, Socket socket) {

        try {

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(Crypter.encode("SWSP://" + data) + "\n");
            writer.flush();

        }catch(IOException exception) {

            System.out.println(getMessage("client.error.sending"));

        }

    }

}
