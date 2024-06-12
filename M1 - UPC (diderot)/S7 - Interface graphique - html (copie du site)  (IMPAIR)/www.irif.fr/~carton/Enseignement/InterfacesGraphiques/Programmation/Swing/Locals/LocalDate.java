// Time-stamp: <LocalDate.java  14 Jan 2004 15:58:17>

import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Affichage multilingue de la date
 * @author O. Carton
 * @version 1.0
 */
class LocalDate extends JFrame {
    final int width  = 250;	// Largeur de la fen�tre
    final int height = 100;	// Hauteur de la fen�tre
    // Locale courante
    Locale locale = Locale.getDefault();
    // Panneau d'affichage de la date
    JLabel label = new JLabel();

    // Ajout d'une entr�e dans un menu
    public LocalDate () {
	// Dimension de la fen�tre
	setSize(width, height);
	// Titre de la fen�tre
	setTitle("Affichage multilingue de la date");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	// Label avec affichage au centre
	label.setHorizontalAlignment(SwingConstants.CENTER);

	// Tableaux des locales
	final Locale[] locales = Locale.getAvailableLocales();
	// Noms des locales
	String[] localeNames = new String [locales.length];
	for(int i = 0; i < localeNames.length; i++)
	    localeNames[i] = locales[i].getDisplayName();
	// Menu pour les locales
	final JComboBox localeMenu = new JComboBox(localeNames);
	// Calcul de l'indice de la locale par d�faut
	for(int i = 0; i < locales.length; i++)
	    if (locales[i].equals(locale)) {
		localeMenu.setSelectedIndex(i);
		break;
	    }
	// Action 
	localeMenu.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    locale = locales[localeMenu.getSelectedIndex()];
		    update();
		}
	    });


	// Panneau principal
	Container contentPane = getContentPane();
	// Ajout des composant dans le panneau principal
	contentPane.add(localeMenu, BorderLayout.NORTH);
	contentPane.add(label, BorderLayout.CENTER);

	// Mise � jour forc�e
	update();
    }
    // Mise � jour de l'affichage
    void update() {
	// Format d'affichage avec la locale choisie
	DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
	// Mise � jour de la date courante
	label.setText(df.format(new Date()));
    }
    public static void main(String [] args)
    {
	// Cr�ation de la fen�tre
	LocalDate ld = new LocalDate();
	// Affichage de la fen�tre
	ld.setVisible(true);
    }
}
