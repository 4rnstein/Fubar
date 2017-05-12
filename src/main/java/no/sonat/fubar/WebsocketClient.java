package no.sonat.fubar;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * Websocket klient eksempel.
 * <p/>
 * Hvis du bygger med Maven legg dette i pom.xml:
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class WebsocketClient {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private final String username = "ateam";
    private String ClientId;
    private String GameId;

    private final CountDownLatch closeLatch;

    @SuppressWarnings("unused")
    private Session session;

    public WebsocketClient(final String GameId) {
        this.GameId = GameId;
        this.closeLatch = new CountDownLatch(1);

        final String destUri = String.format("ws://sonatmazeserver.azurewebsites.net/api/Maze/MazePlayer?username=%s&gameId=%s", username, GameId);

        final WebSocketClient client = new WebSocketClient();

        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(this, echoUri, request);

            System.out.printf("Connecting to : %s%n", echoUri);
            //socket.awaitClose(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public MovePlayer getNextMove(final NextMove nextMove) {
        final MovePlayer movePlayer = new MovePlayer();
        movePlayer.ClientId = ClientId;
        movePlayer.Direction = "East";
        movePlayer.GameId = GameId;

        return movePlayer;
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
//
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {

        try {

            if (msg.contains("ClientRegistered")) {

                Message obj = objectMapper.readValue(msg, Message.class);
                System.out.println(obj.ClientId);
                ClientId = obj.ClientId;
            } else if (msg.contains("NextMove")) {
                NextMove nextMove = objectMapper.readValue(msg, NextMove.class);
                System.out.println(nextMove);

                session.getRemote().sendStringByFuture(objectMapper.writeValueAsString(getNextMove(nextMove)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Got msg: %s%n", msg);
    }
}