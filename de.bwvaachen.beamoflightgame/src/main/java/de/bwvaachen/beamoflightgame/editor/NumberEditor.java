package de.bwvaachen.beamoflightgame.editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public class NumberEditor extends BeamsOfLightEditor 
	implements ActionListener{

	private TileButton tile, clickedTile ;
	private ArrayList<TileButton> tileList ;
	private int lightPower ;
	private String input ;
	
	public NumberEditor(int width, int height) {
		super("BeamsOfLightEditor - Zahleneingabe", width, height);
		
	}

	@Override
	public void initComponents(){
		
		leftButton = new JButton("Solve");
		leftButton.addActionListener(this);
		middleButton = new JButton("DO NOTHING");
		middleButton.addActionListener(this);
		rightButton = new JButton("Reset");
		rightButton.addActionListener(this);
		
		tileList = new ArrayList<TileButton>();
		
		for(int i=1; i<=col; i++){
			for(int j=1; j<=row; j++){
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
	public BeamsOfLightPuzzleBoard convertToBoard() {
		BeamsOfLightPuzzleBoard target = new BeamsOfLightPuzzleBoard();
		target.init(col,row);
		
		for(TileButton tile : tileList){
			if(tile.getState() == TileState.EMPTY){
				// TODO error for empty tiles
			}
			if(tile.getState() == TileState.H_LIGHT || tile.getState() == TileState.V_LIGHT){
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.NUMBER){
				target.putTile(new NumberTile(target,tile.getCol(),tile.getRow(),tile.getLightPower()));
			}
		}
		
		return target;
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
			
			clickedTile  = (TileButton) ae.getSource();
		
			input = (String) JOptionPane.showInputDialog(
					this,
					"Lichtstärke:\n",
					"Lichtstärkeneingabe",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"1");;
			try{
				lightPower = Integer.valueOf(input);
				clickedTile.setLightPower(lightPower);
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
