package de.bwvaachen.beamoflightgame.helper;

import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class AbstractTileVisitor implements ITileVisitor {
	   public void visitLightTile(LightTile t) {
		   visitOtherTile(t);
	   }
	   public void visitNumberTile(NumberTile t) {
		   visitOtherTile(t);
	   }
	   public void visitOtherTile(ITile t) {
		   
	   }
}
