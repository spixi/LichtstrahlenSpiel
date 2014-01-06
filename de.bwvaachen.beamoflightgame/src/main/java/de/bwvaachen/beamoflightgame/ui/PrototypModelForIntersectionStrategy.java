package de.bwvaachen.beamoflightgame.ui;

import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class PrototypModelForIntersectionStrategy extends BeamsOfLightPuzzleBoard {
	

		public PrototypModelForIntersectionStrategy() {
			init(4,4);
		}
		
	
		@Override
		public void init(int x, int y) {
			super.init(x,y);
			
			IChangeableTile t;
			
			// 3 e 1 e 
			// s n 1 e       
			// s 3 e e 
			// w 3 e e 
			
			putTile(new NumberTile(this,3,0,0));
			putTile(new LightTile(this,1,0));
			putTile(new NumberTile(this,1,2,0));
			putTile(new LightTile(this,3,0));
			putTile(new LightTile(this,0,1));
			putTile(new LightTile(this,1,1));
			putTile(new NumberTile(this,1,2,1));
			putTile(new LightTile(this,3,1));
			putTile(new LightTile(this,0,2));
			putTile(new NumberTile(this,3,1,2));
			putTile(new LightTile(this,2,2));
			putTile(new LightTile(this,3,2));
			putTile(new LightTile(this,0,3));
			putTile(new NumberTile(this,3,1,3));
			putTile(new LightTile(this,2,3));
			putTile(new LightTile(this,3,3));
	
			System.out.println(this);
			
		}

	

}
