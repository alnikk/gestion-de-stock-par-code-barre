package com.iutval.projetT.gestiondesstocks.in;


public class Cplan {

	private int hauteurPlan;
	private int largeurPlan;
	private int plan[];
	
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
		this.plan = new int[hauteur*largeur+largeur];
		for(i=0;i<hauteur*largeur+largeur;i++)
			plan[i]=0;
	}
	
	public int getLargeur()
	{
		return this.largeurPlan;
	}
	
	public int getHauteur()
	{
		return this.hauteurPlan;
	}
	
	public int getPixel(int pos)
	{
		return plan[pos];
	}
	
	public int getPixel(int x, int y)
	{
		return plan[y*this.largeurPlan+x];
	}
	
	public void setPixel(int pos, int intensite)
	{
		plan[pos]=intensite;
	}
	
	public void setPixel(int x, int y, int intensite)
	{
		plan[y*this.largeurPlan+x]=intensite;
	}
}
