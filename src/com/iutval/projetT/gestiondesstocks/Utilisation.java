package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Utilisation extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utilisation);
	}
	
	public void photo(View view)
	{
		Intent intent = new Intent(this, Photo.class);
		startActivity(intent);
	}
	
	public void checkRef(View view)
	{
		//TODO nothing for now
	}
}
