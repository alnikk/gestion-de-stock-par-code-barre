package com.iutval.projetT.gestiondesstocks.test;

import com.iutval.projetT.gestiondesstocks.Preview;
import com.iutval.projetT.gestiondesstocks.R;
import com.iutval.projetT.gestiondesstocks.R.layout;
import com.iutval.projetT.gestiondesstocks.R.string;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;

public class TestCam extends Activity 
{

	public Camera mCamera;
	
	public Preview mPreview; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu); // TODO Do boot screen
		this.safeCameraOpen();
		
	}
	
	private boolean safeCameraOpen() 
	{	  
	    try 
	    {
	        releaseCameraAndPreview();
	        mCamera = Camera.open();
	    } catch (Exception e) 
	    {
	        Log.e(getString(R.string.app_name), "failed to open Camera");
	        e.printStackTrace();
	    }

	    return (mCamera != null);  
	}

	private void releaseCameraAndPreview() 
	{
	    //mPreview.setCamera(null);
	    if (mCamera != null)
	    {
	        mCamera.release();
	        mCamera = null;
	    }
	}
}
