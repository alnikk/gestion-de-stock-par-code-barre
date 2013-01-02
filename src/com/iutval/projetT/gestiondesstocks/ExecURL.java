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
	 * Reference (id, in database) of the item 
	 */
	private int refArt = 0;
	
	/**
	 * The amount of item to set
	 */
	private int qte = 0;
	
	/**
	 * The name of the item
	 */
	private String nom = null;
	
	/**
	 * the descriptions of the item
	 */
	private String desc = null;
	
	private Action act;
	
	protected Context context = null;
	
	protected ProgressDialog prgD = null;
	
	//********************** Constructors ****************
	
	public ExecURL(Action a)
	{	
		this.act = a;
	}
	
	public ExecURL(Context c, Action a)
	{
		this.act = a;
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
		
		switch(this.act)
		{
			case ADD:
				if(this.refArt != 0 && this.qte != 0)
				{
					url = url + "id=" + this.refArt;
					url = url + "&=valeur" + this.qte;
					url = url + "&action=add";
				}
				break;
			case REMOVAL:
				if(this.refArt != 0 && this.qte != 0)
				{
					url = url + "id=" + this.refArt;
					url = url + "&=valeur" + this.qte;
					url = url + "&action=removal";
				}
				break;
			case NEW:
				if(this.refArt != 0 && this.nom != null)
				{
					url = url + "id=" + this.refArt;
					url = url + "&nom=" + this.nom;
					if(this.desc != null)
						url = url + "&description=" + this.desc;
					if(this.qte != 0)
						url = url + "&=valeur" + this.qte;
					url = url + "&action=new";
				}					
				break;
			case DELETE:
				if(this.refArt != 0)
				{
					url = url + "id=" + this.refArt;
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
		this.prgD.setTitle("Search");
		this.prgD.setMessage("Receptions des informations");
		this.prgD.show();
	}
	
	private void stopWait()
	{
		if(this.prgD != null)
			this.prgD.dismiss();
	}
	
	//********************** Getters & Setters *******************

	public int getRefArt() 
	{
		return refArt;
	}

	public void setRefArt(int refArt) 
	{
		this.refArt = refArt;
	}

	public int getQte() 
	{
		return qte;
	}

	public void setQte(int qte) 
	{
		this.qte = qte;
	}

	public String getNom() 
	{
		return nom;
	}

	public void setNom(String nom) 
	{
		this.nom = nom;
	}

	public String getDesc() 
	{
		return desc;
	}

	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
}
