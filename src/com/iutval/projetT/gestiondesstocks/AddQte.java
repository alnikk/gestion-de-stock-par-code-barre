package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity for add stock in a database for a product. 
 * 
 * @author Alexandre Guyon
 *
 */
public class AddQte extends Activity 
{
	/**
	 * Product reference
	 */
	private int ref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_maison);
		
		// Get product reference
		Intent intent = getIntent();
		this.ref = intent.getExtras().getInt("refArt");
		
		TextView txt = (TextView) findViewById(R.id.title);
		txt.setText(R.string.addQte);
	}
	
	public void annuler(View view)
	{
		finish();
	}
	
	public void valider(View view)
	{
		TextView zt_qte = (TextView) findViewById(R.id.value);
		
		if(zt_qte.getText() == null || zt_qte.getText().equals("") || zt_qte.getText() == "")
		{
			Toast.makeText(getApplicationContext(), "Entrer une quantitée", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Transform the string to int
		int qte = Integer.parseInt(zt_qte.getText().toString());
		// Create new article with amount to add
		Article articleQte = new Article();
		articleQte.setId(this.ref);
		articleQte.setQte(qte);
		articleQte.setAct(Action.ADD);
		// Send information to the server
		ExecURL add = new ExecURL();
		add.sendArt(articleQte);
		add.start();
		
		finish();
	}
}
