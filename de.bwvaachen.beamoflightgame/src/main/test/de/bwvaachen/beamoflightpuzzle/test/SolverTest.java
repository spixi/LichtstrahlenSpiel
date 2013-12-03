package de.bwvaachen.beamoflightpuzzle.test;

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
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
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
		
		assertNotNull("Pr�fe, ob der Builder einen funktionsf�higen Solver erstellen kann ...",s);
	}
	
	@Test
	public void lonelyFieldStrategyTest() throws PuzzleException, InstantiationException, IllegalAccessException {		
		//Board: 1 _ _ _ 2
		
		IBeamsOfLightPuzzleBoard b = new BeamsOfLightPuzzleBoard();
		b.init(5,1);
		b.putTile(new NumberTile(b,1,0,0));
		b.putTile(new LightTile(b,1,0));
		b.putTile(new LightTile(b,2,0));
		b.putTile(new LightTile(b,3,0));
		b.putTile(new NumberTile(b,2,4,0));
		//TODO !!!
		
		System.out.println(b.toString());
		
		ISolver s = SolverBuilder.buildWith(LonelyFieldStrategy.class).forBoard(b);
		
		s.solve();
		
		System.out.println(b.toString());
		
	}

}
