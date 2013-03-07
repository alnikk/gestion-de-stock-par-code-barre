package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to create new article in database. 
 * @author alexandre
 */
public class NewArticle extends Activity 
{
	//********************* Variable ***************************
	
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
		
		Intent intent = getIntent();
		this.art = new Article();
		this.art.setId(intent.getExtras().getInt("refArt"));
		
		TextView zt_ref = (TextView) findViewById(R.id.zt_ref);
		zt_ref.setText(this.art.getId()+"");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_article, menu);
		return true;
	}
	
	//********************* Button ***************************
	
	public void valider(View view)
	{
		TextView zt_nom = (TextView) findViewById(R.id.zt_nom);
		if(zt_nom.getText() == null || zt_nom.getText().equals(""))
			Toast.makeText(getApplicationContext(), "Entrer un nom", Toast.LENGTH_LONG).show();
		else
		{
			ExecURL send = new ExecURL();
			
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
				
			this.art.setAct(Action.NEW);
			send.sendArt(art);
			send.start();
			try 
			{
				send.join();
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			Intent intent = new Intent(this, Result.class);
			intent.putExtra("refArt", this.art.getId());
			startActivity(intent);
		}
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		finish();
	}
}
