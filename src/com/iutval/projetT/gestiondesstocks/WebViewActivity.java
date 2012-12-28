package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewActivity extends Activity 
{
	//private static final String url = "http://alexgus.no-ip.info/android/script.php";
	private static final String url = "http://192.168.1.20/android/script.php?";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		Intent intent = getIntent();
	    int refArt = intent.getExtras().getInt("refArt");
	    String act = intent.getExtras().getString("act");
	    int qte = intent.getExtras().getInt("qte");
	    
	    WebView webView = (WebView) findViewById(R.id.webview);
	    
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.loadUrl(url + "id=" + 1 + "&action=" + act + "&qte=" + qte);
	    Toast.makeText(getApplicationContext(), url + "id=" + 1 + "&action=" + act + "&qte=" + qte, 1000).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}
}
