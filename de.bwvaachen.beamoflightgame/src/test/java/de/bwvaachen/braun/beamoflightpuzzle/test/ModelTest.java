///**
//* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Frühholz, Georg Braun
//*
//* This file is part of the lichtspiel project.
//*
//* lichtspiel is free software: you can redistribute it and/or modify
//* it under the terms of the GNU General Public License as published by
//* the Free Software Foundation, either version 2 of the License, or
//* (at your option) any later version.
//*
//* lichtspiel is distributed in the hope that it will be useful,
//* but WITHOUT ANY WARRANTY; without even the implied warranty of
//* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//* GNU General Public License for more details.
//*
//* You should have received a copy of the GNU General Public License
//* along with lichtspiel. If not, see <http://www.gnu.org/licenses/>.
//*/
//
///**
//* @file
//* Test for the model
//*/
//package de.bwvaachen.braun.beamoflightpuzzle.test;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.BufferedWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutput;
//import java.io.ObjectOutputStream;
//import java.util.Random;
//import java.util.zip.ZipInputStream;
//import java.util.zip.ZipOutputStream;
//
//import lzma.sdk.lzma.Decoder;
//import lzma.sdk.lzma.Encoder;
//import lzma.streams.LzmaInputStream;
//import lzma.streams.LzmaOutputStream;
//
//import static org.easymock.EasyMock.*;
//import org.junit.*;
//import static org.junit.Assert.*;
//
//import de.bwvaachen.beamoflightgame.model.LightTileState;
//
//public class ModelTest {
//		@Before
//		public void setUp() {
//
//		}
//		
//		@After
//		public void tearDown() {
//			
//		}
//		
//		@BeforeClass
//		public static void setUpBeforeClass() {
//			
//		}
//		
//		@AfterClass
//		public static void setUpAfterClass() {
//			
//		}
//		
//		@Test
//		public void test() throws IOException, ClassNotFoundException {
//			//Wo ist die Test-Suite nur hin???
//			
//			File tmpfile = File.createTempFile("testSerialization", ".tmp");
//			//tmpfile.deleteOnExit();
//			
//			FileOutputStream  fos    = new FileOutputStream(tmpfile);
//			LzmaOutputStream  los    = new LzmaOutputStream.Builder(fos).build();
//			BufferedOutputStream bos = new BufferedOutputStream(los);
//			ObjectOutputStream oos   = new ObjectOutputStream(bos);
//			
//			Object[] expected = new Object[]{LightTileState.EAST, LightTileState.NORTH, LightTileState.SOUTH, LightTileState.WEST, LightTileState.EMPTY, null };
//			Object[] actual;
//
//			System.out.println(tmpfile.toString());
//			
//			oos.writeObject(expected);
//			oos.flush();
//			oos.close();
//			
//			FileInputStream   fis   = new FileInputStream(tmpfile);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			LzmaInputStream   lis   = new LzmaInputStream(bis, new Decoder());
//			ObjectInputStream ois   = new ObjectInputStream(lis);
//			
//			actual = (Object[]) ois.readObject();
//			ois.close();
//
//			assertArrayEquals("Prüfe, ob die Serialisierung vom LightTileState funktioniert", expected, actual);
//		}
//
//
//}
