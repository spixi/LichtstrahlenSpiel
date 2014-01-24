package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TilesPanel extends JPanel {

	private Line2D 			line = null ;
	private RenderingHints 	rh ;
	
	public TilesPanel(){
		super();
		this.rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	public void setLine(Line2D line){
		this.line = line ;
	}
	
	@Override
	public void update(Graphics g){
		super.update(g);
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g ;
		
		super.paint(g2);
		
		if(line != null){	
			g2.setStroke(new BasicStroke(2f));
			g2.setRenderingHints(rh) ;
			g2.draw(line);
		}
	}
}
