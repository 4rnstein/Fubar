package no.sonat.fubar;

import no.sonat.fubar.agent.Move;

/**
 * Copyright (c) All Rights Reserved.
 * User: AC30788 | Arnstein Flemmen
 * Date: 12.05.2017
 */
public class MovePlayer {

       public String  Action =  "MovePlayer";
                public String GameId;
        public String ClientId;
        public String Direction;//: "North" or "South" or "West" or "East",

    public MovePlayer(){

    }
    public MovePlayer(final Move move, final String ClientId, final String GameId){
        this.GameId = GameId;
        this.ClientId = ClientId;


        switch (move){

            case NORHT:
                Direction = "North";
                break;
            case SOUTH:
                Direction = "South";
                break;
            case WEST:
                Direction = "West";
                break;
            case EAST:
                Direction = "East";
                break;
        }

    }
}
