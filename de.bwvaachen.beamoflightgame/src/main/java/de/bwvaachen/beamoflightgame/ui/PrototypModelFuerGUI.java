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

	ArrayList<ITile[]> tiles = new ArrayList<ITile[]>();
	//maybe a Vector<ITile> would be better
	//We could determine an index with the formula
	//int x, y;
	//long index = (x*width)+y;
	
	
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
				// TODO Auto-generated method stub
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

		return tiles.get(row)[col];
	}

	@Override
	public boolean isPlacementOfTileStatePossible(LightTileState state,
			int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITile getTileByIndex(long index) {
		// We can drop this unneccessary method soon, 
		// because it is only confusing
		return null;
	}

	@Override
	public boolean hasField(int row, int col) {
		return(tiles.size()>row && tiles.get(0).length > col);
	}

	@Override
	public void init(int rows, int cols, Iterable<NumberTile> nrs) {
		tiles.add(new ITile[]{new LightTile(0, 0),new LightTile(0, 1), new LightTile(0, 2)});
		tiles.add(new ITile[]{new NumberTile(2, 1, 0),new NumberTile(2, 1, 1), new LightTile(1, 2)});
		tiles.add(new ITile[]{new LightTile(2, 0),new LightTile(2, 1), new LightTile(2, 2)});
		tiles.add(new ITile[]{new LightTile(3, 0),new LightTile(3, 1), new NumberTile(5,3, 2)});
	}

}
