package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to create new article in database. 
 * @author Alexandre Guyon
 */
public class NewArticle extends Activity 
{
	//********************* Attributes ***************************
	
	/**
	 * Reference of the item
	 */
	private Article art;

	//********************* State ***************************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_article);
		
		// Get product's id to create
		Intent intent = getIntent();
		this.art = new Article();
		this.art.setId(intent.getExtras().getInt("refArt"));
		
		// Set it in interface
		TextView zt_ref = (TextView) findViewById(R.id.zt_ref);
		zt_ref.setText(this.art.getId()+"");
	}

	//********************* Button ***************************
	
	/**
	 * Button 'valider'.
	 * It used to send user input data (key in before), to the database
	 * for creating new products in this one.
	 */
	public void valider(View view)
	{
		// Get the name of the products
		TextView zt_nom = (TextView) findViewById(R.id.zt_nom);
		if(zt_nom.getText() == null || zt_nom.getText().equals(""))
			Toast.makeText(getApplicationContext(), "Entrer un nom", Toast.LENGTH_LONG).show();
		else
		{	// Create ExecURL for send informations to server.
			ExecURL send = new ExecURL();
			
			// Get informations key in by user
			this.art.setNom(zt_nom.getText().toString());
			
			if(((TextView) findViewById(R.id.zt_desc)).getText() != null && !((TextView) findViewById(R.id.zt_desc)).getText().equals(""))
				this.art.setDescription(((TextView) findViewById(R.id.zt_desc)).getText().toString());
			
			if(((TextView) findViewById(R.id.zt_qte)).getText() != null && !((TextView) findViewById(R.id.zt_qte)).getText().equals(""))
			{
				CharSequence cs = ((TextView) findViewById(R.id.zt_qte)).getText();
				String s = cs.toString();
				int q = Integer.parseInt(s);
				this.art.setQte(q);
			}
			
			if(((TextView) findViewById(R.id.zt_pu)).getText() != null && !((TextView) findViewById(R.id.zt_pu)).getText().equals(""))
			{
				CharSequence cs = ((TextView) findViewById(R.id.zt_pu)).getText();
				String s = cs.toString();
				int q = Integer.parseInt(s);
				this.art.setPu(q);
			}
			
			// Set the action to do with this product
			this.art.setAct(Action.NEW);
			send.sendArt(art); // Send it to thread
			send.start(); // Send to server
			try 
			{
				send.join(); // Wait answer from server
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			// Show information database for monitoring.
			Intent intent = new Intent(this, Result.class);
			intent.putExtra("refArt", this.art.getId());
			startActivity(intent);
		}
	}
	
	// TODO test it, it used for do what??
	@Override
	protected void onStop() 
	{
		super.onStop();
		finish();
	}
}
