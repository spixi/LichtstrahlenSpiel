package de.bwvaachen.beamoflightgame.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;


public class BeamsOfLightPuzzleBoard implements IBeamsOfLightPuzzleBoard {
	
	private int width, height;
	private TreeMap<Long,INumberTile> numberTiles;
	private ITile[] tiles;
	
	private long xyToIndex(int x, int y){
		return (((long) x << 32) | ((long) y));
	}
	
	private int xFromIndex(long idx){
		return (int)((idx & 0xffffffff00000000L) >> 32);
	}
	
	private int yFromIndex(long idx){
		return (int)(idx & 0x00000000ffffffffL);
	}

	@Override
	public Iterator<ITile> iterator() {
		return new Iterator<ITile>() {
			int x=0, y=0;
			
			public boolean hasNext() {
				return (x==width) && (y==width);
			}

			public ITile next() {
				ITile theTile = numberTiles.get(xyToIndex(x,y));
				if (theTile == null) throw new NoSuchElementException();
			
			 	if(y++==height) x++; y=0;
				return theTile;
			}

			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}
			
		};
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public ITile getTileAt(int row, int col) throws IndexOutOfBoundsException {
		return tiles[row*col];
	}

	@Override
	public boolean isPlacementOfTileStatePossible(LightTileState state,
			int row, int col) {
		return false;
	}

}
