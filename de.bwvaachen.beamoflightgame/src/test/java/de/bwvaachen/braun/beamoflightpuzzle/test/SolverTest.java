package de.bwvaachen.braun.beamoflightpuzzle.test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.*;

import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.SolverBuilder;
import de.bwvaachen.beamoflightgame.logic.strategies.*;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
//import org.junit.runner.RunWith;
//import org.powermock.api.easymock.PowerMock.*;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Object.class})


public class SolverTest {

	@Test
	public void builderTest() throws InstantiationException, IllegalAccessException {
		IBeamsOfLightPuzzleBoard b = EasyMock.createMock(IBeamsOfLightPuzzleBoard.class);
		ISolver s = SolverBuilder.buildWith(IntersectionStrategy.class)
				                 .and(TryAndErrorStrategy.class).forBoard(b);
		
		assertNotNull("Prüfe, ob der Builder einen funktionsfähigen Solver erstellen kann ...",s);
	}
	
	@Test
	public void lonelyFieldStrategyTest() {
		List<INumberTile> numberTiles = Arrays.asList(new INumberTile[] {null, null});
		
		//Board: 1 _ _ _ 2
		
		IBeamsOfLightPuzzleBoard b = new BeamsOfLightPuzzleBoard(5, 1, numberTiles);
		//TODO !!!
		
	}

}
