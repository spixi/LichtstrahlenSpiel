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
		init(3,4);
	}
	

	@Override
	public void init(int x, int y) {
		super.init(x,y);
		
		IChangeableTile t;
		
		putTile(new LightTile(this,0,0));
		putTile(new LightTile(this,1,0));
		putTile(new LightTile(this,2,0));
		putTile(new NumberTile(this,2,0,1));
		putTile(new NumberTile(this,2,1,1));
		putTile(new LightTile(this,2,1));
		putTile(new LightTile(this,0,2));
		putTile(new LightTile(this,1,2));
		putTile(new LightTile(this,2,2));
		putTile(new LightTile(this,0,3));
		putTile(new LightTile(this,1,3));
		putTile(new NumberTile(this,5,2,3));

		t = (IChangeableTile)getTileAt(0,0);
		t.setState(LightTileState.NORTH);
		
		t = (IChangeableTile)getTileAt(2,1);
		t.setState(LightTileState.NORTH);

		t = (IChangeableTile)getTileAt(2,2);
		t.setState(LightTileState.NORTH);
		
		System.out.println(this);
		
	}

}
