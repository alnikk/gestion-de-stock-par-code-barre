package com.iutval.projetT.gestiondesstocks;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;


/**
 * This class is the home class of the application.
 * It detects camera, and launch it for identifying barCode.
 * @author Alexandre Guyon
 */
public class Menu extends Activity 
{
	//******************** Constant ********************

	private final static int LENGHT_TOAST = 5;


	//******************** Variable ********************

	/**
	 * The instance of camera to use
	 */
	private Camera camera = null;

	/**
	 * The preview for drawing rectangle on it, and so take barCode in this area
	 */
	private Preview preview = null;	

	//******************** State ********************

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		// Boot Screen
		setContentView(R.layout.activity_menu); // TODO Do boot screen
	}

	@Override
	protected void onResume() 
	{
		super.onResume();

		// Test if there's camera on the device
		if(this.checkCameraHardware(this.getApplicationContext()))
		{
			this.createCameraInstance();
			this.addPreview();
		}
		else
		{
			Toast.makeText(getApplicationContext(),
					"Your device doesn't support camera",
					LENGHT_TOAST).show();
		}
	}
	//******************** Method ********************

	/**
	 * Check if the device have camera.
	 * @param context 
	 * @return Return true if device have camera, else otherwise. 
	 */
	private boolean checkCameraHardware(Context context) 
	{
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
			return true; 
		return false;
	}	

	/** 
	 * A safe way to get an instance of the Camera object. 
	 */
	private void createCameraInstance()
	{
		try 
		{
			this.camera = Camera.open();
		}
		catch (Exception e)
		{
			// Camera is not available (in use or does not exist)
		}
	}

	private void addPreview()
	{			
		this.preview = new Preview(this, this.camera);

		FrameLayout view = (FrameLayout) findViewById(R.id.camera_preview);
		/////////////////// BUG !//////////////
		view.addView(this.preview);
		///////////////////
	}
}
