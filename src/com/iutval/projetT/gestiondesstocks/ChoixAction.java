package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * This class get informations about product
 *  id in database, and send the user 
 *  automatically throw another activity. 
 * @author Alexandre Guyon
 */
public class ChoixAction extends FragmentActivity 
{
	//**************** Attributes *********************
	
	/**
	 * Reference of the item
	 */
	private Article art;

	/**
	 * Thread for handle JSON receives' informations in database
	 */
	JSONThread getJSON;

	//**************** State *********************

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Initializations
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_action);
		
		this.art = new Article();
		
		// Get argument from previous activity (Article reference)
		Intent intent = getIntent();
		this.art.setId(intent.getExtras().getInt("refArt"));

		// Run thread for handle receives' informations in database
		getJSON = new JSONThread(this.art.getId(), this);
		getJSON.start();		
		try 
		{
			getJSON.join();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		// If the product doesn't exist, automatically change
		// to NewArticle class for create new one
		if(!getJSON.isExist()) 
		{
			Intent newArt = new Intent(this, NewArticle.class);
			newArt.putExtra("refArt", this.art.getId());
			startActivity(newArt);
		}
	}
	
	// TODO à tester
	/*
	 * if(!getJSON.isExist()) 
		{
			Intent newArt = new Intent(this, NewArticle.class);
			newArt.putExtra("refArt", this.art.getId());
			startActivity(newArt);
		}
	 */
	@Override
	protected void onRestart() 
	{
		super.onRestart();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
	}
	
	//****************** Button ****************
	
	/**
	 * Method for the button 'Consulter'. It change the activity 
	 * to Result class for view information about product.
	 */
	public void consulter(View view)
	{
		Intent newArt = new Intent(this, Result.class);
		newArt.putExtra("refArt", this.art.getId());
		startActivity(newArt);
	}
	
	/**
	 * Method for the button 'Ajouter Quantité'. It change the activity 
	 * to AddQte class for add amount in database.
	 */
	public void addQte(View view)
	{
		Intent newArt = new Intent(this, AddQte.class);
		newArt.putExtra("refArt", this.art.getId());
		startActivity(newArt);
	}
	
	/**
	 * Method for the button 'Supprimer Quantité'. It change the activity 
	 * to AddQte class for remove amount in database.
	 */
	public void supprQte(View view)
	{
		Intent newArt = new Intent(this, SupprQte.class);
		newArt.putExtra("refArt", this.art.getId());
		startActivity(newArt);
	}
	
	/**
	 * Method for the button 'Supprimer produit'. It change the activity 
	 * to SupprProduit class for delete the products in database.
	 */
	public void supprProduit(View view)
	{
		SupprProduit suppr = new SupprProduit(this.art.getId());
		suppr.show(getSupportFragmentManager(),"suppr");
		//finish();
	}
}
