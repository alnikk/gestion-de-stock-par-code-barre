package com.iutval.projetT.gestiondesstocks.in;

public class Bitmap 
{
	byte img[];
	
	public Bitmap(byte tab[])
	{
		img=tab;
	}
	
	public byte getElt(int i)
	{
		return this.img[i];
	}
}
