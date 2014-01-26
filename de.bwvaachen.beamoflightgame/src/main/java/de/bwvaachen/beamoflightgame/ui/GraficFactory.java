package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.bwvaachen.beamoflightgame.helper.AbstractTileVisitor;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.Holder;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.ui.RotatedIcon.Rotate;


/**
 * SimpleFactory to create images
 * @author Andreas
 */
public class GraficFactory {
	
	/**
	 * @author pauls_and
	 * @param meineKachel darzustellenden Kachel
	 * @return
	 */
	
	private IBeamsOfLightPuzzleBoard model;
	private BoardTraverser traverser;
	
	public GraficFactory(IBeamsOfLightPuzzleBoard model)
	{
		
		//needed to verify if lightbeam ends
		//nessessary parameter for Boardtraverser!
		this.model = model;
		traverser = new BoardTraverser(model, 0, 0);
		
		
		loadIcons();
	}
	
	
	/**
	 * Return the correct picture for the given ITile
	 * @param meineKachel Das ITile welches gesetzt werdem soll
	 * @return The needed picture
	 */
	public Icon getImage(ITile meineKachel)
	{
		final Holder<Icon> ii = new Holder<Icon>(null);
			meineKachel.accept(new AbstractTileVisitor(){
				public void visitNumberTile(NumberTile nt) {

					String url = String.format("themes/moon/%d.png", nt.getNumber());
					URL u = this.getClass().getClassLoader().getResource(url);
					
					ii.value = new ImageIcon(u);
				}
				
				public void visitLightTile(LightTile lt) {
					String url;
					
					//TODO: Refactor (switch-case in OOP ist ganz mies!)
					switch(lt.getTileState())
					{
					case NORTH:
					case EAST:
					case SOUTH:
					case WEST:
						if(isEnd(lt))
							url = "themes/moon/light2.png";
						else url = "themes/moon/light1.png";
						ii.value = rotateIcon(new ImageIcon(this.getClass().getClassLoader().getResource(url)),lt.getTileState().getRotation());
						break;
						
					case EMPTY:
						 url = "themes/moon/darkness.png";
						 ii.value = new ImageIcon(this.getClass().getClassLoader().getResource(url));
						 break;
						
					default:
						 ii.value = null; //nicht schoen aber einfach
					}//switch(lig.getTileState())
					
				}
			});
			
			return ii.value;

	}//public Icon getImage(ITile meineKachel)
	
	/**
	 * Gibt an, ob der Lichtstrahl zu Ende ist
	 * @param tile  Das LightTile welches geprueft werden soll
	 * @return true, wenn das Licht an diesem Punkt zu Ende ist
	 * @author pauls_and / Marius
	 */
	public boolean isEnd(LightTile tile)
	{
		//Traverser auf die Position der aktuellen Kachel schieben
		traverser.moveTo(tile.getX(), tile.getY());
		//Traverser-Richtung holen
		TraverseDirection td = tile.getTileState().getTraverseDirection();
		
		//end of the board: shift() will return false
		//else: has the neighbour tile another state?
		
		if(traverser.shift(td)) {
			
			return traverser.get().getTileState() != tile.getTileState();
		}
		else {
			//End of the board:
			return true;
		}
	}
	
	
	//Soll zukuenftig benutzt werden damit die Images nur 1 mal geladen werden
	private void loadIcons()
	{
		//TODO implement Fliegengewichtpattern!
	}
	/**
	 * Rotiert ein Icon um den angegebenn Wert
	 * @author pauls_and
	 * @param ii Das Ursprungicon
	 * @param r der Wert aus dem Enum um den gedreht werden soll
	 * @return das gedrehte Icon - als Icon
	 */
	private Icon rotateIcon(Icon ii, Rotate r)
	{
		RotatedIcon ri = new RotatedIcon(ii, r);
		return ri;
	}
}
