package de.bwvaachen.beamoflightgame.ui;

import java.net.URL;

import javax.swing.ImageIcon;

import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class GraficFactory {
	
	/**
	 * @author pauls_and
	 * @param meineKachel darzustellenden Kachel
	 * @return
	 */
	public ImageIcon getImage(ITile meineKachel)
	{
		ImageIcon ii = null;
		try{
			NumberTile num = (NumberTile) meineKachel;

			String url = "themes/happiness/"+ num.getNumber() + ".png";
			URL u = this.getClass().getClassLoader().getResource(url);
			
			return ii = new ImageIcon(u);	
		}
		catch (Exception e){
			
		}
		LightTile lig = (LightTile) meineKachel;
		String url ="";
		switch(lig.getTileState())
		{
		case EAST:
			 url = "themes/happiness/darkness.png";
			 
			break;
		case NORTH:
			 url = "themes/happiness/darkness.png";
			break;
		case SOUTH:
			 url = "themes/happiness/darkness.png";
			break;
		case WEST:
			 url = "themes/happiness/darkness.png";
			break;
		case EMPTY:
			 url = "themes/happiness/darkness.png";
			break;
			
			default:
				return null; //nicht schön aber einfach
		}
		
		//Ansonsten zunaechst eine leere Kachel ausgeben
		
		URL u = this.getClass().getClassLoader().getResource(url);
		ii = new ImageIcon(u);
		
		return ii;
	}
	
	
	//Soll zukünfitug benutzt werden damit die Images nur 1 mal geladen werden
	private void loadIcons()
	{
		
	}
	public GraficFactory()
	{
		loadIcons();
	}
}
