package de.bwvaachen.beamoflightpuzzle.test;

import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.helper.BoardUtils;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.TryAndErrorStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.PrototypModelForLonelyFieldStrategy;

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
	
	@Ignore
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
	
	@Test
	public void lonelyFieldStrategyTest() throws PuzzleException, InstantiationException, IllegalAccessException {		
		
		IBeamsOfLightPuzzleBoard b = new PrototypModelForLonelyFieldStrategy();
		
		ISolver s = SolverBuilder.buildWith(LonelyFieldStrategy.class).forBoard(b);
		
		s.solve();
		
		Assert.assertEquals("Prüfe lonelyFieldStrategy", "n n n \n2 2 n \ns s n \nw w 5 \n", b.toString());
		
	}
	
	@Test
	public void boardUtilsTest() {
		IBeamsOfLightPuzzleBoard b = new PrototypModelForLonelyFieldStrategy();
		LightTile startTile = (LightTile) (b.getTileAt(0, 0));
		BoardUtils.getInstance(LightTileState.class).fillBoard(
				startTile, 3, LightTileState.EAST.getTraverseDirection(), LightTileState.EAST
		);
		Assert.assertEquals("Prüfe BoardUtils.fill", "e e e \n2 2 - \n- - - \n- - 5 \n", b.toString());
	}


}
