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

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.PrototypModelFuerGUI;

//@RunWith(PowerMockRunner.class)
@PrepareForTest({IBeamsOfLightPuzzleBoard.class})

public class UndoTest {
	@Before
	public void setUp() {

	}
	
	//TODO: Implement the controller first
	
	
	@Test
	public void test() {
		BeamsOfLightPuzzleBoard    b =
				new PrototypModelFuerGUI();
		TurnUndoManager um  = new TurnUndoManager();
		
		b.addChangeListener(um);
		
		((IChangeableTile) b.getTileAt(1, 2)).setState(LightTileState.NORTH);

		
		System.out.println(um);
	}
	

}
