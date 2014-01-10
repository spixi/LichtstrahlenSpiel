package de.bwvaachen.beamoflightgame.controller;

import java.util.ArrayList;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class CreateRandomBoard 
{
	public static void main(String[] args)
	{
		BeamsOfLightPuzzleBoard b = new BeamsOfLightPuzzleBoard();
		b.init(5, 6);
		System.out.println(createRandom(b));

	}
	public static BeamsOfLightPuzzleBoard createRandom(BeamsOfLightPuzzleBoard oBoard)
	{
		//1,5*math.sqrt(x,y)+-20%
		//TODO Tiles Holen, Anzahl NumberTiles berechnen, NumberTiles Random setzen, LightTiles auffüllen, Zahlen vergeben
		double dblNumberTileCount;
		dblNumberTileCount = (1.5 * Math.sqrt(oBoard.getHeight()*oBoard.getWidth()));//100*(Math.random()*20);
		final ArrayList<NumberTile> oNumTiles = new ArrayList<NumberTile>();


		for(int i = 0; i < (int)dblNumberTileCount; i++)
		{
			int iHeight = (int)((oBoard.getHeight()*Math.random())-1);

			if(i >= oBoard.getWidth())
			{
				int iWidth;
				do
				{
					iWidth = (int)((oBoard.getWidth()*Math.random())-1);
					iHeight = (int)((oBoard.getHeight()*Math.random())-1);

					if(oBoard.getTileAt(iWidth, iHeight) == null)
					{
						oNumTiles.add(new NumberTile(oBoard, 0, iWidth, iHeight));
					}
				}
				while(oBoard.getTileAt(iWidth, iHeight) != null);
				oBoard.putTile(oNumTiles.get(i));
			}
			else
			{
				oNumTiles.add(new NumberTile(oBoard, 0, i, iHeight));
				oBoard.putTile(oNumTiles.get(i));
			}
		}
		return oBoard;
	}

	public void setNumbers(BeamsOfLightPuzzleBoard oBoard, ArrayList<NumberTile> oNumTiles)
	{
		int iNumber = 0;
		for (int i = 0; i < oNumTiles.size(); i++)
		{
			BoardTraverser oTraverser = new BoardTraverser(oBoard, oNumTiles.get(i).getX(), oNumTiles.get(0).getY());	
			for(int j = 0; j < 4; j++)
			{
				oTraverser.shift(LightTileState.NORTH.getTraverseDirection());
			}
			
			//TODO Von jedem NumberTile in alle Himmelsrichtungen traversieren so weit möglich und daraus Zahl errechnen.
		}
		
	}
}
