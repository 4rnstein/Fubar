package no.sonat.fubar.model;

public class Coordinate {
	public int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return x ^ (y * 10000);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate)) {
			return false;
		}
		Coordinate p = (Coordinate) o;
		return (this.x == p.x && this.y == p.y);
	}
}
