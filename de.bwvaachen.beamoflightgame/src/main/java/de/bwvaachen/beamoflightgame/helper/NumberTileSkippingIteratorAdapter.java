package de.bwvaachen.beamoflightgame.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.INumberTile;

public class NumberTileSkippingIteratorAdapter<ITile> implements Iterator<ILightTile> {
	private Iterator<ITile> adaptee;
	private Queue<ILightTile> buffer;
	
	
	public NumberTileSkippingIteratorAdapter(Iterator<ITile> it) {
		adaptee = it;
		buffer  = new LinkedList<ILightTile>();
	}

	@Override
	public boolean hasNext() {
		if(buffer.isEmpty()) {
			if (adaptee.hasNext()) {
				do {
					ITile next = adaptee.next();
					if(next instanceof ILightTile) {
						buffer.add((ILightTile) next);
						return true;
					}
				}
				while (adaptee.hasNext());
			}
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public ILightTile next() throws NoSuchElementException {
		if (!hasNext()) throw new NoSuchElementException();
		return buffer.poll();
	}

	@Override
	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
		
	}

}
