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

import android.app.ProgressDialog;
import android.content.Context;
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
	
	private Article art;
	
	private boolean exist;
	
	protected ProgressDialog prgD;	
	
	//*************** Constructor ******************
	
	public JSONThread(int ref)
	{
		this.art = null;
		this.art = new Article();
		this.art.setId(ref);
		this.exist = false;
		this.prgD = null;
	}
	
	public JSONThread(int ref, Context c)
	{
		this.art = null;
		this.art = new Article();
		this.art.setId(ref);
		this.exist = false;
		this.prgD = new ProgressDialog(c);
		this.prgD.setTitle("Search");
		this.prgD.setMessage("Receptions des informations");
		this.prgD.show();
	}
	
	//*************** main ************************
	
	public void run()
	{
		this.getInfoJson(this.readJsonURL(URL));
		if(this.prgD != null)
			this.prgD.dismiss();
	}

	//****************** Methods ******************
	
	private String readJsonURL(String url)
	{
		Log.d("JSONThread.class",this.art.getId() + "");
		// Variable
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url + "id=" + this.art.getId() + "&action=see");
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
				this.art.setNom(jsonObject.getString("labelle"));
				this.art.setDescription(jsonObject.getString("description"));
				this.art.setQte(jsonObject.getInt("qte"));
				this.art.setPu(jsonObject.getInt("pu"));
				Log.d("JSONThread.class", this.art.toString());
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
	
	public Article getArt() 
	{
		return art;
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
