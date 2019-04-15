package de.werdasliesstistdoof.websocket.client.thread;

import static de.werdasliesstistdoof.websocket.client.util.Writer.send;
import static de.werdasliesstistdoof.websocket.client.util.Bundel.getMessage;

import de.werdasliesstistdoof.websocket.client.main.Client;
import de.werdasliesstistdoof.websocket.client.util.Writer;

import java.util.Scanner;

public class ConsoleReader extends Thread {

    static Integer currentProgress = 0;

    @Override
    public void run() {

        Scanner console = new Scanner(System.in);
        String data;

        //Login
        currentProgress = 0;
        String name = "";
        String password;

        while(true) {

            while((data = console.nextLine()) != null) {

                if(Client.isLoggedIn())
                    Writer.send("request.command/" + data, Client.getSocket());
                else {

                    if(currentProgress == 0)
                        name = data;
                    else if(currentProgress == 1) {

                        password = data;
                        send("request.authenticate/" + name + ";#-_-#;" + password, Client.getSocket());

                    }

                    currentProgress++;

                }

            }

        }

    }

    public static void resetLogin() {

        currentProgress = 0;
        System.out.println(getMessage("client.error.login"));

    }

}
