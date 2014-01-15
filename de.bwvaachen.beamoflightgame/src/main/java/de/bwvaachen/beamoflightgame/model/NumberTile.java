package de.bwvaachen.beamoflightgame.model;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;

public class NumberTile extends AbstractTile<NumberTileState> implements IChangeableTile<NumberTileState> {
	public NumberTile(final IBeamsOfLightPuzzleBoard board, final int number, int x, int y) {
		super(board, x, y, NumberTileState.class);
		setTileState(new NumberTileState(number), true);
	}

	@Override
	public void accept(ITileVisitor v) {
		v.visitNumberTile(this);
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return getTileState().getNumber();
	}

	public int getRemainingLightRange() {
		int counter = 0;
		BoardTraverser traverser = new BoardTraverser(this.board, this.getX(), this.getY());
		for(LightTileState lts : LightTileState.allDirections()) {
			traverser.reset();
			while(traverser.shift(lts.getTraverseDirection())){
				if(traverser.get().getTileState().equals(lts)) {
					counter++;
				}
				else break;
			}
		}
		return getNumber() - counter;
	}
	
	@Override
	public void setState(NumberTileState state, boolean significant) {
		setTileState(state,significant);	
	}
	

}
