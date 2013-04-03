package com.iutval.projetT.gestiondesstocks;

import java.io.IOException;

import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;


/**
 * It detects camera, and launch it for identifying barCode.
 * @author Alexandre Guyon
 */
public class Photo extends Activity implements Camera.PictureCallback, SurfaceHolder.Callback
{
	private Camera camera;
	
	private SurfaceView mSurface;
	
	private SurfaceHolder mHolder;
	
	private int refArt = 0;
	
	//***************** State ****************************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		this.camera = Camera.open();
		
		//Preview
		mSurface = (SurfaceView) findViewById(R.id.surfaceViewCamera);
		mHolder = mSurface.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		try {
			camera.setPreviewDisplay(mHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		camera.setDisplayOrientation(90);
		
		Camera.Parameters param = camera.getParameters();
		param.setSceneMode(Parameters.SCENE_MODE_BARCODE);
		param.setFocusMode(Parameters.FOCUS_MODE_AUTO);
		camera.setParameters(param);
		
		camera.startPreview();
		
		mSurface.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				if (camera != null) 
				{
					camera.autoFocus(new AutoFocusCallback() 
					{
					    @Override
					    public void onAutoFocus(boolean success, Camera camera)
					    {
					    	camera.takePicture(null, null, Photo.this);
					    }
					});
				}
			}
		});
		
		Toast.makeText(getApplicationContext(), R.string.toastPhoto, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onStop() 
	{
		camera.release();
		camera = null;
		super.onStop();
	}
	
	@Override
	protected void onRestart() 
	{
		super.onRestart();
		if(camera == null)
		{
			camera = Camera.open();
			
			camera.setDisplayOrientation(90);
			
			Camera.Parameters param = camera.getParameters();
			param.setSceneMode(Parameters.SCENE_MODE_BARCODE);
			param.setFocusMode(Parameters.FOCUS_MODE_AUTO);
			camera.setParameters(param);
			
			camera.startPreview();
			
			camera.startPreview();
		}
	}
	
	@Override
	protected void onResume() 
	{	
		super.onResume();
		if(camera == null)
		{
			camera = Camera.open();
			
			camera.setDisplayOrientation(90);
			
			Camera.Parameters param = camera.getParameters();
			param.setSceneMode(Parameters.SCENE_MODE_BARCODE);
			param.setFocusMode(Parameters.FOCUS_MODE_AUTO);
			camera.setParameters(param);
			
			camera.startPreview();
			
			camera.startPreview();
		}
	}


	//********************** SurfaceHolder.callback *******************
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		camera.stopPreview();
		Camera.Parameters parameters = camera.getParameters();
		parameters.setPreviewSize(width, height);
		camera.setParameters(parameters);
		try 
		{
			camera.setPreviewDisplay(mSurface.getHolder());
		} catch (IOException e) {}
		camera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		if (camera == null)
			camera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		if (camera != null) 
		{
			camera.stopPreview();
			camera.release();
		}
	}

	//********************** Camera.pictureCallback *******************
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) 
	{
		// Conversion de l'image JPEG en Bitmap
		/*Bitmap photo = BitmapFactory.decodeByteArray(data, 0, data.length);
		CbitMap d = new CbitMap(photo);
		d.start();
		try {
			d.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.refArt = d.getRes();*/
		this.refArt = (int)(Math.random()*10);
		camera.startPreview();
		//Toast.makeText(getApplicationContext(), R.string.toastIdDecode + refArt, Toast.LENGTH_SHORT).show();
	}
	
	
	//********************** Button *******************
	
	public void capture(View view)
	{
		if(refArt != 0)
		{
			Intent intent = new Intent(getApplicationContext(), ChoixAction.class);
			intent.putExtra("refArt", refArt);
			startActivity(intent);
		}
		else
		{
			Toast.makeText(getApplicationContext(), R.string.toastPhoto, Toast.LENGTH_LONG).show();
		}
	}

}