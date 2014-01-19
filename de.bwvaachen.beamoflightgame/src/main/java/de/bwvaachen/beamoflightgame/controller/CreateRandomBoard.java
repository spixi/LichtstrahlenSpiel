package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
		IBeamsOfLightPuzzleBoard b =
		createRandom(5,6,-0.2);
	}
	
	/**
	 * Creates a random PuzzleBoard. Needs an initialized BeamOfLightPuzzleBoard as parameter and places NumberTiles in pseudo random order on it. Calls setNumbers().
	 * @author cfruehholz
	 * @param oBoard
	 * @return
	 */
	public static IBeamsOfLightPuzzleBoard createRandom(final int width, final int height, double density)
	{
		IBeamsOfLightPuzzleBoard oBoard = new BeamsOfLightPuzzleBoard();
		oBoard.init(width,height);
		
		ArrayList<NumberTile> oNumTiles = new ArrayList<NumberTile>();
		Random heightRandom = new Random(System.nanoTime());
		Random widthRandom  = new Random(System.nanoTime() ^ -1L);
		
		double randomHelper;
		
		//The Gaussian random generator ensures that the tiles are equally derivated
		//to the x and the y direction
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				randomHelper = widthRandom.nextGaussian() * heightRandom.nextGaussian()
						      +density;
				if(Math.signum(randomHelper) == 1.0) {
					//If we encounter a positive number, we will place a NumberTile
					NumberTile nt = new NumberTile(oBoard,0,x,y);
					nt.put();
					oNumTiles.add(nt);
				}
				
			}
		}
		
		System.out.println(oBoard);
		setNumbers(oNumTiles);
		System.out.println(oBoard);
		
		return oBoard;
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
		List<LightTileState> allDirections = LightTileState.allDirections();

		Collections.shuffle(oNumTiles);
		
		for (int i = 0; i < oNumTiles.size(); i++)
		{
			
			BoardTraverser oTraverser = new BoardTraverser(oNumTiles.get(i));
			System.out.println("startX: " + oTraverser.getStartX() + " x: " + oTraverser.getX() + "\n" + "startY: " + oTraverser.getStartY() + " y: " + oTraverser.getY());
			
			Collections.shuffle(allDirections);
			
			for(LightTileState lts: allDirections){
			while (oTraverser.shift(lts.getTraverseDirection()))
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
			}
			
			iNumber = iNumber * 1 + 1;

			
			oNumTiles.get(i).setState(new NumberTileState(iNumber), true);
			iNumber = 0;
			oTraverser.reset();
			
			if(iCountOfLightTiles == 0)
				break;
		}
	}
}
