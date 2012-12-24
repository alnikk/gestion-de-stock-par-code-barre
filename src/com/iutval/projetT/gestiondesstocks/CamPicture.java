package com.iutval.projetT.gestiondesstocks;

import android.hardware.Camera;

public class CamPicture implements Camera.PictureCallback
{
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
