/* Time-stamp: <window.c  25 f�v 2003 15:20:55> */

// Cr�ation d'une fen�tre et affichage d'un texte 
// quand on clique dans la fen�tre

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <X11/Xlib.h>
#include <X11/Xutil.h>

int main(int argc, char* argv[])
{
  char* displayName = 0;	// Nom du serveur 
  Display* dpy;			// Display proprement dit
  int screen;			// Num�ro de l'�cran
  Window win;			// Fen�tre 
  GC gc;			// Contexte graphique
  XEvent event;			// �v�nement
  char name[] = "Hello world !"; // Cha�ne � afficher

  // Recherche d'un argument de type -d ou -display
  if (argc == 3 && (!strcmp(argv[1], "-d") || 
		    !strcmp(argv[1], "-display")))
    displayName = argv[2];

  // Connexion au seveur X
  dpy = XOpenDisplay(displayName);
  // Si la connexion �choue, le programme se termine.
  if (dpy == 0) {
    printf("%s : connexion %s impossible\n", argv[0], 
	   XDisplayName(displayName));
    exit(1);
  }

  // R�cup�ration de l'�cran et du contexte graphique
  screen = DefaultScreen(dpy);	// Num�ro de l'�cran
  gc = DefaultGC(dpy, screen);	// Contexte graphique

  // Cr�ation de la fen�tre
  win = XCreateSimpleWindow(
	  dpy, 			   // Display
	  DefaultRootWindow(dpy),  // Fen�tre m�re
	  0, 0, 300, 100, 	   // G�om�trie 
	  10,			   // Largeur du bord
	  BlackPixel(dpy, screen), // Couleur du bord
	  WhitePixel(dpy, screen)  // Couleur du fond
	  );
  if (win == 0) {
    printf("%s : cr�ation de fen�tre impossible\n", argv[0]);
    exit(1);
  }

  // Mise en place du titre 
  // Ce titre est stock� sous la forme d'une propri�t� qui est
  // utilis�e par le gestionnaire de fen�tres.  Utiliser xprop
  // pour v�rifier que WM_NAME(STRING) = "Hello world !"
  XStoreName(dpy, win, name);

  // S�lection des �v�nements
  // Trois types d'�v�nements sont accept�s par la fen�tre
  XSelectInput(dpy, win, ButtonPressMask | ButtonReleaseMask | ExposureMask);

  // Affichage de la fen�tre
  XMapWindow(dpy, win);

  // Boucle d'attente d'�v�nements
  for (;;) {
    // R�cup�ration de l'�v�nement suivant
    XNextEvent(dpy, &event);
    switch (event.type) {
    case ButtonPress : 
      printf("�v�nement ButtonPress\n");
      // Affichage de la cha�ne
      XDrawString(dpy, win, gc, 100, 50, name, strlen(name));
      break;
    case ButtonRelease : 
      printf("�v�nement ButtonRelease\n");
      break;
    case Expose : 
      printf("�v�nement Exposure\n");
      break;
    }
  }
  return 0;
}
