package no.sonat.fubar.agent;

import java.util.List;
import java.util.Random;
import java.util.Stack;

import no.sonat.fubar.model.Coordinate;
import no.sonat.fubar.model.MazeCell;
import no.sonat.fubar.model.MazeModel;

public class MazeAgentStack {
	public MazeModel model;

	public Coordinate position;
	public Move lastMove;

	private Random r = new Random();

	private Stack<StackStep> stack = new Stack<StackStep>();

	public MazeAgentStack(MazeModel model) {
		this.model = model;

	}

	public void start() {
		position = null;
	}

	public Move makeMove(MazeCell cell) {
		List<Move> moves = cell.possibleMoves();
		if (null == position) {
			position = new Coordinate(0, 0);
			lastMove = moves.get(r.nextInt(moves.size()));
			model.putCellAt(cell);
			Move move = lastMove;
			stack.push(new StackStep(move));
			return lastMove;
		} else {
			model.putCellAt(cell);

			Move move = Move.WEST;

			if (moves.size() > 1) {
				do {
					move = moves.get(r.nextInt(moves.size()));
				} while (lastMove == move.oposite());
			} else {
				move = lastMove.oposite();
			}
			stack.push(new StackStep(move));
			return move;
		}
	}

}
