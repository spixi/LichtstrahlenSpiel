package de.bwvaachen.beamoflightgame.ui;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoButton extends AbstractUndoRedoButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6212990560228610159L;

	public UndoButton(UndoManager um) {
		super(um);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		undoManager.undo();
		}
		catch (CannotUndoException ex) {
			JOptionPane.showMessageDialog(null,"RÃ¼ckgÃ¤ngig nicht mÃ¶glich");
		}
	}
	
	@Override
	protected void update() {
		this.setEnabled(undoManager.canUndo());
		this.setText(undoManager.getPresentationName());
	}
}
