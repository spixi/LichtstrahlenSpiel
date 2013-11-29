package de.bwvaachen.beamoflightgame.ui;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.ui.RotatedIcon.Rotate;

public class GraficFactory {
	
	/**
	 * @author pauls_and
	 * @param meineKachel darzustellenden Kachel
	 * @return
	 */
	public Icon getImage(ITile meineKachel)
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
		case NORTH:
			if(isEnd(meineKachel))
				url = "themes/happiness/light1.png";
			else url = "themes/happiness/light2.png";
			return ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			
			
		case EAST:
			if(isEnd(meineKachel))
				url = "themes/happiness/light1.png";
			else url = "themes/happiness/light2.png";
			ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			return rotateIcon(ii, Rotate.DOWN);
		case SOUTH:
			if(isEnd(meineKachel))
				url = "themes/happiness/light1.png";
			else url = "themes/happiness/light2.png";
			ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			return rotateIcon(ii, Rotate.UPSIDE_DOWN);
			
		case WEST:
			if(isEnd(meineKachel))
				url = "themes/happiness/light1.png";
			else url = "themes/happiness/light2.png";
			ii = new ImageIcon(this.getClass().getClassLoader().getResource(url));
			return rotateIcon(ii, Rotate.UP);
			
		case EMPTY:
			 url = "themes/happiness/darkness.png";
			break;
			
			default:
				return null; //nicht schoen aber einfach
		}
				
		URL u = this.getClass().getClassLoader().getResource(url);
		ii = new ImageIcon(u);
		
		return ii;
	}
	private Icon rotateIcon(ImageIcon ii, Rotate r)
	{
		RotatedIcon ri = new RotatedIcon(ii, r);
		return ri;
	}
	private boolean isEnd(ITile tile)
	{
		//Noch zu implementieren: Wenn das Ende des Strahl erreicht ist: true 
		return false;
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
