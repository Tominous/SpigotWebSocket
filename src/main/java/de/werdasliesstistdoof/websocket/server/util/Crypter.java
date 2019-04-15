package de.werdasliesstistdoof.websocket.server.util;

import java.util.Base64;

public class Crypter {

    //First, here should be a AES-Encoding, but Java hate me.........
    //Fck Java

    public static String encode(String value) {

        return Base64.getEncoder().encodeToString(value.getBytes());

    }

    public static String decode(String value) {

        return new String(Base64.getDecoder().decode(value.getBytes()));

    }

}