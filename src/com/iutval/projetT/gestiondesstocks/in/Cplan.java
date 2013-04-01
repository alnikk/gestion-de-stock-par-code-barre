package com.iutval.projetT.gestiondesstocks.in;

public class Cplan {

	private int hauteurPlan;
	private int largeurPlan;
	private byte plan[];
	
	public Cplan()
	{
		hauteurPlan=0;
		largeurPlan=0;
	}
	
	public Cplan(int largeur, int hauteur)
	{
		int i;
		hauteurPlan=hauteur;
		largeurPlan=largeur;
		for(i=0;i<hauteur*largeur;i++)
		{
			plan[i]=0;
		}
	}
	
	public int getLargeur()
	{
		return this.largeurPlan;
	}
	
	public int getHauteur()
	{
		return this.hauteurPlan;
	}
	
	public byte getPixel(int pos)
	{
		return plan[pos];
	}
	
	public byte getPixel(int x, int y)
	{
		return plan[y*this.largeurPlan+x];
	}
	
	public void setPixel(int pos, byte intensite)
	{
		plan[pos]=intensite;
	}
	
	public void setPixel(int x, int y, byte intensite)
	{
		plan[y*this.largeurPlan+x]=intensite;
	}
}
