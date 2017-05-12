package no.sonat.fubar.model;

import java.util.ArrayList;
import java.util.List;

import no.sonat.fubar.agent.Move;

public class MazeCell {
	public final Coordinate pos;
	public final boolean isNorthOpen;
	public final boolean isSouthOpen;
	public final boolean isWestOpen;
	public final boolean isEastOpen;

	public MazeCell(Coordinate pos, boolean isNorthOpen, boolean isSouthOpen, boolean isWestOpen, boolean isEastOpen

	) {
		this.pos = pos;
		this.isNorthOpen = isNorthOpen;
		this.isSouthOpen = isSouthOpen;
		this.isWestOpen = isWestOpen;
		this.isEastOpen = isEastOpen;
	}

	boolean isOrigin() {
		return pos.isOrigin();
	}

	public MazeCell copy() {
		// TODO Auto-generated method stub
		return new MazeCell(pos.copy(), isNorthOpen, isSouthOpen, isWestOpen, isEastOpen);
	}

	public List<Move> possibleMoves() {
		List<Move> move = new ArrayList<Move>();
		if (isNorthOpen) {
			move.add(Move.NORHT);
		}

		if (isSouthOpen) {
			move.add(Move.SOUTH);
		}

		if (isEastOpen) {
			move.add(Move.EAST);
		}

		if (isWestOpen) {
			move.add(Move.WEST);
		}
		return move;
	}

	public Move getDeadEndDirection() {
		Move m = null;

		return m;
	}
}
