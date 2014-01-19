package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.File;
import java.util.Properties;

public class GameProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3492171893828000103L;
	public static final GameProperties INSTANCE = new GameProperties();
	private File file; //TODO
	
	static {
		INSTANCE.init();
	}

	private GameProperties(){
		String userHome = System.getProperty("user.home");
		file = new File(userHome + File.separator + "beamoflightgame.cfg");
	}
	
	private void init() {
		clear();
		put("newgame:density", -0.2);
		put("newgame:height", 6);
		put("newgame:width", 5);
	}
	
}
