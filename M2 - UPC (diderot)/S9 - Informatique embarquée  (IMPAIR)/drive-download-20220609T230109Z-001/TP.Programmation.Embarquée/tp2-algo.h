#ifndef ALGO_H_
#define ALGO_H_

typedef struct {
	float x;
	float y;
} Point;

/**
 * Calcule les coordonn�es du barycentre du tableau 6x6 \a window, pond�r� du tableau de m�me tailles \a mask.
 * @param window tableau de 6x6 o� l'on cherche le barrycentre
 * @param mask tableau o� les 0 indiquent un pixel � exclure et une autre valeur un pixel � inclure.
 * @return coordonn�es du barycentre.
 */
Point barycentre(float window[], float mask[]);

/**
 * Calcul le flux binaire dans la fenetre \a window en entr�e. \a Mask indique si le pixel doit �tre pris en compte ou non.
 * @param window tableau de 6x6 pixel o� l'on calcul le flux d'une �toile
 * @param mask tableau o� les 0 indiquent un pixel � exclure et une autre valeur un pixel � inclure.
 * @return somme des pixel inclus
 */
double calculFluxBinaire(float window[], float mask[]);

/**
 * Calcul le flux pond�r� dans la fenetre \a window en entr�e. \a Mask indique si le pixel doit �tre pris en compte ou non.
 * @param window tableau de 6x6 pixel o� l'on calcul le flux d'une �toile
 * @param mask tableau o� pour chaque pixel de \a window on trouve le facteur de pond�ration � appliquer
 * @return somme des pixels pond�r�s du facteur de \a mask
 */
double calculFluxPondere(float window[], float mask[]);

#endif /* ALGO_H_ */
