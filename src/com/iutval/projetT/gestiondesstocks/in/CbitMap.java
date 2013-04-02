package com.iutval.projetT.gestiondesstocks.in;

import android.util.Log;

public class CbitMap {

	private Cplan planBleu;
	private Cplan planRouge;
	private Cplan planVert;
	private int hauteurImage;
	private int largeurImage;
	private Caractere tabCar[];
	
	private final static int NB_BARRES=9;
	private final static int MARGE=1;
	
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
		
		Caractere carA = new Caractere('A',Caractere.TAB_A);
		Caractere carB = new Caractere('B',Caractere.TAB_B);
		Caractere carC = new Caractere('C',Caractere.TAB_C);
		Caractere carD = new Caractere('D',Caractere.TAB_D);
		Caractere carE = new Caractere('E',Caractere.TAB_E);
		Caractere carF = new Caractere('F',Caractere.TAB_F);
		Caractere carG = new Caractere('G',Caractere.TAB_G);
		Caractere carH = new Caractere('H',Caractere.TAB_H);
		Caractere carI = new Caractere('I',Caractere.TAB_I);
		Caractere carJ = new Caractere('J',Caractere.TAB_J);
		Caractere carK = new Caractere('K',Caractere.TAB_K);
		Caractere carL = new Caractere('L',Caractere.TAB_L);
		Caractere carM = new Caractere('M',Caractere.TAB_M);
		Caractere carN = new Caractere('N',Caractere.TAB_N);
		Caractere carO = new Caractere('O',Caractere.TAB_O);
		Caractere carP = new Caractere('P',Caractere.TAB_P);
		Caractere carQ = new Caractere('Q',Caractere.TAB_Q);
		Caractere carR = new Caractere('R',Caractere.TAB_R);
		Caractere carS = new Caractere('S',Caractere.TAB_S);
		Caractere carT = new Caractere('T',Caractere.TAB_T);
		Caractere carU = new Caractere('U',Caractere.TAB_U);
		Caractere carV = new Caractere('V',Caractere.TAB_V);
		Caractere carW = new Caractere('W',Caractere.TAB_W);
		Caractere carX = new Caractere('X',Caractere.TAB_X);
		Caractere carY = new Caractere('Y',Caractere.TAB_Y);
		Caractere carZ = new Caractere('Z',Caractere.TAB_Z);
		Caractere car0 = new Caractere('0',Caractere.TAB_0);
		Caractere car1 = new Caractere('1',Caractere.TAB_1);
		Caractere car2 = new Caractere('2',Caractere.TAB_2);
		Caractere car3 = new Caractere('3',Caractere.TAB_3);
		Caractere car4 = new Caractere('4',Caractere.TAB_4);
		Caractere car5 = new Caractere('5',Caractere.TAB_5);
		Caractere car6 = new Caractere('6',Caractere.TAB_6);
		Caractere car7 = new Caractere('7',Caractere.TAB_7);
		Caractere car8 = new Caractere('8',Caractere.TAB_8);
		Caractere car9 = new Caractere('9',Caractere.TAB_9);
		Caractere carEtoile = new Caractere('*',Caractere.TAB_ETOILE);
		this.tabCar[] = {carA,carB,carC,carD,carE,carF,carG,carH,carI,carJ,carK,carL,carM,carN,carO,carP,carQ,carR,carS,carT,carU,carV,carW,carX,carY,carZ,car0,car1,car2,car3,car4,car5,car6,car7,car8,car9,carEtoile};
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
	
	public char[] decodage()
	{
		int cmpBarre=0;
		int cmpPixel=0;
		int cmpCar=1;
		int largeurMin=0;
		int largeurMax=0;
		int milieu=this.hauteurImage/2;
		int i=0;
		byte couleur;
		boolean premierPassage=true;
		int tab[] = null;
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
				tab[cmpBarre]=0;
		
			if(cmpPixel<=largeurMax+MARGE && cmpPixel>=largeurMax-MARGE)
				tab[cmpBarre]=1;

			cmpPixel=0;
			cmpBarre++;
		}
		
		if (compareCar(tab,Caractere.TAB_ETOILE) == false)
			return res;
		cmpBarre=0;
		for(int n=0;n<Caractere.TAILLE_TAB;n++)
			tab[n]=0;
		
		couleur=this.getGrayPixel(i,milieu);
		while(this.getGrayPixel(i,milieu)==couleur)
			i++;
		
		while(compareCar(tab,Caractere.TAB_ETOILE)==false)
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
					tab[cmpBarre]=0;

				if(cmpPixel<=largeurMax+MARGE && cmpPixel>=largeurMax-MARGE)
					tab[cmpBarre]=1;
				cmpPixel=0;
				cmpBarre++;
				
				int a=0;

				while ((a<=37) && (compareCar(tab,this.tabCar[a].getTab()) == false))
					a++;

				if(a<36)
				{
					temp = this.tabCar[a].getCar();
					res[cmpCar]=temp;
					cmpCar++;
				}

				cmpBarre=0;
				
				couleur=this.getGrayPixel(i,milieu);
				while(this.getGrayPixel(i,milieu)==couleur)
					i++;
			}
		}
		
		//Log.d("CbitMap.class", "coucou");
		
		return res;
	}
	
	public boolean compareCar(int tab[],int tab2[])
	{
		for(int i=0;i<9;i++)
		{
			if(tab[i]!=tab2[i])
				return false;
		}
		return true;
	}
}
