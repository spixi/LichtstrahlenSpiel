/**
* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Frï¿½hholz, Georg Braun
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

import org.junit.*;
import static org.junit.Assert.*;

import de.bwvaachen.beamoflightgame.helper.IndexedMap;


public class IndexedMapTest {
	@Before
	public void setUp() {

	}
	
	@After
	public void tearDown() {
		
	}
	
	@BeforeClass
	public static void setUpBeforeClass() {
		
	}
	
	@AfterClass
	public static void setUpAfterClass() {
		
	}
	
	@Test
	public void test() {
		IndexedMap<String,Integer> map = new IndexedMap<String,Integer>();
		map.put("Antwort", 42);
		assertEquals("Prüfe IndexedMap.put()", new Integer(42), map.get("Antwort"));
		
		map.put("Hallo", 10);
		assertEquals("Prüfe IndexedMap.getValueByIndex()", new Integer(10), map.getValueByIndex(1));
		
		map.remove("Antwort");
		assertEquals("Prüfe IndexedMap.remove()", new Integer(10), map.getValueByIndex(0));
		
	}

}
