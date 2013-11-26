package de.bwvaachen.beamoflightgame.ui;

import java.util.ArrayList;
import java.util.Iterator;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;

public class PrototypModelFuerGUI implements IBeamsOfLightPuzzleBoard {

	ArrayList<ITile[]> tiles = new ArrayList<ITile[]>();
	
	
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

	
	
	public PrototypModelFuerGUI()
	{
		tiles.add(new ITile[]{new LightTile(0, 0),new LightTile(0, 1), new LightTile(0, 2)});
		tiles.add(new ITile[]{new NumberTile(2, 1, 0),new NumberTile(2, 1, 1), new LightTile(1, 2)});
		tiles.add(new ITile[]{new LightTile(2, 0),new LightTile(2, 1), new LightTile(2, 2)});
		tiles.add(new ITile[]{new LightTile(3, 0),new LightTile(3, 1), new NumberTile(5,3, 2)});
		
	}
	
	@Override
	public Iterator<ITile> iterator() {
		// TODO Auto-generated method stub
		return null;
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

		return getWidth()*getHeight();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasField(int row, int col) {
		return(tiles.size()>row && tiles.get(0).length > col);
	}

}
