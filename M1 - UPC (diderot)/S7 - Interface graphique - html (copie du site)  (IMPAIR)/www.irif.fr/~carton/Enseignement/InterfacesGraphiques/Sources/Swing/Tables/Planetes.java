// Time-stamp: <Planetes.java  21 avr 2003 12:03:20>

import java.awt.*;
import javax.swing.*;

/**
 * Table des planètes
 * @author O. Carton d'après une idée de J. Berstel
 * @version 1.0
 */
class Planetes extends JFrame {
    public Planetes() {
	// Constructeur avec titre
	super("Table des planetes");
	// Action à faire lorsque la fenêtre est fermée.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Conteneur principal de la fenêtre
	Container contentPane = getContentPane();

	// Données de la table : planètes du système solaire
	Object[][] cellules = { 
	    // Nom       Rayon (en km)      Nombre de lunes  Gazeuse
	    { "Mercure", new Double(2440),  new Integer(0),  "non"},
	    { "Vénus",   new Double(6052),  new Integer(0),  "non"},
	    { "Terre",   new Double(6378),  new Integer(1),  "non"},
	    { "Mars",    new Double(3397),  new Integer(2),  "non"},
	    { "Jupiter", new Double(71492), new Integer(16), "oui"},
	    { "Saturne", new Double(60268), new Integer(18), "oui"},
	    { "Uranus",  new Double(25559), new Integer(17), "oui"},
	    { "Neptune", new Double(24766), new Integer(8),  "oui"},
	    { "Pluton",  new Double(1137),  new Integer(1),  "non"}
	};
	// Noms de colonnes
	String[] columnNames =
	    { "Planète", "Rayon", "Lunes", "Gazeuse"};

	// Création de la table
	JTable table = new JTable(cellules, columnNames);
	// Ajout de la table avec des ascenseurs
	contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
	// Ajout de la table sans les ascenseurs
	// Dans ce cas, il faut ajouter les entêtes séparément.
	// contentPane.add(table.getTableHeader(), BorderLayout.NORTH);
	// contentPane.add(table, BorderLayout.CENTER);
    }
    public static void main(String [] args)
    {
	Planetes view = new Planetes();
	// Mise en place 
	view.pack();
	// Affichage de la vue
	view.setVisible(true);
    }
}
