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

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.bwvaachen.beamoflightgame.helper.IndexedMap;


public class IndexedMapTest {
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
		IndexedMap<String,Integer> map = new IndexedMap<String,Integer>();
		map.put("Antwort", 42);
		assertEquals("Pr�fe IndexedMap.put()", new Integer(42), map.get("Antwort"));
		
		map.put("Hallo", 10);
		assertEquals("Pr�fe IndexedMap.getValueByIndex()", new Integer(10), map.getValueByIndex(1));
		
		map.remove("Antwort");
		assertEquals("Pr�fe IndexedMap.remove()", new Integer(10), map.getValueByIndex(0));
		
	}

}
