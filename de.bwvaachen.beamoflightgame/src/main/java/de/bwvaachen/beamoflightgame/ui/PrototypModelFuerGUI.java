package de.bwvaachen.beamoflightgame.ui;

import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class PrototypModelFuerGUI extends BeamsOfLightPuzzleBoard {
	
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

	public PrototypModelFuerGUI() {
		init(4,3);
	}
	

	@Override
	public void init(int rows, int cols) {
		super.init(rows,cols);
		
		IChangeableTile t;
		
		putTile(new LightTile(0, 0));
		putTile(new LightTile(0, 1));
		putTile(new LightTile(0, 2));
		t = (IChangeableTile)getTileAt(0, 0);
		t.setState(LightTileState.NORTH);
		
		putTile(new NumberTile(2, 1, 0));
		putTile(new NumberTile(2, 1, 1));
		putTile(new LightTile(1, 2));
		putTile(new LightTile(2, 0));
		
		putTile(new LightTile(2, 1));
		t = (IChangeableTile)getTileAt(2,1);
		t.setState(LightTileState.NORTH);
		
		putTile(new LightTile(2, 2));
		t = (IChangeableTile)getTileAt(2,2);
		t.setState(LightTileState.NORTH);
		
		putTile(new LightTile(3, 0));
		putTile(new LightTile(3, 1));
		
		putTile(new NumberTile(5, 3, 2));
		
	}

}
