package de.bwvaachen.beamoflightgame.model.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import org.easymock.EasyMock;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class BeamsOfLightPuzzleBoard implements IBeamsOfLightPuzzleBoard {

	private int width, height;
	private ITile[][] tiles;
	private LinkedList<NumberTile> numberTiles;


	@Override
	public Iterator<ITile> iterator() {
		return new Iterator<ITile>() {
			int x = 0, y = 0;

			public boolean hasNext() {
				return (x == width) && (y == height);
			}

			public ITile next() {
				return getTileAt(x,y);
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
		return tiles[row][col];
	}

	@Override
	public boolean isPlacementOfTileStatePossible(LightTileState state,
			int row, int col) {
		return true;
	}

	@Override
	public int getNumOfNumberTiles() {
		//return numberTiles.values().size();
		return numberTiles.size();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for(int y=0; y<=height; y++) {
			for(int x=0; x<=height; y++) {
				sb.append(getTileAt(x,y));
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	@Override
	public boolean hasField(int row, int col) {
		return (row>0) && (row<getWidth()) && (col>0) && (col<getWidth());
	}


	@Override
	public void init(int rows, int cols, Iterable<NumberTile> nrs) {
		this.height = rows;
		this.width = cols;
		
		tiles = new ITile[rows][cols];
		
		for (NumberTile nt : nrs) {
			numberTiles.add(nt);
			tiles[nt.getRow()][nt.getCol()] = nt;
		}
		
		for(int y=0; y<=height; y++) {
			for(int x=0; x<=width; y++) {
				if(tiles[x][y] == null)
				  tiles[x][y] = new LightTile(x,y);
			}
		}
	}

	@Override
	@Deprecated 
	public ITile getTileByIndex(long index) {
		// TODO Auto-generated method stub
		return null;
	}

}
