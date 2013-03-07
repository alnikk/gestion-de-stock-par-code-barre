package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.*;
import android.view.Menu;
import android.view.View;

public class Utilisation extends FragmentActivity
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
		Intent intent = new Intent(this, OpenId.class);
		startActivity(intent);
	}
}
