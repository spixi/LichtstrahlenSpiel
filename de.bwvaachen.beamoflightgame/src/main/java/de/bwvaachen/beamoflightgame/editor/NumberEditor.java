package de.bwvaachen.beamoflightgame.editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public class NumberEditor extends BeamsOfLightEditor 
	implements ActionListener{

	private TileButton tile ;
	private ArrayList<TileButton> tileList ;
	private int lightPower ;
	
	public NumberEditor(int width, int height) {
		super(EditorType.NumberEditor, width, height);
		
	}

	@Override
	public void initComponents(){
		
		leftButton.addActionListener(this);
		middleButton.addActionListener(this);
		rightButton.addActionListener(this);
		
		tileList = new ArrayList<TileButton>();
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				tile = new TileButton(j,i);
				tile.setSize(128,128);
				tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				tile.addActionListener(this);
				tilesPanel.add(tile);
				tileList.add(tile);
			}
		}
	}
	
	@Override
	public String getTileStats() {
		this.remainingTiles = totalTiles ;
		
		for(TileButton tile : tileList){
			if(tile.getState() == TileState.NUMBER){
				this.remainingTiles -= (tile.getLightPower() + 1); 
			}
		}
		return "Felder (Gesamt): "+totalTiles+"\nFelder (Verbleibend): "+remainingTiles;
	}

	@Override
	public BeamsOfLightPuzzleBoard convertToBoard() {
		BeamsOfLightPuzzleBoard target = new BeamsOfLightPuzzleBoard();
		target.init(col,row);
		
		for(TileButton tile : tileList){
			if(tile.getState() == TileState.EMPTY){
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.H_LIGHT || tile.getState() == TileState.V_LIGHT){
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.NUMBER){
				target.putTile(new NumberTile(target,tile.getLightPower(),tile.getCol(),tile.getRow()));
			}
		}
		return target;
	}
	
	public void importPuzzleBoard(BeamsOfLightPuzzleBoard source){
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				source.getTileAt(i,j).getTileState();
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == leftButton){
			
		}
		if(ae.getSource() == middleButton){
			
		}
		if(ae.getSource() == rightButton){
			for(TileButton tile : tileList){
				tile.reset();
			}
		}else if(ae.getSource().getClass() == TileButton.class){
			String input ;
			int maxLightPower = (col-1) + (row-1) ;
			TileButton clickedTile  = (TileButton) ae.getSource();
			
			try{
				do{
					input = (String) JOptionPane.showInputDialog(
								this,
								"Lichtstaerke(1-" + maxLightPower + "):\n",
								"Lichtstaerkeneingabe",
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"1");
					lightPower = Integer.valueOf(input);
				}while(lightPower > maxLightPower || lightPower < 1);
				
				clickedTile.setLightPower(lightPower);
				tileStats.setText(getTileStats());
			}catch(IIOException iioe){
				//TODO
			}catch(NumberFormatException nfe){
				//TODO
			}catch(Exception e){
				//TODO
			}finally{
				
			}
		}
    }
}
