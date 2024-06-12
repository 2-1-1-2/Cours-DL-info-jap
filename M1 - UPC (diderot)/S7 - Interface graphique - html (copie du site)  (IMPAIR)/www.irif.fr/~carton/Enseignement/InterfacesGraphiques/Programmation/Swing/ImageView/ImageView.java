// Time-stamp: <ImageView.java  25 Apr 2005 15:31:17>

import java.awt.*;
import javax.swing.*;

/**
 * Visualisation d'une image
 * @author O. Carton
 * @version 1.0
 */
class ImageView extends JFrame {
    Image image;		// L'image
    public ImageView () {
	// Titre de la fen�tre
	setTitle("Visualisation d'une image");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Lecture de l'image par le tookit par d�faut
	image = Toolkit.getDefaultToolkit().getImage("images/9personnes.jpg");
	// Affichage du type r�el de l'image
	System.out.println(image.getClass());
	// Affichage des dimensions de l'image
	// Ces deux lignes affichent "Image width = -1, height = -1"
	// car l'image n'est pas encore charg�e
	System.out.print("Image : width = " + image.getWidth(this));
	System.out.println(",  height = " + image.getHeight(this));
	// Place le composant
	setContentPane(new ImagePane());
    }
    /** Panneau pour afficher l'image */
    // Une m�thode na�ve consiterait � dessiner l'image directement dans
    // l'objet JFrame en red�finissant la m�thode paint dans la classe
    // ImageView.   On peut remarquer que le haut de l'image n'est
    // pas visible avec un appel g.drawImage(image, 0, 0, this);
    // Pour �viter ce probl�me, l'image est dessiner dans un panneau
    // qui devient le contentPane de la fen�tre.
    class ImagePane extends JPanel {
	ImagePane() {
	    // Taille du panneau �gale � la taille de l'image
	    setPreferredSize(new Dimension(377,517));
	}	    
	// Dessins de la fen�tre
	public void paintComponent(Graphics g) {
	    // Appel � la m�thode de la super-classe
	    super.paintComponent(g);
	    // Dessin de l'image
	    g.drawImage(image, 0, 0, this);
	}
    }
    public static void main(String [] args)
    {
	// Cr�ation de la fen�tre
	ImageView view = new ImageView();
	// Mise en place
	view.pack();
	// Affichage de la fen�tre
	view.setVisible(true);
    }
}
