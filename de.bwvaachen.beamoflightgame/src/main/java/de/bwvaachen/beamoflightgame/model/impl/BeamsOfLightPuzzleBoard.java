package de.bwvaachen.beamoflightgame.model.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class BeamsOfLightPuzzleBoard implements IBeamsOfLightPuzzleBoard {

	private int width, height;
	private ITile[][] tiles;
	private LinkedList<NumberTile> numberTiles;
	private ConcurrentLinkedQueue<ITile> tileQueue = new ConcurrentLinkedQueue<ITile>();

	@Override
	public Iterator<ITile> iterator() {
		return new Iterator<ITile>() {
			int x = 0, y = 0;

			public boolean hasNext() {
				return (x < getWidth());
			}

			public ITile next() {
				ITile next = getTileAt(x,y);
				if (++y==getHeight()) {
					y=0;
					x++;
				};
				return next;
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
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException {
		return tiles[x][y];
	}

	@Override
	public boolean isPlacementOfTileStatePossible(LightTileState state,
			int x, int y) {
		return true;
	}

	@Override
	public int getNumOfNumberTiles() {
		//return numberTiles.values().size();
		return numberTiles.size();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				sb.append(getTileAt(x,y)+" ");
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	@Override
	public boolean hasField(int x, int y) {
		return ( (x>(-1)) && (x<getWidth()) && (y>(-1)) && (y<getHeight()) );
	}

	@Override
	public void init(int x, int y) {
		width  = x;
		height = y;
		
		tiles       = new ITile[width][height];
		numberTiles = new LinkedList<NumberTile>();
	}

	@Override
	public void putTile(ITile t) {
		t.accept(new ITileVisitor() {
			public void visitLightTile(LightTile t) {
				BeamsOfLightPuzzleBoard.this.putTileInternal(t);
				
			}
			
			public void visitNumberTile(NumberTile t) {
				BeamsOfLightPuzzleBoard.this.putTileInternal(t);
				BeamsOfLightPuzzleBoard.this.numberTiles.add(t);
			}
		});
	}
	
	private void putTileInternal(ITile tile) {
		tiles[tile.getX()][tile.getY()] = tile;
	}

	/**
	 * @author Marius
	 * @param tile The tile to enqueue
	 * 
	 * A thread-safe method to add tiles, also if the board has not been initialized yet.
	 * The tiles are stored in an internal list and committed when the flush() method is called.
	 */
	@Override
	public void enqueueTile(ITile tile) {
		tileQueue.add(tile);
	}

	/**
	 * @author Marius
	 * 
	 * @see enqueueTile(ITile tile)
	 */
	@Override
	public void flush() {
		for(ITile t: tileQueue) {
			putTile(t);
		}
	}

}
