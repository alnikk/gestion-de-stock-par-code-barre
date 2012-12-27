package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;

public class WebViewActivity extends Activity 
{
	private static final String url = "http://nuitinfo2012.iut-valence.fr/eq2/asobo/script.php?";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		Intent intent = getIntent();
	    int refArt = intent.getExtras().getInt("refArt");
	    
	    WebView webView = (WebView) findViewById(R.id.webview);
	    
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.loadUrl(url + "/" + refArt);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}
}
