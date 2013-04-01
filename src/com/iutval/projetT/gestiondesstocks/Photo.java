package com.iutval.projetT.gestiondesstocks;

import com.iutval.projetT.gestiondesstocks.in.Bitmap;
import com.iutval.projetT.gestiondesstocks.in.CbitMap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;


/**
 * It detects camera, and launch it for identifying barCode.
 * @author Alexandre Guyon
 */
public class Photo extends Activity 
{
	//******************** Variable ********************

	/**
	 * The instance of camera to use
	 */
	private Camera camera = null;

	/**
	 * The preview for drawing rectangle on it, and so take barCode in this area
	 */
	private Preview preview = null;	
	
	/**
	 * The pictures took with camera
	 */
	private CamPicture pic = null;

	//******************** State ********************

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// Initialization
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo); // TODO Do boot screen
		
		this.pic = new CamPicture();
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
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
		// FIXME No need to close camera ???
		//camera.stopPreview();
		//camera.release();
	}

	//*********************** Button *********************

	/**
	 * This method is called by pushing button on the layout.
	 * @param view
	 */
	public void capture(View view)
	{
		// Variable
		byte[] photo;
		int refArt = 0;

		
		camera.autoFocus(new AutoFocusCallback() 
		{
		    @Override
		    public void onAutoFocus(boolean success, Camera camera)
		    {
		    	camera.takePicture(null, null, pic);
		    }
		});
		
		
		// Took photo
		
		photo = this.pic.getData();

		
		if(photo == null)
			Log.d("Photo.class", "coucou");
		// Send it to algorithm 
		Bitmap bitmap = new Bitmap(photo);
		CbitMap d = new CbitMap(bitmap);
		refArt = Integer.parseInt(d.decodage().toString());
		//refArt = (int) (Math.random() * 10);
		Toast.makeText(getApplicationContext(), "id = " + refArt, Toast.LENGTH_SHORT).show(); // TODO Debug

		// Gives argument
		Intent intent = new Intent(this, ChoixAction.class);
		intent.putExtra("refArt", refArt);
		startActivity(intent);
	}

	//******************** Method ********************

	/**
	 * Check if the device have camera.
	 * @param context 
	 * @return Return true if device have camera, else otherwise. 
	 */
	private boolean checkCameraHardware(Context context) 
	{ 
		return (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
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
		{}
	}

	/**
	 * Allow to create camera's preview on the frame layout surface
	 */
	private void addPreview()
	{			
		this.preview = new Preview(this, this.camera);

		FrameLayout view = (FrameLayout) findViewById(R.id.camera_preview);
		view.addView(this.preview);
	}
}