package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Result extends Activity 
{
	//********************* Variable ***************************

	/**
	 * Reference of the item
	 */
	private int refArt;
	
	/**
	 * Item's name in database
	 */
	private String nom;
	
	/**
	 * Item's description in database
	 */
	private String desc;
	
	/**
	 * Item's amount in database
	 */
	private int qte;
	
	//********************* State ***************************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Intent intent = getIntent();
		this.refArt = intent.getExtras().getInt("refArt");
		
		JSONThread get = new JSONThread(this.refArt, this);
		get.start();
		try 
		{
			get.join();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		this.qte = get.getQte();
		this.nom = get.getNom();
		this.desc = get.getDesc();
		
		TextView txt;
		txt =  (TextView) findViewById(R.id.txt_ref);
		txt.setText(this.refArt + "");
		txt =  (TextView) findViewById(R.id.txt_nom);
		txt.setText(this.nom);
		txt =  (TextView) findViewById(R.id.txt_qte);
		txt.setText(this.qte + "");
		txt =  (TextView) findViewById(R.id.txt_desc);
		txt.setText(this.desc + "");
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}

	//********************* Button ***************************
	
	public void retour(View view)
	{
		Intent intent = new Intent(this, ChoixAction.class);
		intent.putExtra("refArt", this.refArt);
		startActivity(intent);
	}
}
