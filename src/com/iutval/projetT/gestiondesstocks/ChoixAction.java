package com.iutval.projetT.gestiondesstocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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

	//private static final String url = "http://alexgus.no-ip.info/android/script.php";
	/**
	 * Script's address. 
	 */
	private static final String URL = "http://192.168.1.20/android/script.php?";

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

		// Get informations about item for handle error
		this.getInfoJson(this.readJsonURL(URL));
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

		TextView text = (TextView) findViewById(R.id.value);
		String qte = text.getText().toString();

		RadioButton radiobt = (RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioChoix)).getCheckedRadioButtonId());

		if(radiobt.getText().toString().equals("Ajouter") && this.exist)
		{
			act = Action.ADD;
			ok = true;
		}
		else
			Toast.makeText(getApplicationContext(), "Cet élément n'existe pas", Toast.LENGTH_LONG).show();
		if(radiobt.getText().toString().equals("Retrait") && this.exist && this.qte >= Integer.parseInt(qte))
		{
			act = Action.REMOVAL;
			ok = true;
		}
		else
			Toast.makeText(getApplicationContext(), "Cet élément n'existe pas ou vous essayer d'en enlever une quantité trop importante"
					, Toast.LENGTH_LONG).show();
		if(radiobt.getText().toString().equals("Nouveau") && !this.exist)
		{
			act = Action.NEW;
			ok = true;
		}
		else
			Toast.makeText(getApplicationContext(), "Cet élément existe déjà !", Toast.LENGTH_LONG).show();
		if(radiobt.getText().toString().equals("Suppression") && this.exist)
		{
			act = Action.DELETE;
			ok = true;
		}
		else
			Toast.makeText(getApplicationContext(), "Cet élément n'existe pas", Toast.LENGTH_LONG).show();
		if(radiobt.getText().toString().equals("Consulter") && this.exist)
		{
			act = Action.SEE;
			ok = true;
		}
		else
			Toast.makeText(getApplicationContext(), "Cet élément n'existe pas", Toast.LENGTH_LONG).show();

		if(act == null)
			act = Action.SEE;
		
		if(ok)
		{
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.putExtra("refArt", this.refArt);
			intent.putExtra("act", act.toString().toLowerCase());
			intent.putExtra("qte", qte);
			startActivity(intent);
		}
	}

	//***************** Method *******************

	private String readJsonURL(String url)
	{
		// Variable
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String line;

		try 
		{
			// Request server
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == 200) // Test if the answer is positive 
			{
				// Get stream from server
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				while ((line = reader.readLine()) != null)
					builder.append(line);
			}
			else 
				Log.e(ChoixAction.class.toString(), "Failed to download file");
		}
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		// Return the Json String
		return builder.toString();
	}

	private void getInfoJson(String json)
	{
		try 
		{
			JSONArray jsonArray = new JSONArray(json);
			if(!jsonArray.isNull(0))
			{
				this.exist = true;
				JSONObject jsonObject = jsonArray.getJSONObject(0); // There's only one object
				this.qte = jsonObject.getInt("valeur");
			}
			else
				this.exist = false;
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
}
