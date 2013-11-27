/**
* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Fr�hholz, Georg Braun
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
* Test for the undo mechanism
*/

package de.bwvaachen.beamoflightpuzzle.test;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;

//@RunWith(PowerMockRunner.class)
@PrepareForTest({IBeamsOfLightPuzzleBoard.class})

public class UndoTest {
	@Before
	public void setUp() {

	}
	
	//TODO: Implement the controller first
	
	/*
	@Test
	public void test() {
		IBeamsOfLightPuzzleBoard    b = EasyMock.createStrictMock(IBeamsOfLightPuzzleBoard.class);
		
		ILightTile before;
		ILightTile after;
		
		Turn  t             = new Turn(b, 1, 2, before, after);
		
		b.setTileAt(1,2,after);
		expect(b.isPlacementOfTilePossible(before, 1, 2)).andReturn(true);
		b.setTileAt(1,2,before);
		expect(b.isPlacementOfTilePossible(after, 1, 2)).andReturn(true);
		b.setTileAt(1,2,after);
		
		replay(b);
		
		
		b.setTileAt(1,2,after);
		
		//First undo must work
		t.undo();

		//Second undo must fail
		try {

			t.undo();
		}
		catch(Exception e) {
			assertTrue(e instanceof CannotUndoException);
		}
		
		//First redo must work
		t.redo();
		try {
			t.redo();
		}
		
		//Second redo must fail
		catch(Exception e) {
			assertTrue(e instanceof CannotRedoException);
		}
		
		verify(b);
	}
	*/

}
