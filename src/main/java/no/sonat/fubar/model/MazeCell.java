package no.sonat.fubar.model;

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
}
