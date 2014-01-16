package de.bwvaachen.beamoflightgame.editor;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TilesPanel extends JPanel {

	private Line2D line = null ;
	private RenderingHints rh ;
	
	public TilesPanel(){
		super();
		this.rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	public void setLine(Line2D line){
		this.line = line ;
	}
	
	@Override
	public void update(Graphics g){
		super.update(g);
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g ;
		
		super.paint(g);
		
		if(line != null){	
			g2.setStroke(new BasicStroke(2f));
			g2.setRenderingHints(rh) ;
			g2.draw(line);
		}
	}
}
