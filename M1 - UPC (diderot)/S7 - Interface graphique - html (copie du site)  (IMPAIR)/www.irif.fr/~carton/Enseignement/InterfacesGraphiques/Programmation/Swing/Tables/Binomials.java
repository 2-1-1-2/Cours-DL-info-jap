// Time-stamp: <Binomials.java  21 avr 2003 16:46:46>

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Table des coefficients bin�miaux
 * @author O. Carton 
 * @version 1.0
 */
class Binomials extends JFrame {
    public Binomials() {
	// Constructeur avec titre
	super("Table des coefficients bin�miaux");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Conteneur principal de la fen�tre
	Container contentPane = getContentPane();

	// Mod�le de la table
	class BinomialsModel extends AbstractTableModel {
	    int size;
	    // Constructeurs
	    BinomialsModel(int size) { this.size = size; }
	    BinomialsModel() { this(10); }
	    // M�thode statique du calcul d'un coefficient binomial
	    private int binomial(int n, int p) {

		int binom;	// R�sultat
		int k;		// Variable d'it�ration

		// On se ram�ne au cas o� p <= n
		if (p > n) 
		    return 0;
		// On se ram�ne au cas o� p <= n-p
		if (p > n-p) 
		    p = n-p;
		// k     prend les valeurs     1 ... p
		// n-p+k prend les valeurs n-p+1 ... n
		for (binom = 1, k = 1; k <= p; k++)
		    // Il est important de faire la mutiplication avant la
		    // division.  Le r�sultat interm�diaire binom/k n'est
		    // pas n�cessairement un entier.
		    binom = (binom * (n-p+k)) / k;
		return binom;
	    }
	    // M�thodes � red�finir dans AbstractTableModel
	    // Nombre de lignes
	    public int getRowCount() { return size; }
	    // Nombre de colonnes
	    public int getColumnCount() { return size; }
	    // Ent�tes des colonnes
	    public String getColumnName(int column) { 
		return Integer.toString(column);
	    }
	    // Classe des objets : ceci force une justification � droite
	    public Class getColumnClass(int column) {
		return Number.class;
	    }
	    // Valeurs des cellules
	    public Object getValueAt(int row, int column) {
		return new Integer(binomial(row, column));
	    }
	}
	// Cr�ation de la table
	JTable table = new JTable(new BinomialsModel(20));
	// Ajout dans la vue de la table avec des ascenseurs
	contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
	// Ajout dans la vue de la table sans les ascenseurs
	// Dans ce cas, il faut ajouter les ent�tes s�par�ment.
	// contentPane.add(table.getTableHeader(), BorderLayout.NORTH);
	// contentPane.add(table, BorderLayout.CENTER);
    }
    public static void main(String [] args)
    {
	Binomials view = new Binomials();
	// Mise en place 
	view.pack();
	// Affichage de la vue
	view.setVisible(true);
    }
}

