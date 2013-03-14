package com.iutval.projetT.gestiondesstocks;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class ExecURL extends Thread
{
	//********************** Constants *******************
	/**
	 * Script's address. 
	 */
	//private static final String URL = "http://alexgus.no-ip.info/android/script.php";
	private static final String URL = "http://192.168.1.20/android/script.php?";
	
	//********************** Variable *******************
	/**
	 * The products
	 */
	private Article art;
	
	/**
	 * The context of the activity who is running
	 */
	protected Context context = null;
	
	/**
	 * The ProgressDialog for waiting sending process
	 */
	protected ProgressDialog prgD = null;
	
	//********************** Constructors ****************
	/**
	 * Default Constructor.
	 * It set the article to handle database.
	 */
	public ExecURL()
	{	
		this.art = new Article();
	}
	
	/**
	 * Default Constructor.
	 * It set the article to handle database.
	 * It create too an progessDialog for waiting.
	 */
	public ExecURL(Context c)
	{
		this.art = new Article();
		this.context = c;
		this.createWait();
	}

	//********************** Main *******************
	/**
	 * Main of this thread
	 */
	public void run()
	{
		this.sendInfo();
		this.stopWait();
	}
	
	//********************** Methods *******************
	/**
	 * Send information to server
	 */
	private void sendInfo() 
	{
		// Get the URL on the script on the server
		String url = URL; 
		
		// switch on action to do in the database
		switch(this.art.getAct())
		{
			case ADD: // Add amount
				if(this.art.getId() != 0 && this.art.getQte() != 0)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&valeur=" + this.art.getQte();
					url = url + "&action=add";
				}
				break;
			case REMOVAL: // remove amount
				if(this.art.getId() != 0 && this.art.getQte() != 0)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&valeur=" + this.art.getQte();
					url = url + "&action=removal";
				}
				break;
			case NEW: // New product
				if(this.art.getId() != 0 && this.art.getNom() != null)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&nom=" + this.art.getNom();
					if(this.art.getDescription() != null)
					{
						
						url = url + "&description=" + this.art.getDescription();
					}
					if(this.art.getQte() != 0)
						url = url + "&valeur=" + this.art.getQte();
					if(this.art.getPu() != 0)
						url = url + "&pu=" + this.art.getPu();
					url = url + "&action=new";
				}					
				break;
			case DELETE: // Delete product
				if(this.art.getId() != 0)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&action=delete";
				}
				break;
			default:
				break;
		}
		
		// Remove all space in request
		url = url.replaceAll(" ", "%20");
		
		Log.d("ExecURL.class", url);
		
		// Request on server
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try 
		{
			client.execute(httpGet); // Send the request
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Create WaitBar
	 */
	private void createWait()
	{
		this.prgD = new ProgressDialog(this.context); // Send the context activity to the progressDialog
		this.prgD.setTitle("Execution"); // TODO Changer dans String
		this.prgD.setMessage("Receptions des informations");
		this.prgD.show();
	}
	
	/**
	 * Stop WaitBar
	 */
	private void stopWait()
	{
		if(this.prgD != null)
			this.prgD.dismiss();
	}
	
	//********************** Getters & Setters *******************
	
	public Article getArt() 
	{
		return art;
	}

	public void sendArt(Article art) 
	{
		this.art = art;
	}
}
