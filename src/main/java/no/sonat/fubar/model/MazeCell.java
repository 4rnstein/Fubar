package no.sonat.fubar.model;

public class MazeCell {
	public int x;
	public int y;
	public boolean isNorthOpen;
	public boolean isSouthOpen;
	public boolean isWestOpen;
	public boolean isEastOpen;

	boolean isOrigin(){
		return ((0==x) && (0 == y));
	}
}
