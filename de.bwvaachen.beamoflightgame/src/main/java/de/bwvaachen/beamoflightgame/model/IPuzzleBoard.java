package de.bwvaachen.beamoflightgame.model;

public interface IPuzzleBoard<T> extends Iterable<ITile<? extends T>> {
	public int getWidth();
	public int getHeight();
	public ITile<? extends T> getTileAt(int row, int col) throws IndexOutOfBoundsException;
	public void putTile(ITile<? extends T> tile, int row, int col);
	public void init(int rows, int cols);
}
