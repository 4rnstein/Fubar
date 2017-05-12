package no.sonat.fubar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by helgeskjellevik on 12/05/2017.
 */
public class PlaceShipResult {


    @JsonProperty("class")
    public String name;

    public boolean ok;

    public int shipsLeftToPlace;
}
