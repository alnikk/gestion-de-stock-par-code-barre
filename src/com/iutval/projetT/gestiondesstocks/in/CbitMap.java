package com.iutval.projetT.gestiondesstocks.in;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Thread permettant de décoder la chaîne de caractères codée par le code-barres
 * On considère qu'il n'y a pas de contour autour du code-barres (clair ou blanc)
 * et que le code-barres est centré et droit.
 */
public class CbitMap extends Thread 
{
	private int resultat = 0;

	private Cplan plan;
	private int hauteurImage;
	private int largeurImage;


	private HashMap<Integer, Character> caractere;

	private final static int NB_BARRES = 9;
	private final static int SEUIL = 80;

	/**
	 * Constructeur de CbitMap
	 * @param bm Bitmap que l'on va décoder
	 */
	public CbitMap(Bitmap bm) 
	{
		int intensite = 0;
		int x, y; //indices pour parcourir l'image
		int couleur; //intensité du pixel (entier de 4 bytes : alpha, rouge, vert et bleue)
		this.largeurImage = bm.getWidth();
		this.hauteurImage = bm.getHeight();
		this.caractere = new HashMap<Integer, Character>(); //dictionnaire de caractères de la norme du code-barres
		this.plan = new Cplan(largeurImage, 3); //contient la partie de l'image que l'on va traiter
		//on prend le milieu de l'image en supposant qu'on aura plus de chance d'être 
		//sur le code-barres pour une image centrée :
		int milieu = this.hauteurImage / 2;
		int pixelGris,nInf,nSup; //intensité de gris du pixel, intensité minimale, intensité maximale
		int indY=0; //indice pour parcourir le plan en hauteur
		 
		nSup=0;
		nInf=255;
		//Passage en niveau de gris et détermination de nInf et nSup :
		for(y=milieu-1;y<milieu+2;y++) 
		{
			for (x = 0; x < largeurImage; x++) 
			{
				couleur = bm.getPixel(x,y);
				//Passe le pixel en niveau de gris à partir des 3 composantes (rouge, vert et bleu) :
				pixelGris=(int)(Color.red(couleur) * 0.3 + Color.green(couleur) * 0.59 + Color.blue(couleur) * 0.11);
				this.plan.setPixel(x, indY, pixelGris);
				if(nInf > pixelGris)
					nInf=pixelGris;
				if(nSup < pixelGris)
					nSup=pixelGris;
			}
			indY++;
		} 
		indY=0;
		
		//Normalisation et seuillage
		for(indY=0;indY<3;indY++)
		{
			for(x=0;x<largeurImage;x++)
			{
				if ((this.plan.getPixel(x, indY)-nInf)*(255/(nSup-nInf)) > SEUIL)
				{
					intensite = 255;
				} 
				else 
				{
					intensite = 0;
				}	
				this.plan.setPixel(x, indY, intensite);
			}
		}
		
		//"Correction" de la ligne d'incice 0 du plan en fonction des deux autres :
		for(x=0;x<largeurImage;x++)
		{
			if (this.plan.getPixel(x, 0)+this.plan.getPixel(x, 1) +this.plan.getPixel(x, 2)  >= 510 )
			{
				intensite = 255;
			} 
			else 
			{
				intensite = 0;
			}
			this.plan.setPixel(x, 0, intensite);
			Log.d("CbitMap.class", intensite+"");
		}		
		
		//Remplissage du dictionnaire (entier codé - caractères) :
		caractere.put(265, 'A');
		caractere.put(73, 'B');
		caractere.put(328, 'C');
		caractere.put(25, 'D');
		caractere.put(280, 'E');
		caractere.put(88, 'F');
		caractere.put(13, 'G');
		caractere.put(268, 'H');
		caractere.put(76, 'I');
		caractere.put(28, 'J');
		caractere.put(259, 'K');
		caractere.put(67, 'L');
		caractere.put(322, 'M');
		caractere.put(19, 'N');
		caractere.put(274, 'O');
		caractere.put(82, 'P');
		caractere.put(7, 'Q');
		caractere.put(262, 'R');
		caractere.put(70, 'S');
		caractere.put(22, 'T');
		caractere.put(385, 'U');
		caractere.put(193, 'V');
		caractere.put(448, 'W');
		caractere.put(145, 'X');
		caractere.put(400, 'Y');
		caractere.put(208, 'Z');
		caractere.put(52, '0');
		caractere.put(289, '1');
		caractere.put(97, '2');
		caractere.put(352, '3');
		caractere.put(449, '4');
		caractere.put(304, '5');
		caractere.put(112, '6');
		caractere.put(37, '7');
		caractere.put(292, '8');
		caractere.put(100, '9');
		caractere.put(148, '*');
	}

