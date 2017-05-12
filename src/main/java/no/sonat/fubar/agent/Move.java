package no.sonat.fubar.agent;

public enum Move {
	NORHT, SOUTH, WEST, EAST;

	public Move oposite() {
		switch (this) {
		case NORHT:
			return Move.SOUTH;

		case SOUTH:
			return Move.NORHT;

		case EAST:
			return Move.WEST;

		case WEST:
			return Move.EAST;

		}
		return null;

	}
}
