package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Eine Klasse die das JPanel um Eigenschaften erweitert, die in der GUI hilfreich
 * sein k�nnen.
 * 
 * @author cm
 *
 */

@SuppressWarnings("serial")
public class TilePanel extends JPanel{

	private int 			col;
	private int 			row;
	private BufferedImage 	image;
	private TileState 		state;
	private int 			lightPower;
	
	/**
	 * Eigener Konstruktor
	 * @param row Die Zeile des Panels 
	 * @param col Die Spalte des Panels
	 * @throws IOException 
	 */
	public TilePanel(int col, int row){
		this.col = col ;
		this.row = row ;
		this.image = null ;
		this.state = TileState.EMPTY ;
		this.lightPower = 0 ;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public TileState getState(){
		return state ;
	}
	
	public int getLightPower(){
		return lightPower ;
	}
	
	public void reset(){
		this.image = null ;
		this.state = TileState.EMPTY;
		this.lightPower = 0 ;
		repaint();
	}
	
	public void setLightPower(int lightPower) throws IOException{
		this.lightPower += lightPower ;
		this.state = TileState.NUMBER ;
		this.setImage("resources/themes/moon/"+this.lightPower+".png");
	}
	
	public void setImage(String path) throws IOException{
		this.image = ImageIO.read(new File(path));
		repaint();
	}
	
	public void setImage(String path, double angle) throws IOException{
		this.image = BeamsOfLightEditor.rotate(ImageIO.read(new File(path)),angle) ;
		if(angle == 90.0 || angle == 270.0){
			this.state = TileState.H_LIGHT ;
		}else{
			this.state = TileState.V_LIGHT ;
		}
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		super.paintComponent(g2);
		
		if(image != null){
			g2.drawImage(image,0,0,null);
		}
	}
}
