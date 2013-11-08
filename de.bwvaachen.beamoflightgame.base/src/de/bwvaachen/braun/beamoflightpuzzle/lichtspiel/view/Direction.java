/**
* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Frühholz, Georg Braun
*
* This file is part of the lichtspiel project.
*
* lichtspiel is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* lichtspiel is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with lichtspiel. If not, see <http://www.gnu.org/licenses/>.
*/

/**
* @file
* Direction of a tile
*/

package de.bwvaachen.braun.beamoflightpuzzle.lichtspiel.view;

public enum Direction {
	NORTH(0), EAST(90), SOUTH(180), WEST(-90), DARKNESS(Double.NaN);
	
	private double theta;
	
	private Direction(double theta) {
		this.theta = theta;
	}
	
	double getAnkle() {
		return theta;
	}
}
