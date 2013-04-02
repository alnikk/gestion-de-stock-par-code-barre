package com.iutval.projetT.gestiondesstocks.in;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CbitMap {

	private Cplan plan;
	private int hauteurImage;
	private int largeurImage;
	private Caractere tabCar[];
	
	private HashMap<Integer,Character> caractere;
	
	private final static int NB_BARRES=9;
	private final static int MARGE=1;
	private final static int TAILLE_TAB=9;
	private final static int SEUIL = 128;
	
	public CbitMap()
	{
		hauteurImage=0;
		largeurImage=0;
		this.caractere = new HashMap<Integer,Character>();
	}
	
	public CbitMap(Bitmap bm)
	{
		int intensite = 0;
		int x,y;
		int couleur;
		this.largeurImage=bm.getWidth();
		this.hauteurImage=bm.getHeight();
		this.plan=new Cplan(largeurImage,hauteurImage);
		for (x=0; x<largeurImage; x++)
		{
			for (y=0; y<hauteurImage; y++)
			{
				couleur = bm.getPixel(x,y);
				if((Color.red(couleur)*0.3+Color.green(couleur)*0.59+Color.blue(couleur)*0.11) > SEUIL)
					intensite = 255;
				else
					intensite = 0;
				this.plan.setPixel(x,y,intensite);
			}
		}
		
		caractere.put(265,'A');
		caractere.put(73,'B');
		caractere.put(328,'C');
		caractere.put(25,'D');
		caractere.put(280,'E');
		caractere.put(88,'F');
		caractere.put(13,'G');
		caractere.put(268,'H');
		caractere.put(76,'I');	
		caractere.put(28,'J');
		caractere.put(259,'K');
		caractere.put(67,'L');
		caractere.put(322,'M');
		caractere.put(19,'N');
		caractere.put(274,'O');	
		caractere.put(82,'P');
		caractere.put(7,'Q');
		caractere.put(262,'R');
		caractere.put(70,'S');
		caractere.put(22,'T');
		caractere.put(385,'U');
		caractere.put(193,'V');
		caractere.put(448,'W');
		caractere.put(145,'X');
		caractere.put(400,'Y');
		caractere.put(208,'Z');
		caractere.put(52,'0');
		caractere.put(289,'1');
		caractere.put(97,'2');
		caractere.put(352,'3');
		caractere.put(449,'4');
		caractere.put(304,'5');
		caractere.put(112,'6');
		caractere.put(37,'7');
		caractere.put(292,'8');
		caractere.put(100,'9');
		caractere.put(148,'*');
	}
	
	
	
	public int decodage()
	{
		int cmpBarre=0;
		int cmpPixel=0;
		int cmpCar=0;
		int largeurMin=0;
		int largeurMax=0;
		int milieu=this.hauteurImage/2;
		int i=0;
		int bitCode = 0;
		int resBitCode = 0;
		int couleur;
		boolean premierPassage=true;
		String res = null;
		char temp;
		
		while(this.plan.getPixel(i, milieu)==255)
			i++;
		if(premierPassage)
		{
			couleur = this.plan.getPixel(i, milieu);
			while(couleur==this.plan.getPixel(i, milieu))
			{
				cmpPixel++;
				i++;
			}
			largeurMin=cmpPixel;
			largeurMax=cmpPixel;
			cmpBarre++;
			cmpPixel=0;
			premierPassage=false;
		}
		
		while(cmpBarre<NB_BARRES)
		{
			couleur=this.plan.getPixel(i, milieu);
			while(couleur==this.plan.getPixel(i, milieu))
			{
				cmpPixel++;
				i++;
			}
			if(cmpPixel<largeurMin)
				largeurMin=cmpPixel;
			if(cmpPixel>largeurMax)
				largeurMax=cmpPixel;
			cmpPixel=0;
			cmpBarre++;
		}
		
		i=0;
		cmpBarre=0;

		while(this.plan.getPixel(i, milieu)!=0)
			i++;

		while(cmpBarre<NB_BARRES)
		{
			couleur=this.plan.getPixel(i, milieu);
			while(this.plan.getPixel(i, milieu)==couleur)
			{
				cmpPixel++;
				i++;
			}
			if(cmpPixel<=largeurMin+MARGE && cmpPixel>=largeurMin-MARGE)
			{
				bitCode = 0;
			}
			if(cmpPixel<=largeurMax+MARGE && cmpPixel>=largeurMax-MARGE)
			{
				bitCode = 1;
			}
			resBitCode = resBitCode + bitCode*((int)Math.pow(2,(8-cmpBarre)));
			cmpPixel=0;
			cmpBarre++;
		}
		if(resBitCode != 148) // 148 -> code de l'�toile
		{
			// premier caract�re diff�rent de l'�toile -> pas bon => retourner une erreur
			return -1;
		}
		/*if (compareCar(tab,Caractere.TAB_ETOILE) == false)
			return res;
		*/
		cmpBarre=0;
		resBitCode = 0;
		
		couleur=this.plan.getPixel(i, milieu);
		while(this.plan.getPixel(i, milieu)==couleur)
			i++;
		
		while(true) // tant qu'on n'arrive pas au caract�re de fin
		{
			while(cmpBarre<NB_BARRES)
			{
				couleur=this.plan.getPixel(i, milieu);
				while(this.plan.getPixel(i, milieu)==couleur)
				{
					cmpPixel++;
					i++;
				}
				
				if(cmpPixel<=largeurMin+MARGE && cmpPixel>=largeurMin-MARGE)
					bitCode = 0;
				if(cmpPixel<=largeurMax+MARGE && cmpPixel>=largeurMax-MARGE)
					bitCode = 1;
				resBitCode = resBitCode + bitCode*((int)Math.pow(2,(8-cmpBarre)));
				cmpPixel=0;
				cmpBarre++;
					
			}
			if(resBitCode == 148)
			{
				break;
			}
			if(caractere.containsKey(resBitCode))
			{
				res = res + caractere.get(resBitCode);
				cmpCar++;
			}
			else
			{
				//ERREUR
				return -1;
			}
			resBitCode=0;
			cmpBarre=0;
			couleur=this.plan.getPixel(i, milieu);
			while(this.plan.getPixel(i, milieu)==couleur)
				i++;
		}
		
		return Integer.parseInt(res); 
	}
	
}
