package de.bwvaachen.beamoflightpuzzle.test;

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.TryAndErrorStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.PrototypModelFuerGUI;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Object.class})


public class SolverTest {

	@Test
	public void builderTest() throws InstantiationException, IllegalAccessException {
		IBeamsOfLightPuzzleBoard b = EasyMock.createMock(IBeamsOfLightPuzzleBoard.class);
		ISolver s = SolverBuilder.buildWith(IntersectionStrategy.class)
				                 .and(TryAndErrorStrategy.class).forBoard(b);
		
		assertNotNull("Pr�fe, ob der Builder einen funktionsf�higen Solver erstellen kann ...",s);
	}
	
	@Test
	public void intersectionStrategyTest() throws PuzzleException, InstantiationException, IllegalAccessException {		
		// 3-1-
		// ||1-      
		// |3--
		// -3--
		
		
		IBeamsOfLightPuzzleBoard b = new BeamsOfLightPuzzleBoard();
		
		//new NumberTile()
		
		
		System.out.println(b.toString());
		
		ISolver s = SolverBuilder.buildWith(IntersectionStrategy.class).forBoard(b);
		
		s.solve();
		
		System.out.println(b.toString());
		
	}
	
	@Ignore
	public void lonelyFieldStrategyTest() throws PuzzleException, InstantiationException, IllegalAccessException {		
		
		IBeamsOfLightPuzzleBoard b = new PrototypModelFuerGUI();
		
		System.out.println(b.toString());
		
		ISolver s = SolverBuilder.buildWith(LonelyFieldStrategy.class).forBoard(b);
		
		s.solve();
		
		System.out.println(b.toString());
		
	}


}
