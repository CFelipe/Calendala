package br.ufrn.musica.calendala.controller;

/**
 * @author Felipe Cortez de Sá
 * @version 0.1
 * @since 0.4
 */

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import br.ufrn.musica.calendala.gui.FacadeSwing;

public class CellEdit extends AbstractUndoableEdit {
	private String prevText;

    CellEdit(String prevText) {
    	this.prevText = prevText;
    }

    public void undo() throws CannotUndoException {
    }

    @Override
    public void die() {
        super.die();
    }

    @Override
    public String getPresentationName() {
        return "Undo CellEdit";
    }

    public String getDebugName() {
        return getPresentationName();
    }

}