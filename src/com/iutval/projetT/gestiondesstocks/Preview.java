package com.iutval.projetT.gestiondesstocks;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class Preview extends SurfaceView implements SurfaceHolder.Callback 
{
	
    private SurfaceHolder holder;
    
    private Camera camera;
    
    @SuppressWarnings("deprecation")
	public Preview(Context context, Camera camera) 
    {
        super(context);
        
        this.camera = camera;

        // Install a SurfaceHolder. Callback so we get notified when the
        // underlying surface is created and destroyed.
        this.holder = getHolder();
        this.holder.setSizeFromLayout();
        this.holder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);   
    }

    public void surfaceCreated(SurfaceHolder holder) 
    {
        // The Surface has been created, now tell the camera where to draw the preview.
        try 
        {	
        	camera.setPreviewDisplay(this.holder);
        	camera.startPreview();
        }
        catch (IOException e) 
        {
            //Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        	Log.d("ASSERT", "Error setting camera preview: " + e.getMessage());
        	Log.wtf("CAMERA", "Err : " + e.getStackTrace() + "\n\n\n\n" + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) 
    {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) 
    {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (holder.getSurface() == null)
        {
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try 
        {
            camera.stopPreview();
        } 
        catch (Exception e)
        {
          // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try 
        {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } 
        catch (Exception e)
        {
            //Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}