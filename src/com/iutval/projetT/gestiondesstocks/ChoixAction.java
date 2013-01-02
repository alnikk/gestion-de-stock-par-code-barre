package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

/**
 * This class get informations about product id in database, and send the user automatically throw another activity. 
 * @author alexandre
 */
public class ChoixAction extends Activity 
{
	//**************** Variable *********************
	
	/**
	 * Reference of the item
	 */
	private int refArt;

	/**
	 * Thread for handle JSON informations in databae
	 */
	JSONThread getJSON;

	//**************** State *********************

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Initializations
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_action);
		
		// Get argument
		Intent intent = getIntent();
		this.refArt = intent.getExtras().getInt("refArt");

		// Run thread for handle informations in database
		getJSON = new JSONThread(this.refArt, this);
		getJSON.start();		
		try 
		{
			getJSON.join();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		if(!getJSON.isExist())
		{
			Intent newArt = new Intent(this, NewArticle.class);
			newArt.putExtra("refArt", this.refArt);
			startActivity(newArt);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choix_action, menu);
		return true;
	}
}
