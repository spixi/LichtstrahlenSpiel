package de.bwvaachen.beamoflightgame.model.builder;

import java.util.LinkedList;
import java.util.List;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class BeamOfLightPuzzleBoardBuilder {
	
	private int cols;
	private int rows;
	private IBeamsOfLightPuzzleBoard board=null;
	private boolean contentChanged=false;
	private List<INumberTile>numberTiles=new LinkedList<INumberTile>();
	

	

	public BeamOfLightPuzzleBoardBuilder(int rows, int cols) {
		this.rows=rows;
		this.cols=cols;
	}
	
//	public void setLightTiles(int row, int col, LightTileState state){
//		
//	}
	public void setNumberTiles(int row, int col,int number){
//		numberTiles.add();TODO
		contentChanged=true;
	}
	
	public IBeamsOfLightPuzzleBoard createBeamOfLightPuzzleBoard(){
		if(board==null){
			board=new BeamsOfLightPuzzleBoard(rows, cols, numberTiles);
			contentChanged=false;
		}
		return board;
	}
	

}
