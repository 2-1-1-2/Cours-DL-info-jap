// Time-stamp: <SimpleFrame.java  25 f�v 2003 16:10:01>

import java.awt.*;
import java.awt.event.*;

/**
 * Programme AWT qui ouvre une vraie fen�tre
 * @author O. Carton
 * @version 1.0
 */
class SimpleFrame extends Frame {
    // Constructeur de la fen�tre
    SimpleFrame(String title) { 
	super(title); 
    }
    public static void main(String [] args)
    {
	SimpleFrame f = new SimpleFrame("Simple fen�tre");
	f.addWindowListener(new Closer());
	f.pack();
	f.show();
    }
}

/**
 * Classe charg�e de terminer l'application lorsque la fen�tre est ferm�e
 */
class Closer extends WindowAdapter {
  public void windowClosing(WindowEvent e) { System.exit(0); }
}
