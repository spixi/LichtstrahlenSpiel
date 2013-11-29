package de.bwvaachen.beamoflightgame.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.naming.OperationNotSupportedException;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;

public class PrototypModelFuerGUI implements IBeamsOfLightPuzzleBoard {

	ITile[][] tiles = new ITile[4][3];
		
	
	//	 _______________________
	//	|	  	|	  	|	  	|
	//	|  ---	|	_ 	|	  	|
	//	|	| 	|	| 	|	- 	|
	//	|	| 	|	| 	|	| 	|
	//	|___|___|___|___|___|___|
	//  |	  	|	  	|	| 	|
	//	|	  	|	  	|	| 	|
	//	|	 2	|	2	|	|	|
	//	|	 	|	  	|	| 	|
	//	|_______|_______|___|___|
	//	|	| 	|	| 	|	| 	|
	//	|	| 	|	| 	|	| 	|
	//	|	| 	|	| 	|	| 	|
	//	| ----	|	_ 	|	|	|
	//	|_______|_______|___|___|
	//	|	  	|	  	|	  	|
	//	|	  	|	  	|	  	|
	//	|	|---|-------|	5	|
	//	|	  	|	  	|	  	|
	//	|_______|_______|_______|

	
	@Override
	public Iterator<ITile> iterator() {
		
		//ugly, but working for now
		return new Iterator<ITile>() {
			int x=0, y=0;
			boolean endReached;

			@Override
			public boolean hasNext() {
				return hasField(x,y);
			}

			@Override
			public ITile next() {
				////Are we really required to double-check this?
				//if(! hasNext()) {
				//	throw new NoSuchElementException();
				//}
				
				ITile<ITileState> next = getTileAt(x,y);
				if(++y == getWidth()) {
					y = 0;
					x++;
				}
				return next;
			}

			@Override
			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
				
			}
			
			
		};
		
	}

	@Override
	public int getWidth() {

		return 3;
	}

	@Override
	public int getHeight() {

		return 4;
	}

	@Override
	public int getNumOfNumberTiles() {
		//the number should actually never change
		//later we could get rid of this ugly method here

		int num = 0;
		for(ITile t : this) {
			if(t.getTileState() instanceof LightTileState) {
				num++;
			}
		}
		
		return num;
	}

	@Override
	public ITile getTileAt(int row, int col) throws IndexOutOfBoundsException {

		return tiles[row][col];
	}

	@Override
	public boolean isPlacementOfTileStatePossible(LightTileState state,
			int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public ITile getTileByIndex(long index) {
		// We can drop this unneccessary method soon, 
		// because it is only confusing
		return null;
	}

	@Override
	public boolean hasField(int row, int col) {
		return(row <= getHeight() && col <= getWidth());
	}

	@Override
	public void init(int rows, int cols, Iterable<NumberTile> nrs) {
		tiles[0][0] = new LightTile(0, 0);
		tiles[0][1] = new LightTile(0, 1);
		tiles[0][2] = new LightTile(0, 2);
		
		tiles[1][0] = new NumberTile(2, 1, 0);
		tiles[1][1] = new NumberTile(2, 1, 1);
		tiles[1][2] = new LightTile(1, 2);
		
		tiles[2][0] = new LightTile(2, 0);
		tiles[2][1] = new LightTile(2, 1);
		tiles[2][2] = new LightTile(2, 2);
		
		tiles[3][0] = new LightTile(3, 0);
		tiles[3][1] = new LightTile(3, 1);
		tiles[3][2] = new NumberTile(5,3, 2);
		
		
		}

}
