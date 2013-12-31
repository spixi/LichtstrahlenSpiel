package de.bwvaachen.beamoflightgame.ui;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoRedoButton extends AbstractUndoRedoButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3752060571993629986L;

	public UndoRedoButton(UndoManager um) {
		super(um);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		undoManager.undoOrRedo();
		}
		catch (CannotUndoException ex) {
			JOptionPane.showMessageDialog(null,"RÃ¼ckgÃ¤ngig nicht mÃ¶glich");
		}
		catch (CannotRedoException ex) {
			JOptionPane.showMessageDialog(null,"Wiederholen nicht mÃ¶glich");
		}
	}
	
	@Override
	protected void update() {
		this.setEnabled(undoManager.canUndoOrRedo());
		this.setText(undoManager.getPresentationName());
	}
}
