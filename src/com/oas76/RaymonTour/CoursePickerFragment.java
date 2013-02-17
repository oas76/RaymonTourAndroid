package com.oas76.RaymonTour;


import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;


public final class CoursePickerFragment extends DialogFragment {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		ArrayList<CharSequence> list = new ArrayList<CharSequence>();
		if(((TournamentEdit)getActivity()).mSelectedPlayers == null)
			((TournamentEdit)getActivity()).mSelectedPlayers = new ArrayList<GolfPlayer>(); 
		
		for(GolfPlayer gp: ((RaymonTour)getActivity().getApplication()).getPlayerlist())
		{
			list.add(gp.getNick());
			
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Select Course")
	      	   .setAdapter(new ArrayAdapter<GolfCourse>(getActivity(), android.R.layout.simple_list_item_1, ((RaymonTour)getActivity().getApplication()).getCourselist()), new DialogInterface.OnClickListener() {
	      		   public void onClick(DialogInterface dialog, int which) {
	      			 ((TournamentEdit)getActivity()).gCourse = ((RaymonTour)getActivity().getApplication()).getCourselist().get(which);
	      			if(((TournamentEdit)getActivity()).gCourse != null)
	      			{
                		Toast.makeText(getActivity(),((TournamentEdit)getActivity()).gCourse.toString() + " selected",Toast.LENGTH_LONG).show();
                		((TournamentEdit)getActivity()).bcourse = true;
                		dialog.dismiss();
                		if(TournamentEdit.CURR_STATE == TournamentEdit.STATE_COURSE)
	                	{
							((TournamentEdit)getActivity()).hookupTour();
							TournamentEdit.CURR_STATE = TournamentEdit.STATE_TOUR;
						}
	      			}
               
	      			   
	      		   }
	      			   
	      		})
	      		.setOnKeyListener(new OnKeyListener() {
					
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
	      		
	 
	    return builder.create();
       


        
    }
	


}