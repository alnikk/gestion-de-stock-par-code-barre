package com.iutval.projetT.gestiondesstocks;

import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * It create preview surface for the camera
 * 
 * @author Alexandre Guyon
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback 
{
	/**
	 * Container of the surface
	 */
	private SurfaceHolder holder;

	/**
     * The camera instance
     */
	private Camera camera;

	@SuppressWarnings("deprecation")
	public Preview(Context context, Camera camera) 
	{
		super(context);

		this.camera = camera;

		// Install SurfaceHolder
		this.holder = getHolder();
		this.holder.setSizeFromLayout();
		this.holder.addCallback(this);

		// needed prior to 3.0
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder) 
	{
		// Draw preview
		try 
		{	
			camera.setPreviewDisplay(this.holder);
			camera.setDisplayOrientation(90);

			/*Camera.Parameters params = camera.getParameters(); 

			if (params.getMaxNumMeteringAreas() > 0)
			{
			    List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();

			    Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
			    meteringAreas.add(new Camera.Area(areaRect1, 600)); // set weight to 60%
			    Rect areaRect2 = new Rect(800, -1000, 1000, -800);  // specify an area in upper right of image
			    meteringAreas.add(new Camera.Area(areaRect2, 400)); // set weight to 40%
			    params.setMeteringAreas(meteringAreas);			  
			}

			camera.setParameters(params);*/

			camera.startPreview();
		}
		catch (IOException e) 
		{  }
	}

	public void surfaceDestroyed(SurfaceHolder holder) 
	{}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) 
	{
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (holder.getSurface() == null)
		{
			return;
		}

		try 
		{
			camera.stopPreview();
		} 
		catch (Exception e)
		{    }

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try 
		{
			camera.setPreviewDisplay(holder);
			camera.startPreview();

		} 
		catch (Exception e)
		{    }
	}

	
	/**
	 * Draw on preview
	 */
	@Override
    public void onDraw(Canvas canvas) {
		/*Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );*/

    }
}