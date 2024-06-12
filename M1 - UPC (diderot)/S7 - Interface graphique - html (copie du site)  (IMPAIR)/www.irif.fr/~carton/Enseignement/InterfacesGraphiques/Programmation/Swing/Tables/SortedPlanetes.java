// Time-stamp: <SortedPlanetes.java  21 avr 2003 18:21:10>

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 * Table des plan�tes avec tri selon la colonne s�lectionn�e
 * @author O. Carton d'apr�s une id�e de J. Berstel
 * @version 1.0
 */
class SortedPlanetes extends JFrame {
    public SortedPlanetes() {
	// Constructeur avec titre
	super("Table des planetes avec tri");
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

	// Cr�ation des mod�les
	TableModel baseModel = new DefaultTableModel(cellules, columnNames);
	SortedTableModel sortedModel = new SortedTableModel(baseModel);
	// Cr�ation de la table
	JTable table = new JTable(sortedModel);
	// Selection uniquement des colonnes
	table.setRowSelectionAllowed(false);
	table.setColumnSelectionAllowed(true);
	// �coute des changements de s�lection
	table.getColumnModel().getSelectionModel().addListSelectionListener(sortedModel);
	// Ajout dans la vue de la table avec des ascenseurs
	contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
    }
    public static void main(String [] args)
    {
	SortedPlanetes view = new SortedPlanetes();
	// Mise en place 
	view.pack();
	// Affichage de la vue
	view.setVisible(true);
    }
    // Mod�le de table avec tri
    // Ce mod�le g�re uniquement le tri des lignes et 
    // d�l�gue le reste � un autre mod�le
    class SortedTableModel extends AbstractTableModel 
	                   implements ListSelectionListener {
	// Colonne sur laquelle s'effectue le tri
	int keyColumn = 0;
	// Mod�le qui g�re vraiment les donn�es
	private TableModel model;
	// Correspondance entre la vue et le mod�le :
	// modelRows[i] donne quelle ligne du mod�le doit afficher
	// la ligne n�i de la vue.	    
	private Row[] modelRows;  
	// Chaque objet Row contient un num�ro 
	class Row implements Comparable {
	    private int row; // n� de la ligne dans le mod�le
	    // Constructeur
	    Row(int row) { this.row = row; }
	    // Retourne n� de la ligne dans le mod�le
	    int getRow() { return row; }
	    // Comparaison de deux lignes
	    // On compare les contenus des cellules de la colonne
	    // keyColumn
	    public int compareTo(Object other) {
		Row r = (Row) other;
		Object cellule1 = model.getValueAt(row, keyColumn);
		Object cellule2 = model.getValueAt(r.row, keyColumn);
		return ((Comparable) cellule1).compareTo(cellule2);
	    }
	}
	// Constructeur
	SortedTableModel(TableModel model) {
	    this.model = model;
	    modelRows = new Row[model.getRowCount()];
	    // Au d�part les lignes ne sont pas tri�es.
	    // La ligne modelRows[i] pointe donc sur la ligne n� i.
	    for (int i = 0; i < modelRows.length; i++) 
		modelRows[i] = new Row(i);
	}
	// Acc�s � une cellule
	public Object getValueAt(int row, int column) {
	    return model.getValueAt(modelRows[row].getRow(), column);
	}
	// Nombre de lignes : d�l�gation 
	public int getRowCount() { return model.getRowCount(); }
	// Nombre de colonnes : d�l�gation 
	public int getColumnCount() { return model.getColumnCount(); }
	// Ent�tes des colonnes : d�l�gation 
	public String getColumnName(int column) { 
	    return model.getColumnName(column);
	}
	// Classe des objets
	public Class getColumnClass(int column) {
	    return model.getColumnClass(column);
	}
	// �coute des �v�nements de s�lection
	public void valueChanged(ListSelectionEvent e) {
	    // Mod�le de s�lection de la table qui a �mis l'�v�nement
	    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
	    // Tri selon la colonne s�lectionn�e
	    if (!e.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
		// Colonne s�lectionn�e
		keyColumn = lsm.getMinSelectionIndex();
		// Tri des lignes suivant cette colonne
		Arrays.sort(modelRows);
		// Avertissement de la vue
		fireTableDataChanged();
	    }
	}
    } 
}
