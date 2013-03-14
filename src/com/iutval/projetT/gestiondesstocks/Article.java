/**
 * 
 */
package com.iutval.projetT.gestiondesstocks;

import java.io.Serializable;

/**
 * Create article object.
 * It uses for represent a product.
 * @author Alexandre Guyon
 */
public class Article implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Product's id in database 
	 */
	private int id;
	
	/**
	 * Product's name in database
	 */
	private String nom;
	
	/**
	 * Product's description in database
	 */
	private String description;
	
	/**
	 * Product's amount in database
	 */
	private int qte;
	
	/**
	 * Product's price in database 
	 */
	private int pu;
	
	/**
	 * Action to handle product in database
	 */
	private Action act;

	/**
	 * Product's image in database 
	 */
//	private ImageIcon img;

	//*********************** Constructor **********************
	
	public Article()
	{
		this.id = 0;
		this.nom = "";
		this.description = "";
		this.qte = 0;
		this.pu = 0;
		this.act = null;
	}

	//*********************** Getters and Setters **********************	
	
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

	//*********************** toString **********************
	
	@Override
	public String toString() {
		return "Article [id=" + id + ", nom=" + nom + ", description="
				+ description + ", qte=" + qte + ", pu=" + pu + ", act=" + act
				+ "]";
	}	
}
