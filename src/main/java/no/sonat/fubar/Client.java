package no.sonat.fubar;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Created by helge on 19.02.2015.
 */
public class Client {

    public static String uri = "ws://sonat-battleships.herokuapp.com/game/test3/socket";

    private WebSocketClient wsClient;

    public void connect(String username, String gameId) throws Exception {
        wsClient = new WebSocketClient(new URI(String.format("ws://sonatmazeserver.azurewebsites.net/api/Maze/MazePlayer?username=%s&gameId=%s", username, gameId))) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                onOpenEvent();
            }

            @Override
            public void onMessage(String s) {
                onMessageEvent(s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                onCloseEvent(i, s, b);
            }

            @Override
            public void onError(Exception e) {
                onErrorEvent(e);
            }
        };

        wsClient.connect();
    }

    public static void main(String[] args) throws Exception {
        new Client().connect("ateeam", "7751");


        Thread.sleep(1000000);
    }

    private void onOpenEvent() {
        System.out.println("Web socket open");

        placeShip();
    }

    private void onMessageEvent(String message) {
        System.out.println(String.format("Message received: %1$s", message));

        Object o = convertToObject(message);

        if (o instanceof PlaceShipResult) {
            handlePlaceShipResult(o);
        }
    }

    private void onCloseEvent(int i, String s, boolean b) {
        System.out.println("Web socket close");
    }

    private void onErrorEvent(Exception e) {
        System.out.println("web socket error");
        e.printStackTrace();
    }

    private void handlePlaceShipResult(Object o) {
//        boolean isDone = placement.removeShip();
//        if(!isDone) placeShip();
    }

    private void handleRuleBreakResult(Object o) {
        placeShip();
    }

    public void placeShip() {
//        Ship ship = placement.getNextShipToPlace();
//        sendMessage(new SetShipMessage(ship, Application.PLAYER));
    }

    private void handleGameIsStarted(Object o) {
//        GameIsStarted gis = (GameIsStarted)o;
//        if(StringUtils.equals(String.valueOf(gis.playerTurn), Application.PLAYER)) {
//            bombNextTarget();
//        }

        System.out.println("Game is started");
    }

    private void handleNextPlayerTurn(Object o) {
//        NextPlayerTurn npt = (NextPlayerTurn)o;
//
//        if(StringUtils.equals(String.valueOf(npt.playerTurn), Application.PLAYER)) {
//            bombNextTarget();
//        }
    }

    private void bombNextTarget() {
//        Cordinate c = board.getNextTarget();
//        currentTargetCoordinates = c;
//
//        DropBombMessage bomb = new DropBombMessage();
//        bomb.coordinate = c;
//        bomb.player = Application.PLAYER;
//
//        sendMessage(bomb);
    }

//    private void handleGameOver(Object o) {
//        System.out.println("Game is over. " + ((GameOver) o).andTheWinnerIs);
//    }

    private void handleBombResult(Object o) {
//        BombResult result = (BombResult)o;
//        board.bomb(currentTargetCoordinates, HitResult.valueOf(result.hit));
    }

    public void sendMessage(Object message) {
        wsClient.send(convertToString(message));
    }

    private String convertToString(Object message) {
        ObjectMapper mapper = new ObjectMapper();
        String value = "";
        try {
            value = mapper.writeValueAsString(message);
        } catch (Exception e) {
            System.out.println(e);
        }
        return value;
    }

    private Object convertToObject(String json) {
        List<Class> clazzes = new ArrayList<Class>();
        clazzes.add(PlaceShipResult.class);
//        clazzes.add(RuleBreakResult.class);
//        clazzes.add(GameIsStarted.class);
//        clazzes.add(SetShipMessage.class);
//        clazzes.add(BombResult.class);
//        clazzes.add(DropBombMessage.class);
//        clazzes.add(NextPlayerTurn.class);
//        clazzes.add(GameOver.class);

        Object result = null;

        for (Class clazz : clazzes) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                result = mapper.readValue(json, clazz);
            } catch (Exception e) {
                continue;
            }

            if (null != result) {
                break;
            }
        }
        return result;
    }
}