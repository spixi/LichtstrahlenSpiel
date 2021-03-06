package de.bwvaachen.beamoflightgame.controller;
import static de.bwvaachen.beamoflightgame.i18n.I18N.*;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

public class IllegalPlacementException extends RuntimeException {
	IllegalPlacementException() {
		super(_("TurnNotPossible"));
	}
	
	IllegalPlacementException(String message) {
		super(message);
	}
}
