package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/


import java.util.ArrayList;
import java.util.Collections;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class CreateRandomBoard 
{
	public static void main(String[] args)
	{
		BeamsOfLightPuzzleBoard b = new BeamsOfLightPuzzleBoard();
		b.init(5, 6);
		createRandom(b);
	}
	
	/**
	 * Creates a random PuzzleBoard. Needs an initialized BeamOfLightPuzzleBoard as parameter and places NumberTiles in pseudo random order on it. Calls setNumbers().
	 * @author cfruehholz
	 * @param oBoard
	 * @return
	 */
	public static void createRandom(BeamsOfLightPuzzleBoard oBoard)
	{
		ArrayList<NumberTile> oNumTiles = new ArrayList<NumberTile>();
		double dblNumberTileCount;
		
		//TODO In Berechnung des NumberTileCount den Randomfaktor einbauen.
		dblNumberTileCount = (1.5 * Math.sqrt(oBoard.getHeight()*oBoard.getWidth()));//100*(Math.random()*20);

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
		
		System.out.println(oBoard);
		setNumbers(oNumTiles);
		System.out.println(oBoard);
	}

	/**
	 * An algorithm to determine the strength of Lightmachines (NumberTiles). Needs an ArrayList of NumberTiles which are already put on a BeamOfLightPuzzleBoard.
	 * @author cfruehholz
	 * @param oNumTiles
	 */
	public static void setNumbers(ArrayList<NumberTile> oNumTiles)
	{
		IBeamsOfLightPuzzleBoard oBoard = oNumTiles.get(0).getBoard();
		int iCountOfLightTiles = (oBoard.getHeight()*oBoard.getWidth())-oBoard.getNumOfNumberTiles();
		int iNumber = 0;

		Collections.shuffle(oNumTiles);
		
		for (int i = 0; i < oNumTiles.size(); i++)
		{
			
			BoardTraverser oTraverser = new BoardTraverser(oNumTiles.get(i));	
			
			while (oTraverser.shift(LightTileState.NORTH.getTraverseDirection()))
			{
				if(oTraverser.get() == null && oBoard.hasField(oTraverser.getX(), oTraverser.getY()))
				{
					oBoard.putTile(new LightTile(oBoard, oTraverser.getX(), oTraverser.getY()));
					iNumber++;
					iCountOfLightTiles--;
				}
				else
				{
					break;
				}
			}
			oTraverser.reset();
			while (oTraverser.shift(LightTileState.EAST.getTraverseDirection()))
			{
				if(oTraverser.get() == null && oBoard.hasField(oTraverser.getX(), oTraverser.getY()))
				{
					oBoard.putTile(new LightTile(oBoard, oTraverser.getX(), oTraverser.getY()));
					iNumber++;
					iCountOfLightTiles--;
				}
				else
				{
					break;
				}
			}
			oTraverser.reset();
			while (oTraverser.shift(LightTileState.SOUTH.getTraverseDirection()))
			{
				if(oTraverser.get() == null && oBoard.hasField(oTraverser.getX(), oTraverser.getY()))
				{
					oBoard.putTile(new LightTile(oBoard, oTraverser.getX(), oTraverser.getY()));
					iNumber++;
					iCountOfLightTiles--;
				}
				else
				{
					break;
				}
			}
			oTraverser.reset();
			while (oTraverser.shift(LightTileState.WEST.getTraverseDirection()))
			{
				if(oTraverser.get() == null && oBoard.hasField(oTraverser.getX(), oTraverser.getY()))
				{
					oBoard.putTile(new LightTile(oBoard, oTraverser.getX(), oTraverser.getY()));
					iNumber++;
					iCountOfLightTiles--;
				}
				else
				{
					break;
				}
			}
			
			oNumTiles.get(i).setState(new NumberTileState(iNumber), true);
			iNumber = 0;
			oTraverser.reset();
			
			if(iCountOfLightTiles == 0)
				break;
		}
	}
}
