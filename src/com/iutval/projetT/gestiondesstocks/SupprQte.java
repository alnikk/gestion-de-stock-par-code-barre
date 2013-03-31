package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity is used for removing amount in database's stock of this product.
 * @author Alexandre Guyon
 */
public class SupprQte extends Activity 
{
	//*************************** Attributes ************************
	/**
	 * Product's id
	 */
	private int ref;
	
	//*************************** State ****************************
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_maison);
		
		Intent intent = getIntent();		
		this.ref = intent.getExtras().getInt("refArt");
		
		TextView txt = (TextView) findViewById(R.id.title);
		txt.setText(R.string.supprQte);
	}
	
	//***************************** Buttons **********************
	
	/**
	 * Kill activity when pushing this button
	 */
	public void annuler(View view)
	{
		finish();
	}
	
	/**
	 * Remove amount in database's stock of this product
	 */
	public void valider(View view)
	{
		TextView zt_qte = (TextView) findViewById(R.id.value);
		
		if(zt_qte.getText() == null || zt_qte.getText().equals("") || zt_qte.getText() == "")
		{
			Toast.makeText(getApplicationContext(), "Entrer un id !", Toast.LENGTH_LONG).show();
			return;
		}
		
		int qte = Integer.parseInt(zt_qte.getText().toString());
		
		Article articleQte = new Article();
		articleQte.setId(this.ref);
		articleQte.setQte(qte);
		articleQte.setAct(Action.REMOVAL);
		
		ExecURL add = new ExecURL();
		add.sendArt(articleQte);
		add.start();
		
		finish();
	}
}
