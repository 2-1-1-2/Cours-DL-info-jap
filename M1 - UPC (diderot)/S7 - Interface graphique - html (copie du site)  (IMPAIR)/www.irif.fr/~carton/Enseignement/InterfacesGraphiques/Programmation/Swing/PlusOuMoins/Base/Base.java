// Time-stamp: <Base.java   3 Mar 2005 10:45:00>

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Exemple PlusOuMoins en Model/View/Controler avec le choix de la base
 * @version 1.0
 */
class Base {
    public Base() {
	// Cr�ation du mod�le
	Model model = new Model();
	// Cr�ation du contr�leur
	Controler controler = new Controler(model);
	// Cr�ation de la vue
	View view = new View(model, controler);
	controler.setView(view);
	// Affichage de la vue
	view.setVisible(true);
    }

    public static void main(String [] args)
    {
	// Cr�ation 
	Base base = new Base();
    }
}

/**
 * Bouton avec valeur incr�mentale
 */
class IncrButton extends JButton {
    private int incr;		// Valeur de l'incr�ment
    IncrButton(String title, int incr) {
	// Constructeur avec titre
	super(title);
	// Initialisation de l'incr�ment
	this.incr = incr;
    }
    // Retourne la valeur d'incr�ment
    int getIncr() { return incr; }
}
    

/**
 * Mod�le contenant les donn�es
 * Les donn�es sont constitu�es d'un seul entier.
 */
class Model {
    private int value;		// Donn�es du mod�le
    Model(int value) { this.value = value; }
    Model() { this(0); }
    void incrValue(int incr) { value += incr; }
    int getValue() { return value; }
}

/**
 * Vue affichant les donn�es
 */
class View extends JFrame {
    final int width  = 150;	// Largeur de la fen�tre
    final int height = 20;	// Hauteur de la fen�tre
    Model model;		// Mod�le contenant les donn�es
    JLabel label;		// �tiquette d'affichage
    int base = 10;		// Base d'affichage
    View(Model model, Controler controler) {
	// Constructeur avec titre
	super("Plus ou moins");
	// Initialisation du mod�le
	this.model = model;
	// Dimension de la fen�tre
	setSize(width, height);
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Cr�ation du champ d'affichage de la valeur
	label = new JLabel(Integer.toString(model.getValue()), JLabel.CENTER);
	// Cr�ation des deux Boutons �cout�s par le contr�leur
	JButton plusButton = new IncrButton("+", +1);
	plusButton.addActionListener(controler);
	JButton moinsButton = new IncrButton("-", -1);
	moinsButton.addActionListener(controler);
	// Cr�ation d'une combobox pour le choix de la base
	JComboBox comboBox = new JComboBox();
	// Ajout des quatre bases 
	// Attention, l'ordre est implicitement utilis� par le contr�leur
	comboBox.addItem("D�cimal");
	comboBox.addItem("Hexad�cimal");
	comboBox.addItem("Octal");
	comboBox.addItem("Binaire");
	// Cha�ne encoy�e dans l'�v�nement ActionEvent.
	// C'est ainsi que le contr�leur reconna�t que l'�v�nement 
	// provient de la combobox
	comboBox.setActionCommand("Combo");
	// La combobox est �cout�e par le contr�leur
	comboBox.addActionListener(controler);
	// Mise en place des �l�ments dans un panneau
	JPanel mainPanel = (JPanel) getContentPane();
	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(comboBox, BorderLayout.NORTH);
	mainPanel.add(plusButton,BorderLayout.EAST );
	mainPanel.add(label,BorderLayout.CENTER );
	mainPanel.add(moinsButton, BorderLayout.WEST);
    }
    // Mise � jour de l'affichage � partir des donn�es du mod�le
    void update() {
	label.setText(Integer.toString(model.getValue(), base));
    }
    // Changement de la base d'affichage
    void setBase(int base) {
	this.base = base;
    }
}

/**
 * Contr�leur 
 */
class Controler implements ActionListener {
    Model model;		// Mod�le contenant les donn�es 
    View view;			// Vue des donn�es
    Controler(Model model) { this.model = model; }
    void setView(View view) { this.view = view; }
    // Action sur reception d'un �v�nement
    public void actionPerformed(ActionEvent event) {
	// 
	if (event.getActionCommand().equals("Combo")) {
	    // ComboBox qui a �mis l'�v�nement
	    JComboBox comboBox = (JComboBox) event.getSource();
	    // On utlise l'ordre implicite des bases dans la combobox :
	    // 0 d�cimal, 1 hexad�cimal, 2 octal, 3 binaire.
	    switch(comboBox.getSelectedIndex()) {
	    case 1:
		view.setBase(16);
		break;
	    case 2:
		view.setBase(8);
		break;
	    case 3:
		view.setBase(2);
		break;
	    default:
		view.setBase(10);
		break;
	    }   
	} else {
	    // Bouton �metteur de l'�v�nement
	    IncrButton button = (IncrButton) event.getSource();
	    // Mise � jour des donn�es
	    model.incrValue(button.getIncr());
	    // Force la vue � �tre conforme aux donn�es
	}
	view.update();
    }
}
