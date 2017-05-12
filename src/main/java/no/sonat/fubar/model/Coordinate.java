package no.sonat.fubar.model;

public class Coordinate {
	public final int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return x ^ (y * 31);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate)) {
			return false;
		}
		Coordinate p = (Coordinate) o;
		return (this.x == p.x && this.y == p.y);
	}

	public boolean isOrigin() {

		return ((0 == x) && (0 == y));
	}

	public Coordinate copy() {
		return new Coordinate(x, y);
	}
}
