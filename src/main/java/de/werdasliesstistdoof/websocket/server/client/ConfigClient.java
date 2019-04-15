package de.werdasliesstistdoof.websocket.server.client;

import de.werdasliesstistdoof.websocket.server.util.Crypter;

public class ConfigClient {

    private String name;
    private String password;
    private String role;
    private boolean canWrtie;

    public ConfigClient(String name, String password) {

        this.name = name;
        this.password = password;
        this.role = "r";
        this.canWrtie = false;

    }

    public String canWrite() {

        return role;

    }

    public String getName() {

        return name;

    }

    public boolean isPassword(String password) {

        return Crypter.decode(this.password).equalsIgnoreCase(password);

    }

    public boolean isCanWrtie() {
        return canWrtie;
    }

    public void setCanWrtie(boolean canWrtie) {
        this.canWrtie = canWrtie;
        this.role = canWrtie ? "w" : "r";
    }

}