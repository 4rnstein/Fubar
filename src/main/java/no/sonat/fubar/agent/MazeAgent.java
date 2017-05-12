package no.sonat.fubar.agent;

import no.sonat.fubar.model.Coordinate;
import no.sonat.fubar.model.MazeModel;

public class MazeAgent {
	public MazeModel model;

	public Coordinate position;

	public MazeAgent(MazeModel model) {
		this.model = model;

	}

	void start(MazeCell cell){
		position();
	}
}
