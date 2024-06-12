// Time-stamp: <ImageWait.java  13 mai 2003 11:27:01>

import java.awt.*;
import javax.swing.*;

/**
 * Visualisation d'une image avec attente du chargement de l'image
 * @author O. Carton
 * @version 1.0
 */
class ImageWait extends JFrame {
    Image image;		// L'image
    public ImageWait () {
	// Titre de la fen�tre
	setTitle("Visualisation d'une image avec attente");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Lecture de l'image par le tookit par d�faut
	image = Toolkit.getDefaultToolkit().getImage("images/9personnes.jpg");
	// Place le composant dans la fen�tre
	setContentPane(new ImagePane());
    }
    /** Panneau pour afficher l'image */
    class ImagePane extends JPanel {
	ImagePane() {
	    // Taille du panneau
	    setPreferredSize(new Dimension(377,517));
	}	    
	// Dessins de la fen�tre
	public void paintComponent(Graphics g) {
	    // Appel � la m�thode de la super-classe
	    super.paintComponent(g);
	    // Dessin de l'image
	    g.drawImage(image, 0, 0, this);
	}
 	// Red�finition de la m�thode imageUpdate de l'interface ImageObserver
	public boolean imageUpdate(Image image, int flags, int x, int y,
				   int width, int height) {
	    // Affichage de l'avancement du chargement de l'image
	    System.out.println("imageUpdate() : x = " + x + ", y = " + y +
			       ", width = " + width + ", height = " + height);
	    // Affichage de l'image lorsque l'image est totalement charg�e
	    if ((flags & ALLBITS) != 0)
		repaint();
	    return true;
	}
    }
    public static void main(String [] args)
    {
	// Cr�ation de la fen�tre
	ImageWait view = new ImageWait();
	// Mise en place
	view.pack();
	// Affichage de la fen�tre
	view.setVisible(true);
    }
}