// Time-stamp: <TwoLists.java  19 mai 2003 18:25:48>

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Deux listes
 * @author O. Carton
 * @version 1.0
 */
class TwoLists extends JFrame {
    public TwoLists () {
	// Titre de la fen�tre
	setTitle("Deux listes");
	// Action � faire lorsque la fen�tre est ferm�e par 
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Panneau principal
	Container contentPane = getContentPane();

	// Mod�les des listes
	final DefaultListModel leftModel = new DefaultListModel();
	leftModel.addElement("Nathalie");
	leftModel.addElement("Sylvie");
	final DefaultListModel rightModel = new DefaultListModel();
	rightModel.addElement("Marie");
	rightModel.addElement("Isabelle");

	// Listes avec des tailles maximales non born�es
	final JList leftList = new JList(leftModel);
	leftList.setMaximumSize(new Dimension(Integer.MAX_VALUE, 
					      Integer.MAX_VALUE));
	final JList rightList = new JList(rightModel);
	rightList.setMaximumSize(new Dimension(Integer.MAX_VALUE, 
					       Integer.MAX_VALUE));

	// Boutons de transfert
	JButton left2rightButton = new JButton("Vers la droite");
	JButton right2leftButton = new JButton("Vers la gauche");

	// Z�ne de saisie
	final JTextField textField = new JTextField(30);
	textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 
				     textField.getPreferredSize().height));

	// Boutons d'ajout
	JButton leftAddButton  = new JButton("Ajout � gauche");
	JButton rightAddButton  = new JButton("Ajout � droite");
	
	// Panneau des boutons de transfert
	JPanel movePanel = new JPanel(new GridLayout(0, 1));
	movePanel.add(left2rightButton);
	movePanel.add(right2leftButton);
	movePanel.setMaximumSize(movePanel.getPreferredSize());

	// Panneau pour la troisi�me colonne
	JPanel columnPanel = new JPanel();
	columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.Y_AXIS));
	columnPanel.add(Box.createVerticalGlue());
	columnPanel.add(movePanel);
	columnPanel.add(Box.createVerticalGlue());

	// Panneau des listes et des boutons de transfert
	JPanel listsPanel = new JPanel();
	listsPanel.setLayout(new BoxLayout(listsPanel, BoxLayout.X_AXIS));
	listsPanel.add(new JScrollPane(leftList));
	listsPanel.add(columnPanel);
	listsPanel.add(new JScrollPane(rightList));

	// Panneau des boutons d'ajout
	JPanel addPanel = new JPanel(new GridLayout(1, 0));
	addPanel.add(leftAddButton);
	addPanel.add(rightAddButton);
	addPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 
				    addPanel.getPreferredSize().height));

	// Paneau principal
	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	contentPane.add(listsPanel);
	contentPane.add(textField);
	contentPane.add(addPanel);

	// Contr�leur du bouton Gauche -> Droite
	left2rightButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    Object[] values = leftList.getSelectedValues();
 		    for(int i = 0; i < values.length; i++) {
 			leftModel.removeElement(values[i]);
			rightModel.addElement(values[i]);
		    }
		}
	    });

	// Contr�leur du bouton Droite -> Gauche
	right2leftButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    Object[] values = rightList.getSelectedValues();
 		    for(int i = 0; i < values.length; i++) {
 			rightModel.removeElement(values[i]);
			leftModel.addElement(values[i]);
		    }
		}
	    });
		    
	// Contr�leur du bouton Ajout � Gauche
	leftAddButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    leftModel.addElement(textField.getText());
		}
	    });
	// Contr�leur du bouton Ajout � Droite
	rightAddButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    rightModel.addElement(textField.getText());
		}
	    });
    }
    public static void main(String [] args)
    {
	// Cr�ation de la fen�tre
	TwoLists view = new TwoLists();
	// Mise en place 
	view.pack();
	// Affichage de la fen�tre
	view.setVisible(true);
	
    }
}
