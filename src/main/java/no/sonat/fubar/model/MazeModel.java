package no.sonat.fubar.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author lennart IMPORTNAT: The origin (cell 0,0) is where we started from
 */
public class MazeModel {

	public Map<Coordinate, MazeCell> cells;

	public MazeModel() {
		cells = new HashMap<Coordinate, MazeCell>();
	}

	public MazeCell getCellAt(Coordinate pos) {

		return null;

	}

	public MazeCell getCellAt(int x, int y) {
		return getCellAt(new Coordinate(x, y));
	}
}
