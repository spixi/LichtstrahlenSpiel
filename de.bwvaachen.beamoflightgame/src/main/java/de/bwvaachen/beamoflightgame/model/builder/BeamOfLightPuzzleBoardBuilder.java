package de.bwvaachen.beamoflightgame.model.builder;

import java.util.LinkedList;
import java.util.List;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class BeamOfLightPuzzleBoardBuilder {
	
	private int cols;
	private int rows;
	private IBeamsOfLightPuzzleBoard board=null;
	private boolean contentChanged=false;
	private List<NumberTile>numberTiles=new LinkedList<NumberTile>();
	
	public BeamOfLightPuzzleBoardBuilder(int rows, int cols) {
		this.rows=rows;
		this.cols=cols;
	}
	
//	public void setLightTiles(int row, int col, LightTileState state){
//		
//	}
	public void setNumberTiles(int row, int col, int number){
//		numberTiles.add();TODO
		//board.
		contentChanged=true;
	}
	
	public IBeamsOfLightPuzzleBoard create(Class<? extends IBeamsOfLightPuzzleBoard> clazz) throws InstantiationException, IllegalAccessException{
		//TODO Don't use something like if(board==null)
		//Use context classes instead (See SolverBuilder and ISolverBuilderContext)
		if(board==null){
			board = clazz.newInstance();
			board.init(rows, cols, null); //TODO
			contentChanged=false;
		}
		return board;
	}
	
	public IBeamsOfLightPuzzleBoard create() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return create((Class<? extends IBeamsOfLightPuzzleBoard>) Class.forName("de.bwvaachen.beamoflightgame."));
	}
	

}
