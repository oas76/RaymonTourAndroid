package com.oas76.RaymonTour;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.DatePicker;


public final class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),this, year, month, day);
        dialog.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK)
				{
					dialog.dismiss();
					((TournamentEdit)getActivity()).onBackPressed();
					
				}
				return false;
			}

        });
        return dialog;
    }
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
	        ((TournamentEdit)getActivity()).setDate(year,month,day);
	        
	}

}