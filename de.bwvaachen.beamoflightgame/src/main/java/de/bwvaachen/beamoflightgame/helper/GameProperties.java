package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Properties;

public class GameProperties extends Properties {
	public static final GameProperties INSTANCE = new GameProperties();
	private File file;
	private SoftReference<BufferedOutputStream> outputStream;
	
	{
		String userHome = System.getenv("user.home");
		
		file = new File(userHome + File.separator + "beamoflightgame.cfg");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
			reset();
		}
		else {
			try {
				loadFromXML(new BufferedInputStream(new FileInputStream(file)));
			} catch (Exception e) {
				reset();
			}
		}
	}
	
	@Override
	public Object put(Object key, Object value) {
		Object oldValue = super.put(key, value);
		
		if(outputStream.get() == null) {
			try {
				outputStream = new SoftReference<BufferedOutputStream>(new BufferedOutputStream(new FileOutputStream(file)));
			} catch (FileNotFoundException e) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
				}
			}
		}
		
		if (oldValue != value) {
			try {
				storeToXML(outputStream.get(), (String) null);
			} catch (IOException e) {
			}
		}
		
		return oldValue;
	}
	
	public void reset() {
		clear();
		
		//Initial values here.
		put("randomFactor", 20);
		put("lightTileDensity", 1.5D);
	}
	
	
	
}
