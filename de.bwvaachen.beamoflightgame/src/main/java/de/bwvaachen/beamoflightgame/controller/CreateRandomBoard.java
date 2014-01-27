package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
 */


import java.util.Collections;
import java.util.List;
import java.util.Random;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.IndexedSet;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import static de.bwvaachen.beamoflightgame.i18n.I18N.*;

public class CreateRandomBoard 
{
	public static void main(String[] args)
	{
		try {
			IBeamsOfLightPuzzleBoard b =
					createRandom(5,6,-0.2,false);
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Creates a random PuzzleBoard. Needs an initialized BeamOfLightPuzzleBoard as parameter and places NumberTiles in pseudo random order on it. Calls setNumbers().
	 * @author cfruehholz
	 * @param oBoard
	 * @return
	 * @throws CouldNotCreatePuzzleException 
	 */
	public static IBeamsOfLightPuzzleBoard createRandom(final int width, final int height, double density, boolean allowZeroTiles) throws CouldNotCreatePuzzleException
	{
		IndexedSet<NumberTile> oNumTiles = new IndexedSet<NumberTile>();
		Random heightRandom = new Random(System.nanoTime());
		Random widthRandom  = new Random(System.nanoTime() ^ -1L);

		IBeamsOfLightPuzzleBoard oBoard = new BeamsOfLightPuzzleBoard();
		boolean boardOK = false;

		int iteration = 0;
		int MAX_ITERATIONS = 250000;

		while(! boardOK) {
			oBoard.init(width,height);

			if((iteration++) == MAX_ITERATIONS) {
				throw new CouldNotCreatePuzzleException(_("MaxNoOfIterationsExceeded"));
			}


			double randomHelper;

			//The Gaussian random generator ensures that the tiles are equally derivated
			//to the x and the y direction
			oNumTiles.clear();

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

			//the board is impossible to solve if there is not at least one tile in each row or in each line
			if(oNumTiles.size() < Math.min(width, height))
				continue;

			boardOK = setNumbers(oNumTiles, allowZeroTiles);
		}



		return oBoard;
	}

	/**
	 * An algorithm to determine the strength of Lightmachines (NumberTiles). Needs an ArrayList of NumberTiles which are already put on a BeamOfLightPuzzleBoard.
	 * @author cfruehholz
	 * @author mSpix
	 * @param oNumTiles
	 */
	public static boolean setNumbers(IndexedSet<NumberTile> oNumTiles, boolean allowZeroTiles)
	{
		//A puzzle with zero NumberTiles makes no sense
		if(oNumTiles.size() == 0) return false;
		IBeamsOfLightPuzzleBoard oBoard = oNumTiles.get(0).getBoard();
		int iCountOfLightTiles = (oBoard.getHeight()*oBoard.getWidth())-oBoard.getNumOfNumberTiles();
		int iNumber = 0;
		int iPutTiles = oNumTiles.size();
		boolean boardOk;
		List<LightTileState> allDirections = LightTileState.allDirections();

		for (int i = 0; i < oNumTiles.size(); i++)
		{
			BoardTraverser oTraverser = new BoardTraverser(oNumTiles.get(i));

			Collections.shuffle(allDirections);

			for(LightTileState lts: allDirections){
				while (oTraverser.shift(lts.getTraverseDirection()))
				{
					if(oTraverser.get() == null)
					{
						new LightTile(oBoard, oTraverser.getX(), oTraverser.getY()).put();
						iNumber++;
						iPutTiles++;
						iCountOfLightTiles--;
						//A little bit more random. This allows a puzzles where a 
						//The beam stops before reaching another tile.
						//example: 2 e e w w 2
						//
						//Without this tweak, we always would generate
						// 4 e e e e 0  or  0 w w w w 4
						if(Math.random() > 0.5) break;
					}
					else
					{
						break;
					}
				}
				oTraverser.reset();
			}

			oNumTiles.get(i).setState(new NumberTileState(iNumber), true);
			iNumber = 0;
			oTraverser.reset();

			if(iCountOfLightTiles == 0)
				break;
		}

		boardOk = true;

		if(!allowZeroTiles) {
			for(NumberTile nt: oNumTiles) {
				boardOk = boardOk && (nt.getNumber() != 0);
			}
		}

		//Check whether all tiles have been set
		boardOk = boardOk && (iPutTiles == (oBoard.getHeight() * oBoard.getWidth()));

		return boardOk;
	}
}
