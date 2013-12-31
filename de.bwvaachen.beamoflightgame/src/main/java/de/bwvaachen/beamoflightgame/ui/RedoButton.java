package de.bwvaachen.beamoflightgame.ui;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

public class RedoButton extends AbstractUndoRedoButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6212990560228610159L;

	public RedoButton(UndoManager um) {
		super(um);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		undoManager.redo();
		}
		catch (CannotRedoException ex) {
			JOptionPane.showMessageDialog(null,"Wiederholen nicht mÃ¶glich");
		}
	}
	
	@Override
	protected void update() {
		this.setEnabled(undoManager.canRedo());
		this.setText(undoManager.getRedoPresentationName());
	}
}

