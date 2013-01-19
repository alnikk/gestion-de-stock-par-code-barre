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
	
	private Article art;
	
	protected Context context = null;
	
	protected ProgressDialog prgD = null;
	
	//********************** Constructors ****************
	
	public ExecURL(Action a)
	{	
		this.art = new Article();
		this.art.setAct(a);
	}
	
	public ExecURL(Context c, Action a)
	{
		this.art = new Article();
		this.art.setAct(a);
		this.context = c;
		this.createWait();
	}

	//********************** Main *******************
	
	public void run()
	{
		this.sendInfo();
		this.stopWait();
	}
	
	//********************** Methods *******************
	
	private void sendInfo()
	{
		String url = URL;
		
		switch(this.art.getAct())
		{
			case ADD:
				if(this.art.getId() != 0 && this.art.getQte() != 0)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&=valeur" + this.art.getQte();
					url = url + "&action=add";
				}
				break;
			case REMOVAL:
				if(this.art.getId() != 0 && this.art.getQte() != 0)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&=valeur" + this.art.getQte();
					url = url + "&action=removal";
				}
				break;
			case NEW:
				if(this.art.getId() != 0 && this.art.getNom() != null)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&nom=" + this.art.getNom();
					if(this.art.getDescription() != null)
						url = url + "&description=" + this.art.getDescription();
					if(this.art.getQte() != 0)
						url = url + "&valeur=" + this.art.getQte();
					if(this.art.getPu() != 0)
						url = url + "&pu=" + this.art.getPu();
					url = url + "&action=new";
				}					
				break;
			case DELETE:
				if(this.art.getId() != 0)
				{
					url = url + "id=" + this.art.getId();
					url = url + "&action=delete";
				}
				break;
		default:
			break;
		}
		
		Log.d("ExecURL.class", url);
		
		// Request
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try 
		{
			client.execute(httpGet);
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
	
	private void createWait()
	{
		this.prgD = new ProgressDialog(this.context);
		this.prgD.setTitle("Execution");
		this.prgD.setMessage("Receptions des informations");
		this.prgD.show();
	}
	
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
