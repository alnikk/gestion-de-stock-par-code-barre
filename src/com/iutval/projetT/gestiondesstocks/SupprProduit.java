package com.iutval.projetT.gestiondesstocks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SupprProduit extends DialogFragment 
{
	private int ref;
	
	public SupprProduit(int ref)
	{
		this.ref = ref;
	}
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) 
    {
    	DialogInterface.OnClickListener listenner = new DialogInterface.OnClickListener()
        {
            private int ref = 0;

            public void onClick(DialogInterface dialog, int id) 
            {
         	   if(this.ref != 0)
         	   {
         		   Article articleQte = new Article();
        				articleQte.setId(this.ref);
        				articleQte.setAct(Action.DELETE);
        				
        				ExecURL add = new ExecURL();
        				add.sendArt(articleQte);
        				add.start();
         	   }
            }
            
            public void setRef(int ref)
            {
         	   this.ref = ref;
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Etes vous sur de supprimer cette article ?")
               .setPositiveButton("Oui", listenner)
               .setNegativeButton("Non", new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id)
                   {
                       dismiss();
                   }
               });
        return builder.create();
    }
}


