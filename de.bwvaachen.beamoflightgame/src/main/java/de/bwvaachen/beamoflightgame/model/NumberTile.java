package de.bwvaachen.beamoflightgame.model;

import java.util.Arrays;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;

public class NumberTile extends AbstractTile<NumberTileState> implements ITile<NumberTileState> {
	public NumberTile(final IBeamsOfLightPuzzleBoard board, final int number, int x, int y) {
		super(board, x, y, NumberTileState.class);
		setTileState(new NumberTileState(number));
	}

	public int getRemainingLightRange() {
		int counter = 0;
		BoardTraverser traverser = new BoardTraverser(this.board, this.getX(), this.getY());
		for(LightTileState lts : new LightTileState[] {
				LightTileState.NORTH,
				LightTileState.EAST,
				LightTileState.SOUTH,
				LightTileState.WEST
		}) {
			traverser.reset();
			while(traverser.shift(lts.getTraverseDirection())){
				if(traverser.get().getTileState() == lts) {
					counter++;
				}
				else break;
			}
		}
		return getNumber() - counter;
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return getTileState().getNumber();
	}

}
