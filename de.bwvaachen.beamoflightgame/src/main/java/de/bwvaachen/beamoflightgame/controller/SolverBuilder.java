package de.bwvaachen.beamoflightgame.controller;

import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.AbstractSolver;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

public class SolverBuilder {
	
	private static SolverBuilder instance = new SolverBuilder();
	
	private class SolverBuilderContext implements ISolverBuilderContext  {
		private List<IStrategy> strategies;
		
		private SolverBuilderContext() {
			strategies = new LinkedList<IStrategy>();
		}

		@Override
		public ISolver forBoard(final IBeamsOfLightPuzzleBoard board) {
			return new AbstractSolver(board) {

				@Override
				public void solve() throws PuzzleException {
					for(ITile tile: board) {
						step(tile, 0);
					}
				}

				
				private void step(ITile tile, int stackPointer) throws PuzzleException {
					//if (stackPointer == strategies.size()) throw new UnsolvablePuzzleException();
					if (stackPointer == strategies.size()) return;
					
					IStrategy currentStrategy = strategies.get(stackPointer);
					
					if ( !currentStrategy.isAppliableForTile(tile) ) {
						step(tile, stackPointer+1);
					}
					else {
						currentStrategy.init(tile);
						boolean canSolve = currentStrategy.tryToSolve();
						//if(!canSolve) step(tile, stackPointer+1);
					}
				}
				
			};
		}

		@Override
		public ISolverBuilderContext and(Class<? extends IStrategy> s)
				throws InstantiationException, IllegalAccessException {
		    strategies.add(s.newInstance());
			return this;
		}
		
	}

	public static ISolverBuilderContext buildWith(Class<? extends IStrategy> s)
			   throws InstantiationException, IllegalAccessException{
		return instance.new SolverBuilderContext().and(s);
	}
}
