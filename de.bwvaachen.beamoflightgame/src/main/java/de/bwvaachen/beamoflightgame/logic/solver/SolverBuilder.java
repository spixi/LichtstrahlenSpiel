package de.bwvaachen.beamoflightgame.logic.solver;

import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class SolverBuilder {
	
	private static SolverBuilder instance = new SolverBuilder();
	
	private class SolverBuilderContext implements ISolverBuilderContext {
		private List<IStrategy> strategies;
		
		private SolverBuilderContext() {
			strategies = new LinkedList<IStrategy>();
		}

		@Override
		public ISolverBuilderContext with(Class<? extends IStrategy> s) throws InstantiationException, IllegalAccessException {
		    strategies.add(s.newInstance());
			return this;
		}

		@Override
		public ISolver forBoard(final IBeamsOfLightPuzzleBoard board) {
			return new AbstractSolver(board) {
				@Override
				public void solve() throws PuzzleException {
					// TODO Auto-generated method stub
					
				}
			};
		}
		
	}

	public static ISolverBuilderContext build() {
		return instance.new SolverBuilderContext();
	}

}
