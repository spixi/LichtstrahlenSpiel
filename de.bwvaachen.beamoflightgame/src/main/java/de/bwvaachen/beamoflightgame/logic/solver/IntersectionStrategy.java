package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class IntersectionStrategy implements IStrategy {

	@Override
	public boolean solve(IBeamsOfLightPuzzleBoard b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getComplexity() {
		// TODO Auto-generated method stub
		
		//eindeutige Lösung: return 1
		//zwei Lösungen: return 4
		//drei Lösungen: return 9
		//x Lösungen: return Lösungen^2
		
		return 0;
	}

}
