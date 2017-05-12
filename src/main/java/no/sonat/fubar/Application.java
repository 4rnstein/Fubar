package no.sonat.fubar;

import no.sonat.fubar.model.Client;

public class Application {

    public static String PLAYER = "1";

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connect();

        Thread.sleep(1000000);
    }

}
