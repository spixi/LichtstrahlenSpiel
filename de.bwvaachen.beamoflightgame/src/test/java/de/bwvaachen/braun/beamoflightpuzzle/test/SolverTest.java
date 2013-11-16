package de.bwvaachen.braun.beamoflightpuzzle.test;

import static org.easymock.EasyMock.*;
import org.junit.*;

import de.bwvaachen.beamoflightgame.logic.solver.DependencyStrategy;
import de.bwvaachen.beamoflightgame.logic.solver.ISolver;
import de.bwvaachen.beamoflightgame.logic.solver.SolverBuilder;
//import org.junit.runner.RunWith;
//import org.powermock.api.easymock.PowerMock.*;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({Object.class})


public class SolverTest {

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		ISolver s = SolverBuilder.build().with(DependencyStrategy.class).get();
	}

}
