package de.bwvaachen.beamoflightgame.helper;

import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public interface ITileVisitor {
	   public void visitLightTile(LightTile t);
	   public void visitNumberTile(NumberTile t);
}
