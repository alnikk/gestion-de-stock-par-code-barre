package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ChoixAction extends Activity 
{
	//**************** Variable *********************
	/**
	 * Reference of the item
	 */
	private int refArt;
	
	/**
	 * Know if the item exist or not
	 */
	private boolean exist; 
	
	/**
	 * The amount of this same item in the stock
	 */
	private int qte = 0;

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

		// Run thread for handle infromations in database
		getJSON = new JSONThread(this.refArt);
		getJSON.start();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choix_action, menu);
		return true;
	}

	//**************** Button ********************

	/**
	 * Button validate 
	 * @param view
	 */
	public void validate(View view)
	{
		// Initializations
		Action act = null;
		boolean ok = false;

		// Wait for informations
		try 
		{
			this.getJSON.join();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		this.exist = this.getJSON.isExist();
		this.qte = this.getJSON.getQte();
		
		TextView text = (TextView) findViewById(R.id.value);
		String qte = text.getText().toString();

		RadioButton radiobt = (RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioChoix)).getCheckedRadioButtonId());
		
		
		if(this.exist)
		{
			if(radiobt.getText().toString().equals("Ajouter"))
			{
				act = Action.ADD;
				ok = true;
			}
			if(radiobt.getText().toString().equals("Suppression"))
			{
				act = Action.DELETE;
				ok = true;
			}
			if(radiobt.getText().toString().equals("Consulter"))
			{
				act = Action.SEE;
				ok = true;
			}	
			if(radiobt.getText().toString().equals("Retrait") && this.qte >= Integer.parseInt(qte))
			{
				act = Action.REMOVAL;
				ok = true;
			}
			if(radiobt.getText().toString().equals("Retrait") && this.qte < Integer.parseInt(qte))
				Toast.makeText(getApplicationContext(),
						"Cet élément n'existe pas ou vous essayer d'en enlever une quantité trop importante",
						Toast.LENGTH_LONG).show();
		}
		else
		{
			if(radiobt.getText().toString().equals("Nouveau"))
			{
				act = Action.NEW;
				ok = true;
			}
			else
				Toast.makeText(getApplicationContext(), "Cette référence n'existe pas", Toast.LENGTH_LONG).show();
		}
		
		if(ok)
		{
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.putExtra("refArt", this.refArt);
			intent.putExtra("act", act.toString().toLowerCase());
			intent.putExtra("qte", qte);
			startActivity(intent);
		}
	}
}
