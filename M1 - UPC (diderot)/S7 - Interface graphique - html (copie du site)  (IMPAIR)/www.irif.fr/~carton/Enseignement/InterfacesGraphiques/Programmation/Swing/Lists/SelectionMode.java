// Time-stamp: <SelectionMode.java  10 Aug 2005 10:17:55>

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Modes de s�lection d'une liste
 * @author O. Carton
 * @version 1.0
 */
class SelectionMode extends JFrame {
    public SelectionMode () {
	// Titre de la fen�tre
	setTitle("Essais des modes de selection");
	// Action � faire lorsque la fen�tre est ferm�e par 
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Paneau principal
	Container contentPane = getContentPane();

	// Liste de selection
	// Tableau de pr�noms f�minins pour les valeurs de la liste
	String[] data = { "Agathe", "Alice", "Aude", "Barbara", "Clarisse", 
			  "Cl�mence", "C�line", "Isabelle", "L�a", "Marion", 
			  "Natacha", "Nathalie", "Sylvie", "Val�rie", 
			  "V�ronique" };
	                    
	// Liste 
	final JList list = new JList(data);
	// avec une barre de d�filement
	JScrollPane listScrollPane = new JScrollPane(list);

	// Panneau d'affichage 
	final JTextArea textArea = new JTextArea(20, 10);
	// avec une barre de d�filement
	JScrollPane textScrollPane = new JScrollPane(textArea);

	// Panneau pour s�parer la liste et l'affichage
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					      listScrollPane, textScrollPane);
	// Ajout du tout dans le panneau principal
	contentPane.add(splitPane, BorderLayout.CENTER);
					      
	// Tableau de contr�le
	JPanel controlPanel = new JPanel();
	// Ajout du panneau de contr�le en haut du panneau principal
	contentPane.add(controlPanel, BorderLayout.NORTH);
	// Bouton d'ajout
	final JButton printButton = new JButton("Print");
	// Bouton inactif au d�part
	printButton.setEnabled(false);
	// Ajout du bouton dans le panneau de contr�le
	controlPanel.add(printButton);
	// Partie contr�leur du bouton
	printButton.addActionListener(new ActionListener () {
		public void actionPerformed(ActionEvent e) {
		    // Valeurs s�lectionn�es
		    Object[] values = list.getSelectedValues();
		    // Affichage dans le  panneau de texte 
		    // des valeurs selectionn�es 
		    for(int i = 0; i < values.length; i++)
			textArea.append(values[i].toString() + "\n");
		    // Ajout d'une ligne de s�paration
		    textArea.append("--------------\n");
		}
	    });
	
	// �coute des changements de s�lection pour changer l'activation
	// du bouton print
	ListSelectionModel lsm = list.getSelectionModel();
	lsm.addListSelectionListener(new ListSelectionListener () {
		public void valueChanged(ListSelectionEvent e) {
		    // Mod�le de s�lection de la table qui a �mis l'�v�nement
		    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		    printButton.setEnabled(!lsm.isSelectionEmpty());
		}
	    });

	// Menu pour choisir le type de s�lection
	// Tableau de cha�nes pour le menu
	String[] selectionStrings = {"Simple selection", 
				      "Interval selection", 
				     "Multiple selection" }; 
	// Tableau des modes dans le m�me ordre que les cha�nes
	final int[] selectionModes = {
	    ListSelectionModel.SINGLE_SELECTION,
	    ListSelectionModel.SINGLE_INTERVAL_SELECTION,
	    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION };

	// Impl�mentation du menu par une Combo-Box
	final JComboBox comboBox = new JComboBox(selectionStrings);
	comboBox.setSelectedIndex(2); // Multiple selection
	// Ajout du menu dans le panneau de contr�le
	controlPanel.add(comboBox);
	// Partie contr�leur du menu
	comboBox.addActionListener(new ActionListener () {
		public void actionPerformed(ActionEvent event) {
		    // Changement du type s�lection
		    int mode = selectionModes[comboBox.getSelectedIndex()];
		    list.setSelectionMode(mode);
		}    
	    });
    }
    public static void main(String [] args)
    {
	// Cr�ation de la fen�tre
	SelectionMode view = new SelectionMode();
	// Mise en place 
	view.pack();
	// Affichage de la fen�tre
	view.setVisible(true);
    }
}
