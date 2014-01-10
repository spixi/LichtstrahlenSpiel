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
* Template for JUnit tests
*/

package de.bwvaachen.beamoflightpuzzle.test;

import java.io.File;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.helper.SimpleASCIICodec;
import de.bwvaachen.beamoflightgame.helper.ZipPersister;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.PrototypModelForLonelyFieldStrategy;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Object.class})


public class PersistenceTest {
	@AfterClass
	public static void setUpAfterClass() {
		
	}
	
	@BeforeClass
	public static void setUpBeforeClass() {
		
	}
	
	@Before
	public void setUp() {

	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void test() {
		ZipPersister test = new ZipPersister(new SimpleASCIICodec());
		IBeamsOfLightPuzzleBoard board1 = new PrototypModelForLonelyFieldStrategy();
		System.out.println(board1);
		
		IBeamsOfLightPuzzleBoard board2 = null;
		try {
			test.save(new File("/tmp/Test.zip"), board1, new LinkedList<Turn>());
			board2 = test.load(new File("/tmp/Test.zip")).left;
			System.out.println(board2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(board1.toString(), board2.toString());
	}
	

}
