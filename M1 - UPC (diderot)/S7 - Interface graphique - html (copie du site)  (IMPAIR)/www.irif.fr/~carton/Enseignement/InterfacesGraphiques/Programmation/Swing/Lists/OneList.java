// Time-stamp: <OneList.java  10 Aug 2005 10:15:41>

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Ajout et supression dans une JList
 * @author O. Carton
 * @version 1.0
 */
class OneList extends JFrame {
    public OneList () {
	// Titre de la fen�tre
	setTitle("OneList");
	// Action � faire lorsque la fen�tre est ferm�e par 
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Paneau principale
	Container contentPane = getContentPane();

	// Liste de selection
	// Tableau de pr�noms f�minins pour les valeurs de la liste
	String[] data = { "Agathe", "Alice", "Aude", "Barbara", "Clarisse", 
			  "Cl�mence", "C�line", "H�lia", "Isabelle", "L�a", 
			  "Marion", "Natacha", "Nathalie", "Sylvie", 
			  "Val�rie", "V�ronique" };
	// Mod�le
	// On cr�e explicitement le mod�le de la liste pour �tre s�r qu'il 
	// soit de type DefaultListModel.  Sinon, il est impl�ment� par 
	// une classe interne � JList
	DefaultListModel model = new DefaultListModel();
	// Ajout des �l�ments au mod�le
	// La classe DefaultListModel n'a pas de constructeur qui prend 
	// directement un tableau en param�tre comme en a un la classe JList.
	for (int i = 0; i < data.length; i++)
	    model.addElement(data[i]);
	// Liste cr��e avec le mod�le voulu
	final JList list = new JList(model);
	// avec une barre de d�filement
	JScrollPane listScrollPane = new JScrollPane(list);
	// Ajout de la liste dans le panneau principal
	
	// Panneau d'affichage 
	final JTextArea textArea = new JTextArea(20, 10);
	// avec une barre de d�filement
	JScrollPane textScrollPane = new JScrollPane(textArea);
	// Ajout de la liste dans le panneau principal

	// Panneau pour s�parer la liste et l'affichage
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					      listScrollPane, textScrollPane);
	contentPane.add(splitPane, BorderLayout.CENTER);
					      
	// Tableau de contr�le
	JPanel controlPanel = new JPanel();
	// Utilisation d'un BoxLayout pour que les boutons et la zone de texte 
	// aient la m�me hauteur
	controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
	// Ajout du panneau de contr�le en haut du panneau principal
	contentPane.add(controlPanel, BorderLayout.NORTH);

	// Bouton d'affichage
	JButton printButton = new JButton("Print");
	// Ajout du bouton dans le panneau de contr�le
	controlPanel.add(printButton);
	// Contr�leur du bouton
	printButton.addActionListener(new ActionListener () {
		public void actionPerformed(ActionEvent e) {
		    // Liste des valeurs s�lectionn�es
		    Object[] selection = list.getSelectedValues();
		    // Affichage des valeurs selectionn�es dans le
		    // panneau de texte
		    for(int i = 0; i < selection.length; i++)
			textArea.append(selection[i].toString() + "\n");
		    // Ajout d'une ligne de s�paration
		    textArea.append("--------------\n");
		}
	    });

	// Bouton de suppression
	JButton removeButton = new JButton("Remove");
	// Ajout du bouton dans le panneau de contr�le
	controlPanel.add(removeButton);
	// Contr�leur du bouton
	removeButton.addActionListener(new ActionListener () {
		public void actionPerformed(ActionEvent e) {
		    // Indices des valeurs s�lectionn�es
		    // Il est ici pr�f�rable de travailler avec les indices
		    // dans le cas o� un m�me �l�ment aurait plusieurs 
		    // occurrences dans la liste
		    int[] indices = list.getSelectedIndices();
		    // Mod�le des donn�es de la liste
 		    DefaultListModel model = (DefaultListModel) 
 			list.getModel();
		    // Supression des valeurs s�lectionn�es
		    // Le "-i" prend en compte le fait que les premi�res 
		    // suppressions d�calent les indices des �l�ments 
		    // qui suivent.
 		    for(int i = 0; i < indices.length; i++)
 			model.removeElementAt(indices[i]-i);
		}
	    });

	// Zone de texte pour la saisie de l'ajout
	final JTextField textField = new JTextField(10);
	// Bouton pour forcer l'ajout
	JButton addButton = new JButton("Add");
	// Ajout du bouton et de la zone de texte dans le panneau de contr�le
	controlPanel.add(addButton);
	controlPanel.add(textField);
	// Contr�leur commun au bouton et � la zone de texte
	ActionListener addAction = new ActionListener () {
		public void actionPerformed(ActionEvent e) {
		    // Valeur � ajouter
		    String value = textField.getText();
		    // Mod�le des donn�es de la liste
 		    DefaultListModel model = (DefaultListModel) 
 			list.getModel();
		    if (value.length() > 0)
			model.addElement(value);
		}
	    };
	// Ajout du contr�leur comme �couteur du bouton et de la zone
	addButton.addActionListener(addAction);
	textField.addActionListener(addAction);
    }
    public static void main(String [] args)
    {
	// Cr�ation de la fen�tre
	OneList view = new OneList();
	// Mise en place 
	view.pack();
	// Affichage de la fen�tre
	view.setVisible(true);
    }
}
