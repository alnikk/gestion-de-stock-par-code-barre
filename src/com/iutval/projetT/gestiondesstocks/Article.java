/**
 * 
 */
package com.iutval.projetT.gestiondesstocks;

import java.io.Serializable;

/**
 * Create article object
 * @author alexandre
 */
public class Article implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String nom;
	
	private String description;
	
	private int qte;
	
	private int pu;
	
	private Action act;
	
//	private ImageIcon img;

	public Article()
	{
		this.id = 0;
		this.nom = "";
		this.description = "";
		this.qte = 0;
		this.pu = 0;
		this.act = null;
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public int getQte() 
	{
		return qte;
	}

	public void setQte(int qte) 
	{
		this.qte = qte;
	}

	public int getPu() 
	{
		return pu;
	}

	public void setPu(int pu) 
	{
		this.pu = pu;
	}

	public Action getAct() 
	{
		return act;
	}

	public void setAct(Action act) 
	{
		this.act = act;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", nom=" + nom + ", description="
				+ description + ", qte=" + qte + ", pu=" + pu + ", act=" + act
				+ "]";
	}
	
	
}
