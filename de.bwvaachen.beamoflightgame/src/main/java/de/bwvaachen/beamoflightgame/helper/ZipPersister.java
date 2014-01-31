package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;


/**
 * Saves the game as a ZIP-file
 * @author bWinzen, aPauls
 *
 */
public class ZipPersister implements IPersistenceHelper {

	private ICodec codec;

	public ZipPersister(ICodec saveCodec) {
		this.codec = saveCodec;
	}

	@SuppressWarnings("deprecation")
	@Override
	/**
	 * @author Andreas
	 * @return 0. ArrayElement = Board, 1.Element = Lösung
	 */
	public Pair<IBeamsOfLightPuzzleBoard[],List<Turn>> load(File path) throws IOException, WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		    ICodec codec = null;
			FileInputStream fileInputStream = new FileInputStream(path);
			ZipInputStream zipIn = new ZipInputStream(fileInputStream);
			ZipEntry entry;
			IBeamsOfLightPuzzleBoard board = null, solution = null;
			List<Turn> turns=null;
			
			Map<String,String> sections = new HashMap<String,String>();

			while ((entry = zipIn.getNextEntry()) != null) {
				StringWriter sw = new StringWriter();
				int data;
				while((data = zipIn.read()) != -1) {
					sw.append((char)data);
				}
				sections.put(entry.getName(),sw.toString());
			}

			//depricated
//			if(!sections.containsKey("codec")) throw new WrongCodecException();
			if(!sections.containsKey("board")) throw new IllegalStateException();
//			if(!sections.containsKey("turns")) throw new WrongCodecException();
			
			
			
			codec = //(ICodec)Class.forName(sections.get("codec")).newInstance();
					new de.bwvaachen.beamoflightgame.helper.SimpleASCIICodec();
			
			//TODO: Refactor this stuff, we could also try to get rid of the codec trash
			board 		= codec.boardFromInputstream(new StringBufferInputStream(sections.get("board")));
			solution 	= codec.boardFromInputstream(new StringBufferInputStream(sections.get("solution")));
			turns 		= codec.turnsFromInputstream(new StringBufferInputStream(sections.get("turns")),board);
			
			return new Pair<IBeamsOfLightPuzzleBoard[], List<Turn>>(new IBeamsOfLightPuzzleBoard[]{board,solution},turns);
	}

	@Override
	public void save(File path, IBeamsOfLightPuzzleBoard board, List<Turn> turns, IBeamsOfLightPuzzleBoard solution)
			throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(path);
		ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);
		try {

			zipOut.putNextEntry(new ZipEntry("codec"));
			zipOut.write(codec.getClass().toString().getBytes());
			zipOut.closeEntry();

			zipOut.putNextEntry(new ZipEntry("board"));
			codec.boardToOutputstream(zipOut, board);
			zipOut.closeEntry();

			zipOut.putNextEntry(new ZipEntry("turns"));
			codec.turnsToOutputstream(zipOut, turns);
			zipOut.closeEntry();
			
			zipOut.putNextEntry(new ZipEntry("solution"));
			codec.boardToOutputstream(zipOut, solution);
			zipOut.closeEntry();
			
			zipOut.finish();
			
			zipOut.flush();
			fileOutputStream.flush();
		} catch (Throwable t) {
			zipOut.close();
			fileOutputStream.close();
			throw t;
		}

	}

}
