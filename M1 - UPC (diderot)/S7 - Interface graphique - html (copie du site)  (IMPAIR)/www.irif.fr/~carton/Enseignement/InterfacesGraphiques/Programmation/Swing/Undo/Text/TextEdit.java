// Time-stamp: <TextEdit.java  28 avr 2003 15:08:10>

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

/**
 * Undo et Redo avec un composant Texte
 * @author O. Carton d'apr�s une id�e de J. Berstel
 * @version 1.0
 */
class TextEdit extends JFrame {
    UndoManager manager = new UndoManager(); // Gestionnaire de undo/redo
    JButton undoButton  = new JButton();     // Bouton de undo
    JButton redoButton  = new JButton();     // Bouton de redo
    public TextEdit() {
	// Constructeur avec titre
	super("Undo/Redo avec du texte");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Conteneur principal de la fen�tre
	Container contentPane = getContentPane();

	// Panneau de bouton
	JPanel buttonPanel = new JPanel(new GridLayout(1,0));
	buttonPanel.add(undoButton);
	buttonPanel.add(redoButton);
	contentPane.add(buttonPanel, BorderLayout.NORTH);

	// �coute des boutons
	undoButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    manager.undo();
		    updateButtons();
		}
	    });
	redoButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    manager.redo();
		    updateButtons();
		}
	    });

	// Zone de texte
	JTextArea editor = new JTextArea();
 	// Ajout du composant de texte avec des ascenseurs
	contentPane.add(new JScrollPane(editor), BorderLayout.CENTER);

	// �coute des �v�nements de modification de la z�ne de texte
	editor.getDocument().addUndoableEditListener(new UndoableEditListener() {
		public void undoableEditHappened(UndoableEditEvent e) {
		    manager.undoableEditHappened(e);
		    updateButtons();
		}
	    });

	// Mise � jour des boutons
	updateButtons();
    }
    // Mise � jour des boutons
    void updateButtons() {
	// Textes des boutons fournis par le UndoManager
	undoButton.setText(manager.getUndoPresentationName());
	redoButton.setText(manager.getRedoPresentationName());
	// Activations des boutons fournies par le UndoManager
	undoButton.setEnabled(manager.canUndo());
	redoButton.setEnabled(manager.canRedo());
    }	
    public static void main(String [] args)
    {
	TextEdit view = new TextEdit();
	// Mise en place 
	view.pack();
	// Affichage de la vue
	view.setVisible(true);
    }

}
