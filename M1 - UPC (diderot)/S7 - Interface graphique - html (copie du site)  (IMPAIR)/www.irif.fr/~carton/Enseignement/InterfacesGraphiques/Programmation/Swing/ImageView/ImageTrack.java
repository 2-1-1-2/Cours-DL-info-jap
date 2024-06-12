// Time-stamp: <ImageTrack.java  13 mai 2003 11:27:52>

import java.awt.*;
import javax.swing.*;

/**
 * Visualisation d'une image avec utilisation d'un MediaTracker
 * @author O. Carton
 * @version 1.0
 */
class ImageTrack extends JFrame {
    Image image;
    public ImageTrack () {
	// Titre de la fen�tre
	setTitle("Utilisation d'un MediaTracker");
	// Action � faire lorsque la fen�tre est ferm�e.
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	// Lecture de l'image par le tookit par d�faut
	image = Toolkit.getDefaultToolkit().getImage("images/9personnes.jpg");
	// Attente du chargement de l'image avec un MediaTracker
	// Cr�ation du MediaTracker
	MediaTracker tracker = new MediaTracker(this);
	// Ajout de l'image aux images suivies
	tracker.addImage(image, 0);
	// Attente proprement dite
	try { tracker.waitForID(0); }
	catch(InterruptedException e) {}
	// Place le composant dans la fen�tre
	setContentPane(new ImagePane());
    }
    /** Panneau pour afficher l'image */
    class ImagePane extends JPanel {
	ImagePane() {
	    // Taille du panneau donn�e par l'image
	    setPreferredSize(new Dimension(image.getWidth(this),
					   image.getHeight(this)));
	}	    
	// Dessins de la fen�tre
	public void paintComponent(Graphics g) {
	    // Appel � la m�thode de la super-classe
	    super.paintComponent(g);
	    // Dessin de l'image
	    g.drawImage(image, 0, 0, this);
	}
 	// Red�finition de la m�thode imageUpdate de l'interface ImageObserver
	// Cette m�thode ne sera pas appel�e puisque l'image sera d�j� charg�e
	// au moment de l'appel � g.drawImage dans la m�thode paint().
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
	ImageTrack view = new ImageTrack();
	// Mise en place
	view.pack();
	// Affichage de la fen�tre
	view.setVisible(true);
    }
}
