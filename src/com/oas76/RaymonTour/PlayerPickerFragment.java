package com.oas76.RaymonTour;


import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;


public final class PlayerPickerFragment extends DialogFragment {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		ArrayList<String> list = new ArrayList<String>();
		for(GolfPlayer gp:SectionFragment.playerlist)
		{
			list.add(gp.getNick());
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Select Player")
	           .setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list), new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               // The 'which' argument contains the index position
	               // of the selected item
	           }
	    });
	    return builder.create();
       


        
    }
	


}