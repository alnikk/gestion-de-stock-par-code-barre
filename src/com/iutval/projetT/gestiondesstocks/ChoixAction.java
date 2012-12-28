package com.iutval.projetT.gestiondesstocks;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ChoixAction extends Activity 
{
	//**************** Variable *********************

	private int refArt;	

	//**************** State *********************

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_action);

		Intent intent = getIntent();
		this.refArt = intent.getExtras().getInt("refArt");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_choix_action, menu);
		return true;
	}

	//**************** Button ********************

	public void validate(View view)
	{
		Action act;

		TextView text = (TextView) findViewById(R.id.value);

		String qte = text.getText().toString();

		RadioButton radiobt = (RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioChoix)).getCheckedRadioButtonId());

		if(radiobt.getText() == findViewById(R.id.add).getContentDescription())
			act = Action.ADD;
		else
			act = Action.SEE;

		Intent intent = new Intent(this, WebViewActivity.class);
		intent.putExtra("refArt", this.refArt);
		intent.putExtra("act", act.toString().toLowerCase());
		intent.putExtra("qte", qte);
		startActivity(intent);
	}
}
