package de.bwvaachen.beamoflightgame.logic.strategies;

import java.lang.ref.WeakReference;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;

public class TryAndErrorStrategy extends AbstractStrategy<ITileState> implements UndoableEditListener  {
	private TurnUndoManager um;
	private SaveListener    listener;
	
	//Avoid memory leaks - a big problem in the Observer Pattern
	//If the TryAndErrorStrategy is disposed we must also unregister it from the board.
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
	
	/*
	 * This is an initializator list (indeed, a very seldom used Java construct)
	 * Never write a constructor, because the SolverBuilder would be unable to load
	 * this class otherwise!
	 */
	{
		um = new TurnUndoManager();
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
		//TODO
		return true;
	}
	
	@Override
	public boolean tryToSolve() {
		return false;
		// TODO Auto-generated method stub
	}


	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		um.undoableEditHappened(e);
	}

}
