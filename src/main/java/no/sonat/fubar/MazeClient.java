package no.sonat.fubar;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import no.sonat.fubar.agent.MazeAgentStack;
import no.sonat.fubar.agent.Move;
import no.sonat.fubar.model.MazeModel;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class MazeClient {
    private final ObjectMapper objectMapper;
    private final CountDownLatch closeLatch;
    private final MazeAgentStack mazeAgent;
    private final String GameId;

    private String ClientId;
    private Session session;

    public MazeClient(final String GameId, final String username) throws Exception {
        this.GameId = GameId;
        this.closeLatch = new CountDownLatch(1);
        this.objectMapper = new ObjectMapper();
        this.mazeAgent = new MazeAgentStack(new MazeModel());

        final WebSocketClient client = new WebSocketClient();
        client.start();
        client.connect(this, new URI(String.format("ws://sonatmazeserver.azurewebsites.net/api/Maze/MazePlayer?username=%s&gameId=%s", username, GameId)), new ClientUpgradeRequest());
    }

    public MovePlayer getNextMove(final NextMove nextMove) {
        final Move move = mazeAgent.makeMove(nextMove.getMazeCell());
        return new MovePlayer(move, ClientId, GameId);
    }

    @SuppressWarnings("unused")
    @OnWebSocketMessage
    public void onMessage(final String msg) {
        System.out.printf("Got msg: %s%n", msg);
        try {
            if (msg.contains("ClientRegistered")) {
                ClientId = objectMapper.readValue(msg, ClientRegistered.class).ClientId;
                System.out.println("ClientId: " + ClientId);
            } else if (msg.contains("NextMove")) {
                final NextMove nextMove = objectMapper.readValue(msg, NextMove.class);
                MovePlayer nextMove1 = getNextMove(nextMove);
                System.out.println("Moving " + nextMove1.Direction);
                session.getRemote().sendStringByFuture(objectMapper.writeValueAsString(nextMove1));
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
    public boolean awaitClose(final int duration, final TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @SuppressWarnings("unused")
    @OnWebSocketClose
    public void onClose(final int statusCode, final String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @SuppressWarnings("unused")
    @OnWebSocketConnect
    public void onConnect(final Session session) {
        System.out.printf("Got connect: %s%n", session);
        this.session = session;
    }
}