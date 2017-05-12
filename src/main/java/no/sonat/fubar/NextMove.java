package no.sonat.fubar;

import no.sonat.fubar.model.MazeCell;

/**
 * Copyright (c) All Rights Reserved.
 * User: AC30788 | Arnstein Flemmen
 * Date: 12.05.2017
 */
public class NextMove {
    public String Action = "NextMove";
    public String North;// = "Wall" or "Open",
    public String West;// = "Wall" or "Open",
    public String East;// = "Wall" or "Open",
    public String South;// = "Wall" or "Open",
    public String ClientId;
    public String GameId;

    public MazeCell getMazeCell() {
        return new MazeCell(null, North.equals("Open"), South.equals("Open"), West.equals("Open"), East.equals("Open"));
    }
}
