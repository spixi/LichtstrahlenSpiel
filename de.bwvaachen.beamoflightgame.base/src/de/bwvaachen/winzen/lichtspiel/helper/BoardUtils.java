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
* Game board helper class
*/

package de.bwvaachen.winzen.lichtspiel.helper;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.bwvaachen.winzen.lichtspiel.model.IBoard;
import de.bwvaachen.winzen.lichtspiel.model.Tile;

public class BoardUtils {
	
	public Iterator<Tile> getRowIterator(final IBoard b, final int row) {
		return new Iterator<Tile>() {
			private int x = row, y = 0;
			
			public boolean hasNext() {
				return y<=b.getDimension().getHeight();
			}

			public Tile next() throws NoSuchElementException {
				try {
					return b.getTileAt(x,y++);
				}
				catch (IndexOutOfBoundsException e) {
					throw new NoSuchElementException();
				}
			}

			public void remove() throws UnsupportedOperationException {
					throw new UnsupportedOperationException();
			}
		};
	}
	
	public Iterator<Tile> getColumnIterator(final IBoard b, final int row) {
		return new Iterator<Tile>() {
			private int x = row, y = 0;
			
			public boolean hasNext() {
				return x<=b.getDimension().getWidth();
			}

			public Tile next() {
				try {
					return b.getTileAt(x++,y);
				}
				catch (IndexOutOfBoundsException e) {
						throw new NoSuchElementException();
				}
			}

			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}
		};
	}

}
