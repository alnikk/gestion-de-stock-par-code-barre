package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;

public class WebViewActivity extends Activity 
{
	private static final String url = "www.facebook.com";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		Intent intent = getIntent();
	    int refArt = intent.getExtras().getInt("refArt");
	    
	    WebView webview = new WebView(this);
	    setContentView(webview);
	    
	    //WebView mWebView = (WebView) findViewById(R.id.webView1);
	    webview.loadUrl(url + "/" + refArt);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		return true;
	}
}
