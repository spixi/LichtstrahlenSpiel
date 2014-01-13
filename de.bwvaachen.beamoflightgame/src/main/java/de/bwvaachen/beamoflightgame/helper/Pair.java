/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

package de.bwvaachen.beamoflightgame.helper;

/**
* Pair: Compose objects dynamically into a simple binary tree
*
* <P>This is a helper object which allows to compose objects into a simple tree.
* <P>It is usable if you want a method have more than one return value.
* <P>Example
* @code{
* 	Pair<Pair<String,Integer>,Boolean> foo() {
* 		return new Pair<Pair<String,Integer>,Boolean> (
* 			new Pair<String,Integer>("The answer is",42),true
* 		)
*   }
*   
*   Pair<Pair<String,Integer>,Boolean> tree = foo();
* }
* <P> @code{tree} will have the following content:
* @code{
* tree.left.left  = "The answer is"
* tree.left.right = 42
* tree.right      = foo
* }
* <P>Note:
* The nesting order is important:
* a @code{Pair<Pair<Pair<Integer,Integer>,Integer>,Integer>} is a linked list
* but a @code{Pair<Pair<Integer,Integer>,Pair<Integer,Integer>>} is a balanced tree.
*  
* @author Marius
* @version 1.0
* @param <L> Any object type
* @param <R> Another (or the same) object type
 */

public class Pair<L,R> {
	public final L left;
	public final R right;
	public Pair(final L l, final R r){
		this.left  = l;
		this.right = r;
	}

	public boolean equals(Pair<Object,Object> aPair) {
		return this.left.equals(aPair.left) && this.right.equals(aPair.right);
	}
	
	@Override
	public int hashCode() {
		//97 is a nice prime number
		//without this multiplication hashCode() would always return 0 if left == right
		return left.hashCode() ^ (right.hashCode()*97);
	}
	
	@Override
	public String toString() {
		return String.format("{ %s; %s }", left.toString(), right.toString());
	}
}
