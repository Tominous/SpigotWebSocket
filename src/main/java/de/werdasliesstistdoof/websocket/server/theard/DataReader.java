package de.werdasliesstistdoof.websocket.server.theard;

import de.werdasliesstistdoof.websocket.server.client.Client;
import de.werdasliesstistdoof.websocket.server.client.ClientManager;
import de.werdasliesstistdoof.websocket.server.util.Crypter;

import java.io.*;

public class DataReader extends Thread {

    Client client;
    public DataReader(Client client) {

        this.client = client;

    }

    @Override
    public void run() {

        while(!client.getSocket().isClosed()) {

            try {

                InputStream inputStream = client.getSocket().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String data;

                while((data = reader.readLine()) != null) {

                    try {

                        client.readData(Crypter.decode(data));

                    }catch(IllegalArgumentException exception) {/*Danke JS*/}

                }

            }catch(IOException exception) {

                try {

                    client.getSocket().close();


                }catch(IOException exception1) {

                    exception1.printStackTrace();

                }

                ClientManager.connected.remove(this.client);
                ClientManager.threads.remove(this);
                this.interrupt();

            }

        }

    }
}