	public void run() 
	{
		int cmpBarre = 0; // compteur de barres (noires et blanches)
		int cmpPixel = 0; // compteur de pixels pour connaître la largeur des
							// bandes
		int largeurMin; // largeur supposée d'une barre étroite
		int largeurMax; // largeur supposée d'une barre large
		int milieu = 0; // on parcourt le code-barres en son
											// milieu (on suppose l'image est
											// bien centrée)
		int i = 0; // indice pour parcourir l'image en largeur
		int bitCode = 0; // bit codé par la barre parcourue (0 ou 1)
		int resBitCode = 0; // entier décimal codé par un bloc de 9 barres
		int couleur; // intensité du pixel
		String res = null; // chaîne codé par le code-barres (ex: "Code39"))
		int margeMax; //Marge d'erreur sur la largeur des barres larges
		int margeMin; // Marge d'erreur sur la largeur des barres étroites

		//On avance tant qu'on arrive pas à la première barre noire du codes-barres
		while ((i < this.largeurImage - 1) && this.plan.getPixel(i, milieu) == 255)
			i++;

		//1ère affectation de largeurMin et largeurMax (avec la largeur de la 1ère bande) :
		couleur = this.plan.getPixel(i, milieu);
		while ((i < this.largeurImage - 1) && (couleur == this.plan.getPixel(i, milieu))) 
		{
			cmpPixel++;
			i++;
		}
		largeurMin = cmpPixel;
		largeurMax = cmpPixel;
		cmpBarre++;
		cmpPixel = 0;

		//Détermination de largeurMin et largeurMax :
		while (cmpBarre < NB_BARRES) 
		{
			couleur = this.plan.getPixel(i, milieu);
			while ((i < this.largeurImage - 1) && (couleur == this.plan.getPixel(i, milieu))) 
			{
				cmpPixel++;
				i++;
			}
			if (cmpPixel < largeurMin)
				largeurMin = cmpPixel;
			if (cmpPixel > largeurMax)
				largeurMax = cmpPixel;
			cmpPixel = 0;
			cmpBarre++;
		}

		//Détermination des marges d'erreur sur la largeur des barres
		margeMin = (int) largeurMin / 3;
		margeMax = (int) largeurMax / 3;

		i = 0;
		cmpBarre = 0;

		//On reparcourt le plan pour décoder le code-barres
		
		//On avance tant qu'on arrive pas à la 1ère barre noire
		while ((i < this.largeurImage - 1) && (this.plan.getPixel(i, milieu) == 255))
			i++;

		//Décodage du premier bloc de 9 barres (=1er caractère)
		while (cmpBarre < NB_BARRES) 
		{
			couleur = this.plan.getPixel(i, milieu);
			while ((i < this.largeurImage - 1) && (this.plan.getPixel(i, milieu) == couleur)) 
			{
				cmpPixel++;
				i++;
			}
			
			//Détermination du bit codé par chaque barre
			if (cmpPixel <= largeurMin + margeMin && cmpPixel >= largeurMin - margeMin) 
			{
				bitCode = 0;
			}
			if (cmpPixel <= largeurMax + margeMax && cmpPixel >= largeurMax - margeMax) 
			{
				bitCode = 1;
			}
			
			//Conversion du résultat binaire en décimal
			resBitCode = resBitCode + bitCode * ((int) Math.pow(2, (8 - cmpBarre)));
			cmpPixel = 0;
			cmpBarre++;
		}
		
		//On vérifie que le premier caractère est bien une étoile
		if (resBitCode != 148) // 148 -> code de l'etoile
		{
			this.resultat = -1; // TODO throws exception
			return;
		}

		cmpBarre = 0;
		resBitCode = 0;

		//On saute l'intervale entre deux caractères (=entre deux blocs de 9 barres)
		couleur = this.plan.getPixel(i, milieu);
		while ((i < this.largeurImage - 1) && (this.plan.getPixel(i, milieu) == couleur))
			i++;

		//On parcourt le reste du code-barres tant qu'on arrive pas au caractère étoile de fin
		while (true)
		{
			//Décodage de chaque bloc de 9 barres
			while (cmpBarre < NB_BARRES) 
			{
				couleur = this.plan.getPixel(i, milieu);
				while ((i < this.largeurImage - 1) && (this.plan.getPixel(i, milieu) == couleur)) 
				{
					cmpPixel++;
					i++;
				}

				if (cmpPixel <= largeurMin + margeMin && cmpPixel >= largeurMin - margeMin)
					bitCode = 0;
				if (cmpPixel <= largeurMax + margeMax && cmpPixel >= largeurMax - margeMax)
					bitCode = 1;
				resBitCode = resBitCode + bitCode * ((int) Math.pow(2, (8 - cmpBarre)));
				cmpPixel = 0;
				cmpBarre++;

			}
			
			//Si le caractère courant est l'étoile on sort de la boucle
			if (resBitCode == 148) 
			{
				break;
			}

			//Si l'entier obtenu est dans le dictionnaire, on récupère le caractère qui lui correspond
			if (caractere.containsKey(resBitCode)) 
			{
				res = res + caractere.get(resBitCode);
			} 
			else 
			{
				// ERREUR
				this.resultat = -1; // TODO throws exception
				return;
			}
			resBitCode = 0;
			cmpBarre = 0;
			
			//On saute l'intervalle séparant deux caractères
			couleur = this.plan.getPixel(i, milieu);
			while (i < this.largeurImage - 1 && this.plan.getPixel(i, milieu) == couleur)
				i++;
		}

		//Conversiont de la chaîne de caractères finalement obtenue en entier
		this.resultat = Integer.parseInt(res);
	}

	public int getRes() 
	{
		return this.resultat;
	}
}
