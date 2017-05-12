package no.sonat.fubar.agent;

import java.util.List;
import java.util.Random;

import no.sonat.fubar.model.Coordinate;
import no.sonat.fubar.model.MazeCell;
import no.sonat.fubar.model.MazeModel;

public class MazeAgent {
	public MazeModel model;

	public Coordinate position;
	public Coordinate lastMove;

	private Random r = new Random();

	public MazeAgent(MazeModel model) {
		this.model = model;

	}

	public void start() {
		position = new Coordinate(0, 0);
	}

	public Move makeMove(MazeCell cell) {
		List<Move> moves = cell.possibleMoves();

		return moves.get(r.nextInt(moves.size()));
	}

}
