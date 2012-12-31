/**
 * 
 */
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

import android.util.Log;

/**
 * Class to handle JSON in external process
 * 
 * @author alexandre
 */
public class JSONThread extends Thread 
{
	//**************** Constant *********************
	//private static final String url = "http://alexgus.no-ip.info/android/script.php";
	/**
	 * Script's address. 
	 */
	private static final String URL = "http://192.168.1.20/android/script.php?";
	
	//**************** Variable *********************
	
	/**
	 * Item's ID in database
	 */
	private int id;
	
	/**
	 * Item's name in database
	 */
	private String nom;
	
	/**
	 * Item's description in database
	 */
	private String desc;
	
	/**
	 * Item's amount in database
	 */
	private int qte;
	
	private boolean exist;
	
	
	//*************** Constructor ******************
	
	public JSONThread(int ref)
	{
		this.id = ref;
		this.nom = null;
		this.desc = null;
		this.qte = 0;
		this.exist = false;
	}
	
	//*************** main ************************
	
	public void run()
	{
		this.getInfoJson(this.readJsonURL(URL));
	}

	//****************** Methods ******************
	
	private String readJsonURL(String url)
	{
		// Variable
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url + "id=" + this.id);
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
				this.nom = jsonObject.getString("nom");
				this.desc = jsonObject.getString("description");
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
	
	//*************** Getters and Setters ***********
	
	/**
	 * Returns item's ID in database
	 * @return Item's ID in database
	 */
	public int getRef() 
	{
		return id;
	}

	/**
	 * Returns item's name in database
	 * @return Item's name in database
	 */
	public String getNom() 
	{
		return nom;
	}

	/**
	 * Returns item's description in database
	 * @return Item's description in database
	 */
	public String getDesc() 
	{
		return desc;
	}

	/**
	 * Returns item's amount in database
	 * @return Item's amount in database
	 */
	public int getQte() 
	{
		return qte;
	}

	/**
	 * Check if id exist in database
	 * @return true if exist
	 */
	public boolean isExist() 
	{
		return exist;
	}	
}
