package com.iutval.projetT.gestiondesstocks.in;

import android.util.Log;

public class CbitMap {

	private Cplan planBleu;
	private Cplan planRouge;
	private Cplan planVert;
	private int hauteurImage;
	private int largeurImage;
	private Caractere tabCar[];
	
	private HashMap<int,char> caractere;
	
	private final static int NB_BARRES=9;
	private final static int MARGE=1;
	private final static int TAILLE_TAB=9;
	
	public CbitMap()
	{
		hauteurImage=0;
		largeurImage=0;
	}
	
	public CbitMap(Bitmap bm)
	{
		this.largeurImage=this.recupInfo(bm, 4, 18);
		this.hauteurImage=this.recupInfo(bm, 4, 22);
		this.planBleu=new Cplan(largeurImage,hauteurImage);
		this.planRouge=new Cplan(largeurImage,hauteurImage);
		this.planVert=new Cplan(largeurImage,hauteurImage);
		splitInto3Plan(bm);
		this.seuillage();
		
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
	
	private int recupInfo(Bitmap bm, int nbOctets, int offset)
	{
		int resultat=0;
		int i;
		for(i=nbOctets-1;i>=0;i--)
		{
			resultat=resultat*256+bm.getElt(i+offset);
		}
		return resultat;
	}
	
	private void splitInto3Plan(Bitmap bm)
	{
		int ligne, colonne, nbOctetsPadding;
		int posPixel=0;
		int cpt=0;
		nbOctetsPadding=this.largeurImage%4;
		for(ligne=0;ligne<this.hauteurImage;ligne++)
		{
			for(colonne=0;colonne<this.largeurImage;colonne++)
			{
				this.planBleu.setPixel(posPixel, bm.getElt(cpt));
				cpt++;
				this.planVert.setPixel(posPixel, bm.getElt(cpt));
				cpt++;
				this.planRouge.setPixel(posPixel, bm.getElt(cpt));
				posPixel++;
				cpt++;
			}
			cpt+=nbOctetsPadding;
		}
	}
		
	private byte getGrayPixel(int x, int y)
	{
		byte r,v,b;
		r=this.planRouge.getPixel(x, y);
		v=this.planVert.getPixel(x, y);
		b=this.planBleu.getPixel(x, y);
		return (byte) (0.3*r+0.59*v+0.11*b);
	}
	
	private byte getGrayPixel(int pos)
	{
		byte r,v,b;
		r=this.planRouge.getPixel(pos);
		v=this.planVert.getPixel(pos);
		b=this.planBleu.getPixel(pos);
		return (byte) (0.3*r+0.59*v+0.11*b);
	}
	
	private void setGrayPixel(int pos, byte intensite)
	{
		this.planRouge.setPixel(pos,intensite);
		this.planVert.setPixel(pos,intensite);
		this.planBleu.setPixel(pos,intensite);
	}
	
	
	public void seuillage()
	{
		int i,n;
		byte pixelCourant;
		for(i=0;i<this.hauteurImage*this.largeurImage;i++)
		{
			pixelCourant=this.getGrayPixel(i);
			if pixelCourant < 128
				this.setGrayPixel(i,0);
			else
				this.setGrayPixel(i,255);
		}
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
		int resbitCode = 0;
		byte couleur;
		boolean premierPassage=true;
		char res[] = null;
		char temp;
		
		while(this.getGrayPixel(i, milieu)==255)
			i++;
		if(premierPassage)
		{
			couleur=this.getGrayPixel(i,milieu);
			while(couleur==this.getGrayPixel(i,milieu))
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
			couleur=this.getGrayPixel(i,milieu);
			while(couleur==this.getGrayPixel(i,milieu))
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

		while(this.getGrayPixel(i,milieu)!=0)
			i++;

		while(cmpBarre<NB_BARRES)
		{
			couleur=this.getGrayPixel(i,milieu);
			while(this.getGrayPixel(i,milieu)==couleur)
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
			resBitCode = resbitCode + bitCode*((int)math.pow(2,(8-cmpBarre)));
			cmpPixel=0;
			cmpBarre++;
		}
		if(resBitCode != 148)) // 148 -> code de l'étoile
		{
			// premier caractère différent de l'étoile -> pas bon => retourner une erreur
			return null;
		}
		/*if (compareCar(tab,Caractere.TAB_ETOILE) == false)
			return res;
		*/
		cmpBarre=0;
		resBitCode = 0;
		
		couleur=this.getGrayPixel(i,milieu);
		while(this.getGrayPixel(i,milieu)==couleur)
			i++;
		
		while(true) // tant qu'on n'arrive pas au caractère de fin
		{
			while(cmpBarre<NB_BARRES)
			{
				couleur=this.getGrayPixel(i,milieu);
				while(this.getGrayPixel(i,milieu)==couleur)
				{
					cmpPixel++;
					i++;
				}
				
				if(cmpPixel<=largeurMin+MARGE && cmpPixel>=largeurMin-MARGE)
					bitCode = 0;
				if(cmpPixel<=largeurMax+MARGE && cmpPixel>=largeurMax-MARGE)
					bitCode = 1;
				resBitCode = resbitCode + bitCode*((int)math.pow(2,(8-cmpBarre)));
				cmpPixel=0;
				cmpBarre++;
					
			}
			if(resBitCode == 148)
			{
				break;
			}
			if(caractere.containsKey(resBitCode))
			{
				res = res.concat(caractere.get(resBitCode))
				cmpCar++;
			}
			else
			{
				//ERREUR
				return null;
			}
			resBitCode=0;
			cmpBarre=0;
			couleur=this.getGrayPixel(i,milieu);
			while(this.getGrayPixel(i,milieu)==couleur)
				i++;
		}
		
		//Log.d("CbitMap.class", "coucou");
		return Integer.parseInt(res); 
	}
	
}
