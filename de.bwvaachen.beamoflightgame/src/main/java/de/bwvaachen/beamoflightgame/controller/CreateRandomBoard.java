package de.bwvaachen.beamoflightgame.controller;

import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class CreateRandomBoard 
{
	public BeamsOfLightPuzzleBoard createRandom(BeamsOfLightPuzzleBoard oBoard)
	{
		//1,5*math.sqrt(x,y)+-20%
		//TODO Tiles Holen, Anzahl NumberTiles berechnen, NumberTiles Random setzen, LightTiles auffüllen, Zahlen vergeben
		double dblNumberTileCount;
		dblNumberTileCount = (1.5 * Math.sqrt(oBoard.getHeight()*oBoard.getWidth())/100*(Math.random()*20));
		oBoard.putTile(oBoard.getTileAt((int)(oBoard.getHeight()*Math.random()), (int)(oBoard.getWidth()*Math.random())));		
		
		return oBoard;
	}
	
}
