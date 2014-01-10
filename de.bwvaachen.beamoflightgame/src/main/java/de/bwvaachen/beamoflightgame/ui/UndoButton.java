package de.bwvaachen.beamoflightgame.ui;

import java.awt.event.ActionEvent;
import java.awt.peer.MenuItemPeer;

import javax.swing.JOptionPane;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import de.bwvaachen.beamoflightgame.controller.TurnUndoManager;

public class UndoButton extends AbstractUndoRedoButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6212990560228610159L;

	public UndoButton(TurnUndoManager um) {
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
		this.setText(undoManager.getUndoPresentationName());
	}
}
