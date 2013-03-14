package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

/**
 * This class is used for viewing product's details in database.
 * @author Alexandre Guyon
 */
public class Result extends Activity 
{
	//********************* Variable ***************************

	/**
	 * The article
	 */
	private Article art;
	
	//********************* State ***************************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		// Get product's id
		Intent intent = getIntent();
		JSONThread get = new JSONThread(intent.getExtras().getInt("refArt"), this);
		get.start(); // Start receiving informations
		try 
		{
			get.join();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		this.art = get.getArt();
		// Sending informations to the view
		TextView txt;
		txt =  (TextView) findViewById(R.id.txt_ref);
		txt.setText(this.art.getId() + "");
		txt =  (TextView) findViewById(R.id.txt_nom);
		txt.setText(this.art.getNom());
		txt =  (TextView) findViewById(R.id.txt_qte);
		txt.setText(this.art.getQte() + "");
		txt =  (TextView) findViewById(R.id.txt_desc);
		txt.setText(this.art.getDescription() + "");
		txt =  (TextView) findViewById(R.id.txt_pu);
		txt.setText(this.art.getPu() + "");
	}
}
