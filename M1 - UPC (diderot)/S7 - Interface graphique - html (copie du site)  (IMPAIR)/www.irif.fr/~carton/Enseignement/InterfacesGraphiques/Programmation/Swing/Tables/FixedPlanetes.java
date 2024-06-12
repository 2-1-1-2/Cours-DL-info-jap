// Time-stamp: <FixedPlanetes.java  21 avr 2003 12:04:05>

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Table des plan�tes avec des cellules non �ditables
 * @author O. Carton 
 * @version 1.0
 */
class FixedPlanetes extends JFrame {
    public FixedPlanetes() {
	// Constructeur avec titre
	super("Table des planetes");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Conteneur principal de la fen�tre
	Container contentPane = getContentPane();

	// Donn�es de la table : plan�tes du syst�me solaire
	Object[][] cellules = { 
	    // Nom       Rayon (en km)      Nombre de lunes  Gazeuse
	    { "Mercure", new Double(2440),  new Integer(0),  "non"},
	    { "V�nus",   new Double(6052),  new Integer(0),  "non"},
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
	    { "Plan�te", "Rayon", "Lunes", "Gazeuse"};

	// Mod�le
	// On utilise DefaultTableModel avec une red�finition de la
	// m�thode isCellEditable qui retourne faux
	class FixedTableModel extends DefaultTableModel {
	    FixedTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	    }
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	}

	// Cr�ation de la table
	JTable table = new JTable(new FixedTableModel(cellules, columnNames));
	// Ajout de la table avec des ascenseurs
	contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
	// Ajout de la table sans les ascenseurs
	// Dans ce cas, il faut ajouter les ent�tes s�par�ment.
	// contentPane.add(table.getTableHeader(), BorderLayout.NORTH);
	// contentPane.add(table, BorderLayout.CENTER);
    }
    public static void main(String [] args)
    {
	FixedPlanetes view = new FixedPlanetes();
	// Mise en place 
	view.pack();
	// Affichage de la vue
	view.setVisible(true);
    }
}
