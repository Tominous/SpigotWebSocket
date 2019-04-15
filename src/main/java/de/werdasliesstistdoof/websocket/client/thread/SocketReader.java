package de.werdasliesstistdoof.websocket.client.thread;

import static de.werdasliesstistdoof.websocket.client.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.client.main.Client;
import de.werdasliesstistdoof.websocket.client.util.Crypter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SocketReader extends Thread {

    @Override
    public void run() {

        try {

            InputStream inputStream = Client.getSocket().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String data;

            while((data = reader.readLine()) != null) {

                data = Crypter.decode(data).replaceFirst("SWSP://", "");

                if(data.startsWith("send.message/"))
                    System.out.println(data.replaceFirst("send[.]message/", ""));
                else if(data.startsWith("data.authenticate"))
                    Client.setLoggedIn(true);
                else if(data.startsWith("failed.authenticate"))
                    ConsoleReader.resetLogin();
                else if(data.startsWith("close.socket")) {
                    System.out.println(getMessage("client.reloading"));
                    System.exit(1);
                }

            }

        }catch(IOException exception) {



        }

    }
}
