package com.iutval.projetT.gestiondesstocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class OverlayView extends SurfaceView 
{
	private SurfaceHolder mOverSH;

	private Camera mCam;
	private Camera.Size mFrameSize;

	public OverlayView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);

		mOverSH = getHolder();
	}

	public void setPreviewSize(Size s) 
	{
		mFrameSize = s;
		//mFrameCount = 0;
	}

	// Called by Sobel.initCamera, to set callback
	public void setCamera(Camera c)
	{
		mCam = c;
		mCam.setPreviewCallback(new PreviewCallback()
		{
			private int mFrameCount;

			// Called by camera hardware, with preview frame
			public void onPreviewFrame(byte[] frame, Camera c)
			{
				Canvas cOver = mOverSH.lockCanvas(null);
				try
				{
					// Perform overlay rendering here
					// Here, draw an incrementing number onscreen
					Paint pt = new Paint();
					pt.setColor(Color.WHITE);
					pt.setTextSize(16);
					cOver.drawText(Integer.toString(mFrameCount++),
							10, 10, pt);
				}
				catch(Exception e)
				{
					// Log/trap rendering errors
				}
				finally
				{
					mOverSH.unlockCanvasAndPost(cOver);
				}
			}
		});	
	}}
