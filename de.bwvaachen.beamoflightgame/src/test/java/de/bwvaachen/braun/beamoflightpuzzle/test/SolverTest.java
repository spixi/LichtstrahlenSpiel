package de.bwvaachen.braun.beamoflightpuzzle.test;

import static org.easymock.EasyMock.*;

import org.easymock.EasyMock;
import org.junit.*;

import de.bwvaachen.beamoflightgame.logic.solver.ISolver;
import de.bwvaachen.beamoflightgame.logic.solver.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.solver.SolverBuilder;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
//import org.junit.runner.RunWith;
//import org.powermock.api.easymock.PowerMock.*;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Object.class})


public class SolverTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		IBeamsOfLightPuzzleBoard b = EasyMock.createMock(IBeamsOfLightPuzzleBoard.class);
		ISolver s = SolverBuilder.build().with(IntersectionStrategy.class).forBoard(b);
	}

}
