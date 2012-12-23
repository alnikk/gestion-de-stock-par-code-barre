package com.iutval.projetT.gestiondesstocks.test;

import com.iutval.projetT.gestiondesstocks.R;
import com.iutval.projetT.gestiondesstocks.R.layout;

import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

public class TestCameraSimple extends Activity 
{

	Bitmap mImageBitmap;
	
	ImageView mImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu); // TODO Do boot screen
		this.dispatchTakePictureIntent(BIND_AUTO_CREATE);
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		this.handleSmallCameraPhoto(data);
	}
	
	private void dispatchTakePictureIntent(int actionCode) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(takePictureIntent, actionCode);
	}
	
	private void handleSmallCameraPhoto(Intent intent) {
	    Bundle extras = intent.getExtras();
	    Bitmap mImageBitmap = (Bitmap) extras.get("data");
	    mImageView.setImageBitmap(mImageBitmap);
	}
}
