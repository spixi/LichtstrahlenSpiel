package de.bwvaachen.beamoflightgame.ui;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.ui.RotatedIcon.Rotate;


/**
 * 
 * @author Andreas
 * SimpleFactory um die Images zu erstellen
 */
public class GraficFactory {
	
	/**
	 * @author pauls_and
	 * @param meineKachel darzustellenden Kachel
	 * @return
	 */
	
	private IBeamsOfLightPuzzleBoard model;
	private BoardTraverser traverser;
	
	/**
	 * Liefert das passende Picture fuer die Kachel  zurueck
	 * @param meineKachel Das ITile welches gesetzt werdem soll
	 * @return Das passende Image :D
	 */
	public Icon getImage(ITile meineKachel)
	{
		ImageIcon ii = null;
		try{
			NumberTile num = (NumberTile) meineKachel;

			String url = "themes/happiness/"+ num.getNumber() + ".png";
			URL u = this.getClass().getClassLoader().getResource(url);
			
			return ii = new ImageIcon(u);	
		}catch (Exception e){/*Kein Exception handling da in allen anderen Faellen ein LightTile vorhanden ist*/} 
			
		
		LightTile lig = (LightTile) meineKachel;
		String url ="";
		switch(lig.getTileState())
		{
		case NORTH:
			if(isEnd(lig))
				url = "themes/happiness/light2.png";
			else url = "themes/happiness/light1.png";
			return new ImageIcon(this.getClass().getClassLoader().getResource(url));
			
			
		case EAST:
			if(isEnd(lig))
				url = "themes/happiness/light2.png";
			else url = "themes/happiness/light1.png";
			ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			return rotateIcon(ii, Rotate.DOWN);
			
		case SOUTH:
			if(isEnd(lig))
				url = "themes/happiness/light2.png";
			else url = "themes/happiness/light1.png";
			ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			return rotateIcon(ii, Rotate.UPSIDE_DOWN);
			
		case WEST:
			if(isEnd(lig))
				url = "themes/happiness/light2.png";
			else url = "themes/happiness/light1.png";
			ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			return rotateIcon(ii, Rotate.UP);
			
		case EMPTY:
			 url = "themes/happiness/darkness.png";
			 return new ImageIcon(this.getClass().getClassLoader().getResource(url));
			
			default:
				return null; //nicht schoen aber einfach
		}//switch(lig.getTileState())

	}//public Icon getImage(ITile meineKachel)
	
	
	/**
	 * Rotiert ein Icon um den angegebenn Wert
	 * @author pauls_and
	 * @param ii Das Ursprungicon
	 * @param r der Wert aus dem Enum um den gedreht werden soll
	 * @return das gedrehte Icon - als Icon
	 */
	private Icon rotateIcon(ImageIcon ii, Rotate r)
	{
		RotatedIcon ri = new RotatedIcon(ii, r);
		return ri;
	}
	
	/**
	 * Gibt an, ob der Lichtstrahl zu Ende ist
	 * @param tile  Das LightTile welches geprueft werden soll
	 * @return true, wenn das Licht an diesem Punkt zu Ende ist
	 * @author Marius
	 */
	private boolean isEnd(LightTile tile)
	{
		//Traverser auf die Position der aktuellen Kachel schieben
		traverser.moveTo(tile.getX(), tile.getY());
		//Traverser-Richtung holen
		TraverseDirection td = tile.getTileState().getTraverseDirection();
		
		//end of the board: shift() will return false
		//else: has the neighbour tile another state?
		
		if(traverser.shift(td)) {
			
			return traverser.get().getTileState() != tile.getTileState();
		}
		else {
			//End of the board:
			return true;
		}
	}
	
	
	//Soll zukuenftig benutzt werden damit die Images nur 1 mal geladen werden
	private void loadIcons()
	{
		//TODO implement Fliegengewichtpattern!
	}
	public GraficFactory(IBeamsOfLightPuzzleBoard model)
	{
		
		//needed to verify if lightbeam ends
		//nessessary parameter for Boardtraverser!
		this.model = model;
		traverser = new BoardTraverser(model, 0, 0);
		
		
		loadIcons();
	}
}
