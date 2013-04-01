package com.iutval.projetT.gestiondesstocks.in;
public class Caractere
{
	public final static int TAB_A[]={1,0,0,0,0,1,0,0,1};
	public final static int TAB_B[] = {0,0,1,0,0,1,0,0,1};
	public final static int TAB_C[] = {1,0,1,0,0,1,0,0,0};
	public final static int TAB_D[] = {0,0,0,0,1,1,0,0,1};
	public final static int TAB_E[] = {1,0,0,0,1,1,0,0,0};
	public final static int TAB_F[] = {0,0,1,0,1,1,0,0,0};
	public final static int TAB_G[] = {0,0,0,0,0,1,1,0,1};
	public final static int TAB_H[] = {1,0,0,0,0,1,1,0,0};
	public final static int TAB_I[] = {0,0,1,0,0,1,1,0,0};
	public final static int TAB_J[] = {0,0,0,0,1,1,1,0,0};
	public final static int TAB_K[] = {1,0,0,0,0,0,0,1,1};
	public final static int TAB_L[] = {0,0,1,0,0,0,0,1,1};
	public final static int TAB_M[] = {1,0,1,0,0,0,0,1,0};
	public final static int TAB_N[] = {0,0,0,0,1,0,0,1,1};
	public final static int TAB_O[] = {1,0,0,0,1,0,0,1,0};
	public final static int TAB_P[] = {0,0,1,0,1,0,0,1,0};
	public final static int TAB_Q[] = {0,0,0,0,0,0,1,1,1};
	public final static int TAB_R[] = {1,0,0,0,0,0,1,1,0};
	public final static int TAB_S[] = {0,0,1,0,0,0,1,1,0};
	public final static int TAB_T[] = {0,0,0,0,1,0,1,1,0};
	public final static int TAB_U[] = {1,1,0,0,0,0,0,0,1};
	public final static int TAB_V[] = {0,1,1,0,0,0,0,0,1};
	public final static int TAB_W[] = {1,1,1,0,0,0,0,0,0};
	public final static int TAB_X[] = {0,1,0,0,1,0,0,0,1};
	public final static int TAB_Y[] = {1,1,0,0,1,0,0,0,0};
	public final static int TAB_Z[] = {0,1,1,0,1,0,0,0,0};
	public final static int TAB_0[] = {0,0,0,1,1,0,1,0,0};
	public final static int TAB_1[] = {1,0,0,1,0,0,0,0,1};
	public final static int TAB_2[] = {0,0,1,1,0,0,0,0,1};
	public final static int TAB_3[] = {1,0,1,1,0,0,0,0,0};
	public final static int TAB_4[] = {0,0,0,1,1,0,0,0,1};
	public final static int TAB_5[] = {1,0,0,1,1,0,0,0,0};
	public final static int TAB_6[] = {0,0,1,1,1,0,0,0,0};
	public final static int TAB_7[] = {0,0,0,1,0,0,1,0,1};
	public final static int TAB_8[] = {1,0,0,1,0,0,1,0,0};
	public final static int TAB_9[] = {0,0,1,1,0,0,1,0,0};
	public final static int TAB_ETOILE[] = {0,1,0,0,1,0,1,0,0};
	public final static int TAILLE_TAB=9;
	private char car;
	private int tab[];
	
	public Caractere(char car, int tab[])
	{
		this.car=car;
		this.tab=tab;
	}
	
	public char getCar()
	{
		return this.car;
	}
	
	public int[] getTab()
	{
		int tab2[]=this.tab;
		return tab2;
	}
	
	public int getElement(int i)
	{
		return this.tab[i];
	}
}
