package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OpenId extends Activity 
{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_maison);
	}
	
	public void annuler(View view)
	{
		finish();
	}
	
	public void valider(View view)
	{
		TextView zt_id = (TextView) findViewById(R.id.openId);
		
		if(zt_id.getText() == null || zt_id.getText().equals("") || zt_id.getText() == "")
		{
			Toast.makeText(getApplicationContext(), "Entrer un id !", Toast.LENGTH_LONG).show();
			return;
		}
		
		int refArt = Integer.parseInt(zt_id.getText().toString());
		
		// Gives argument
		Intent intent = new Intent(this, ChoixAction.class);
		intent.putExtra("refArt", refArt);
		startActivity(intent);
	}
}
