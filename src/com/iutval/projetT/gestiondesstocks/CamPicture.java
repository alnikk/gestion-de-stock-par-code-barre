package com.iutval.projetT.gestiondesstocks;

import android.hardware.Camera;

/**
 * This class used to handle pictures took from camera
 * @author alexandre
 */
public class CamPicture implements Camera.PictureCallback
{
	/**
	 * The picture data
	 */
	private byte[] data;
	
	@Override
    public void onPictureTaken(byte[] data, Camera camera)
	{
		this.data = data;
	}
	
	public byte[] getData()
	{
		return data;
	}
}
