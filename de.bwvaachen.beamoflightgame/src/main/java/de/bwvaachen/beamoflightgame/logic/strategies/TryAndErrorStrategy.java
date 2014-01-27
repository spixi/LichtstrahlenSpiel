package de.bwvaachen.beamoflightgame.logic.strategies;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;
import de.bwvaachen.beamoflightgame.helper.AbstractTileVisitor;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.Holder;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.AbstractSolver;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class TryAndErrorStrategy extends AbstractStrategy<ITileState> implements UndoableEditListener  {
	private TurnUndoManager um;
	private SaveListener    listener;
	private final EnumSet<LightTileState> remainingDirections;
	
	//Avoid memory leaks - a big problem in the Observer Pattern
	//If the TryAndErrorStrategy is disposed we must also unregister it from the board.
	//
	//We need to unregister from the IBeamsOfLightPuzzleBoard as soon the TryAndErrorStrategy
	//is disposed. This can be done in the finalize() method, which is called by the
	//garbage collector.
	//
	//Because the IBeamsOfLightPuzzleBoard is holding a reference to the listener
	//itself, the garbage collector would never call the finalize() method. Therefore
	//we must use a weak reference. Unlike a normal references a weak reference does
	//not increase the reference counter. If an object is only referenced by weak
	//references, the GC will free it.
	//
	//This class is static, so its instances can live without a TryAndErrorStrategy instance
	private static class SaveListener implements UndoableEditListener {
		private WeakReference<TryAndErrorStrategy> ownerRef;
		private IBeamsOfLightPuzzleBoard board;
		
		private SaveListener(IBeamsOfLightPuzzleBoard board, TryAndErrorStrategy owner) {
			this.board    = board;
			this.ownerRef = new WeakReference<TryAndErrorStrategy>(owner);
		}
		
		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			TryAndErrorStrategy owner = ownerRef.get();
			if(owner== null) {
				this.dispose();
			}
			else {
				owner.undoableEditHappened(e);
			}
		}

		private void dispose() {
			board.removeUndoableEditListener(this);
			board = null;
		}
	}
	
	private BacktrackingHook hook;
	
	/*
	 * This is an initializator list (indeed, a very seldom used Java construct)
	 * Never write a constructor, because the SolverBuilder would be unable to load
	 * this class otherwise!
	 */
	{
		um = new TurnUndoManager();
		remainingDirections = EnumSet.of(LightTileState.NORTH,
				                         LightTileState.EAST,
				                         LightTileState.SOUTH,
				                         LightTileState.WEST);
		hook = new BacktrackingHook();
	}
	
	protected void _init() {
		IBeamsOfLightPuzzleBoard board = tile.getBoard();
		if(listener == null) {
			listener = new SaveListener(tile.getBoard(),this);
			board.addUndoableEditListener(listener);
		}
	}
	
	//Do this at garbage collection
    protected void finalize() throws Throwable {
        try {
            if (listener != null)
                listener.dispose();
        } finally {
            super.finalize();
        }
    }

	@Override
	public double getComplexity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAppliableForTile(ITile t) {
		final Holder<Boolean> isAppliable = new Holder<Boolean>(false);
		
		t.accept(new AbstractTileVisitor() {
			public void visitNumberTile(NumberTile t) {
				isAppliable.value = (t.getRemainingLightRange() > 0);
			}
		});
		
		return isAppliable.value;
	}
	
	@Override
	public boolean tryToSolve() throws UnsolvablePuzzleException {
		final BoardTraverser  t           = new BoardTraverser(tile);
		final Holder<Boolean> error = new Holder<Boolean>(false);
		
		//No heuristic for now. It would be better, to start this
		//strategy with tiles which have only two remaining 
		//But this would be much more complicated. So we have to stay
		//with this for now.
		
		//Check whether the neighbour is an empty field
		for(LightTileState direction: remainingDirections) {
			if(!t.shift(direction.getTraverseDirection())) {
				//We reached the border of the board
				error.value = true;
			}
			else {
				final ITile<?> neighbour = t.get();
				neighbour.accept(new AbstractTileVisitor(){
					public void visitLightTile(LightTile lt) {
						LightTileState lts = lt.getTileState();
						//Check whether the tile is not empty
						if (!(lts.equals(LightTileState.EMPTY)))
							error.value = true;
					}
					public void visitOtherTile(ITile t) {
						//We reached a non-lightTile
						error.value = true;
					}
				});
			}
			
			//TODO: How can we remember which directions are remaining for each tile
			//when this depends on the way we are going through the backtracking tree?
			if(error.value) {
				remainingDirections.remove(direction);
			}
			
			error.value = false;
			
			t.reset();
		}
		
		//no remainingDirections ...
		if(remainingDirections.isEmpty()) {
			hook = null;
			throw new UnsolvablePuzzleException(tile);
		}
		else {
			LightTileState guess = (LightTileState) remainingDirections.toArray()[0];	
			
			//Set a marker. We can undo to the last marker in the hook
			//which is called in case of an UnsolvablePuzzleException
			um.addMarker();
			t.shift(guess.getTraverseDirection());
			((LightTile) t.get()).setState(guess, true);
			return true;
		}
	}
	
	public Collection<BacktrackingHook> getHooks() {
		return Collections.singleton(new BacktrackingHook());
	}
	
	private class BacktrackingHook implements AbstractSolver.Hook {
		@Override
		public void run() {
			um.goToLastMark();
		}
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		um.undoableEditHappened(e);
	}

}
