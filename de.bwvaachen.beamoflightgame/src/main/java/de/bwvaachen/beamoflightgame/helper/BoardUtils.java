package de.bwvaachen.beamoflightgame.helper;

import java.util.HashMap;

import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;

public final class  BoardUtils<T extends ITileState>{
	private static HashMap multitons;
	
	static {
		multitons = new HashMap();
	}
	
	public static <U extends ITileState> BoardUtils<U> getInstance(Class<U> clazz) {
		BoardUtils<U> instance = null;
		instance = (BoardUtils<U>) multitons.get(clazz);
			if(instance == null) {
				synchronized(multitons) {
					instance = (BoardUtils<U>) multitons.get(clazz);
					if(instance == null) {
						instance = new BoardUtils<U>();
						multitons.put(clazz, new BoardUtils<U>());
					}
				}
			}
		return instance;
	}
	
	public void fillBoard(ITile<T> start, int numOfFields, TraverseDirection dir, T state) {
		BoardTraverser traverser = start.getTraverser();
		while(numOfFields-- >= 0) {
			ITile tile = traverser.get();
			if(tile.isStateChangeable()) {
				//Change the state
				//Only the last change should be significant, therefore numOfFields==0
				((IChangeableTile) tile).setState(state, numOfFields==0);
			}
			//shift the traverser to the next tile
			if(!traverser.shift(dir)) return;
		}
	}

}
