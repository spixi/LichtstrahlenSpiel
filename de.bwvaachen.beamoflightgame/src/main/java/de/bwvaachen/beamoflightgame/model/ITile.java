package de.bwvaachen.beamoflightgame.model;

import java.io.Serializable;

public interface ITile extends Serializable {
	void setRow(int row);
	int getRow();
	
	void setCol(int col);
	int getCol();
}
