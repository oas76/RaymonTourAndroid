package com.oas76.RaymonTour;


import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;


public final class CoursePickerFragment extends DialogFragment {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		ArrayList<CharSequence> list = new ArrayList<CharSequence>();
		if(((TournamentEdit)getActivity()).mSelectedPlayers == null)
			((TournamentEdit)getActivity()).mSelectedPlayers = new ArrayList<GolfPlayer>(); 
		
		for(GolfPlayer gp:SectionFragment.playerlist)
		{
			list.add(gp.getNick());
			
		}
		
		int count = list.size();
		CharSequence[] charList = list.toArray(new CharSequence[count]);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Select Course")
	      	   .setAdapter(new ArrayAdapter<GolfCourse>(getActivity(), android.R.layout.simple_list_item_1, SectionFragment.courselist), new DialogInterface.OnClickListener() {
	      		   public void onClick(DialogInterface dialog, int which) {
	      			 ((TournamentEdit)getActivity()).gCourse = SectionFragment.courselist.get(which);
	      			if(((TournamentEdit)getActivity()).gCourse != null)
	      			{
                		Toast.makeText(getActivity(),((TournamentEdit)getActivity()).gCourse.toString() + " selected",Toast.LENGTH_LONG).show();
                		((TournamentEdit)getActivity()).bcourse = true;
	      			}
               
	      			   
	      		   }
	      			   
	      		});
	 
	    return builder.create();
       


        
    }
	


}