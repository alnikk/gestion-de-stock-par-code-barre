package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddQte extends Activity 
{
	private int ref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_maison);
		
		Intent intent = getIntent();		
		this.ref = intent.getExtras().getInt("refArt");		
	}
	
	public void annuler(View view)
	{
		finish();
	}
	
	public void valider(View view)
	{
		TextView zt_qte = (TextView) findViewById(R.id.openId);
		
		if(zt_qte.getText() == null || zt_qte.getText().equals("") || zt_qte.getText() == "")
		{
			Toast.makeText(getApplicationContext(), "Entrer un id !", Toast.LENGTH_LONG).show();
			return;
		}
		
		int qte = Integer.parseInt(zt_qte.getText().toString());
		
		Article articleQte = new Article();
		articleQte.setId(this.ref);
		articleQte.setQte(qte);
		articleQte.setAct(Action.ADD);
		
		ExecURL add = new ExecURL();
		add.sendArt(articleQte);
		add.start();
		
		finish();
	}
}
