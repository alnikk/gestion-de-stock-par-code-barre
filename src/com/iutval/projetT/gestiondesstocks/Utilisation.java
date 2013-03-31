package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.*;
import android.view.View;

/**
 * Main Activity.
 * This will wait user touch one of theses buttons.
 * The user have to choice between take a picture or
 * key in manually the product's id.
 * @author Alexandre Guyon
 */
public class Utilisation extends FragmentActivity
{
	//********************** State ************************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utilisation);
	}
	
	//************************ Buttons ********************
	
	/**
	 * Take a picture (launch activity)
	 */
	public void photo(View view)
	{
		Intent intent = new Intent(this, Photo.class);
		startActivity(intent);
	}
	
	/**
	 * Key in a the product's id.
	 */
	public void checkRef(View view)
	{
		Intent intent = new Intent(this, OpenId.class);
		intent.putExtra("title", getResources().getString(R.string.utilisation_title));
		startActivity(intent);
	}
}
