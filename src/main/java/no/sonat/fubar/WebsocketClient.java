package no.sonat.fubar;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * Websocket klient eksempel.
 *
 * Hvis du bygger med Maven legg dette i pom.xml:
 *


 *
 *
 *
 */
public class WebsocketClient {
    private static ObjectMapper objectMapper = new ObjectMapper();


    private String ClientId;


    public WebsocketClient(){

        String username = "ateam";
        String gameId = "1688";
        String destUri = String.format("ws://sonatmazeserver.azurewebsites.net/api/Maze/MazePlayer?username=%s&gameId=%s",username,gameId);

        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new WebsocketClient.SimpleEchoSocket();

        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);

            socket.

            System.out.printf("Connecting to : %s%n", echoUri);
            //socket.awaitClose(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
//        } finally {
//            try {
//                client.stop();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public static void main(String[] args) {
         new WebsocketClient();
    }

    /**
     * Basic Echo Client Socket
     */
    @WebSocket(maxTextMessageSize = 64 * 1024)
    public static   class SimpleEchoSocket {

        private final CountDownLatch closeLatch;

        @SuppressWarnings("unused")
        private Session session;

        public SimpleEchoSocket() {
            this.closeLatch = new CountDownLatch(1);
        }

        public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
            return this.closeLatch.await(duration, unit);
        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason) {
            System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
            this.session = null;
            this.closeLatch.countDown();
        }

        @OnWebSocketConnect
        public void onConnect(Session session) {
            System.out.printf("Got connect: %s%n", session);
            this.session = session;
            try {
                Future<Void> fut;
                fut = session.getRemote().sendStringByFuture("Hello");
                fut.get(2, TimeUnit.SECONDS);
                session.close(StatusCode.NORMAL, "I'm done");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @OnWebSocketMessage
        public void onMessage(String msg) {

            try {
                Message obj = objectMapper.readValue(msg,Message.class);
                System.out.println(obj.ClientId);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("Got msg: %s%n", msg);
        }
    }
}