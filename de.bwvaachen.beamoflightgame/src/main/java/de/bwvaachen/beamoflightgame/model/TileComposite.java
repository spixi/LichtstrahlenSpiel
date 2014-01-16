package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditListener;

public interface TileComposite {
	public void addChangeListener(ChangeListener cl);
	public void addUndoableEditListener(UndoableEditListener ul);
	public void removeChangeListener(ChangeListener cl);
	public void removeUndoableEditListener(UndoableEditListener ul);
}
