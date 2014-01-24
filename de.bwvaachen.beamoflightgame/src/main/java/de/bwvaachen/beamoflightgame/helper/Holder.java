package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

/**
* Holder for any object types
*
* <P>This is a helper object which allows call-by-value for non-primitive data types.
* <P>Final holders are usable for passing arguments to anonymous inner classes.
*
* @author Marius
* @version 1.0
* @param <T> Any object type
 */
public final class Holder<T> {
	public T value;
	public Holder(final T initialValue) {
		this.value = initialValue;
	}
}
