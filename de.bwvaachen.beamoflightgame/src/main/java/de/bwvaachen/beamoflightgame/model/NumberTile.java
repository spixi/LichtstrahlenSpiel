package de.bwvaachen.beamoflightgame.model;

public class NumberTile extends AbstractTile<NumberTileState> implements ITile<NumberTileState> {
	public NumberTile(final int number, int row, int col) {
		super(row, col, NumberTileState.class);
		setTileState(new NumberTileState(number));
	}

	public int getRemainingLightRange() {
		// TODO ...
		return 0;
	}

	public int getNumber() {
		// TODO Auto-generated method stub
		return getTileState().getNumber();
	}

}
