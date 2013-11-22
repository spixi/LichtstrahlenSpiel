package de.bwvaachen.beamoflightgame.model.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.easymock.EasyMock;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;

public class BeamsOfLightPuzzleBoard implements IBeamsOfLightPuzzleBoard {

	private int width, height;
	private TreeMap<Long, ITile> tiles;
	private TreeMap<Long, INumberTile> numberTiles;


	public BeamsOfLightPuzzleBoard(int width, int heigth, Iterable<INumberTile> nrs) {
		this.width = width;
		this.height = heigth;
		
		tiles       = new TreeMap<Long, ITile>();
		numberTiles = new TreeMap<Long, INumberTile>();
		
		for (INumberTile nt : nrs) {
			Long index = xyToIndex(nt.getCol(),nt.getRow());
			numberTiles.put(index, nt);
			tiles.put(index, nt);
		}
		
		for(int y=0; y<=height; y++) {
			for(int x=0; x<=height; y++) {
				long index = xyToIndex(x,y);
				if (!numberTiles.containsKey(index)) {
				}
				
				ILightTile tile;
				
				try{
				//tile = new LightTile(LightTileState.EMPTY)	
					
				tile = (ILightTile) Class.forName("de.bwvaachen.beamoflightgame.model.impl.LightTile")
						    .getConstructor(LightTileState.class).newInstance(LightTileState.EMPTY);
				}
				catch (Throwable e) {
					tile = EasyMock.createMock(ILightTile.class);
					EasyMock.expect(tile.getTileState()).anyTimes().andReturn(LightTileState.EMPTY);
				}
				
				tiles.put(xyToIndex(x,y), tile);
			}
		}
		
		
		//TODO
	}

	private long xyToIndex(int x, int y) {
		return (((long) x << 32) | ((long) y));
	}

	private int xFromIndex(long idx) {
		return (int) ((idx & 0xffffffff00000000L) >> 32);
	}

	private int yFromIndex(long idx) {
		return (int) (idx & 0x00000000ffffffffL);
	}

	@Override
	public Iterator<ITile> iterator() {
		return new Iterator<ITile>() {
			int x = 0, y = 0;

			public boolean hasNext() {
				return (x == width) && (y == height);
			}

			public ITile next() {
				if (x++ == width) {
					y++;
				    x = 0;
				}
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
		return tiles.get(xyToIndex(row,col));
	}

	@Override
	public boolean isPlacementOfTileStatePossible(LightTileState state,
			int row, int col) {
		return false;
	}

	@Override
	public ITile getTileByIndex(long index) {
		return tiles.get(index);
	}

	@Override
	public int getNumOfNumberTiles() {
		return numberTiles.values().size();
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
		// TODO Auto-generated method stub
		return (row>0) && (row<getWidth()) && (col>0) && (col<getWidth());
	}

}
