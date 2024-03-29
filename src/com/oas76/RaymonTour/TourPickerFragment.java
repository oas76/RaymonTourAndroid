package com.oas76.RaymonTour;


import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;


public final class TourPickerFragment extends DialogFragment {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		ArrayList<CharSequence> list = new ArrayList<CharSequence>();
		if(((TournamentEdit)getActivity()).mSelectedTour == null)
			((TournamentEdit)getActivity()).mSelectedTour = new ArrayList<Tour>(); 
		
		for(Tour tp: (((RaymonTour)getActivity().getApplication()).getTourlist()))
		{
			list.add(tp.getTourName());
			
		}
		
		int count = list.size();
		CharSequence[] charList = list.toArray(new CharSequence[count]);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Select Tour")
	    	   .setMultiChoiceItems(charList,((TournamentEdit)getActivity()).tboolList,
                      new DialogInterface.OnMultiChoiceClickListener() {
	    		   		@Override
	    		   		public void onClick(DialogInterface dialog, int which,
	    		   				boolean isChecked) {
	    		   			if (isChecked) {
	    		   				// If the user checked the item, add it to the selected items
	    		   				((TournamentEdit)getActivity()).mSelectedTour.add((((RaymonTour)getActivity().getApplication()).getTourlist()).get(which));
	    		   				((TournamentEdit)getActivity()).tboolList[which] = true;
	    		   			} else if (((TournamentEdit)getActivity()).mSelectedPlayers.contains(which)) {
	    		   				// Else, if the item is already in the array, remove it 
	    		   				((TournamentEdit)getActivity()).mSelectedTour.remove((((RaymonTour)getActivity().getApplication()).getTourlist()).get(which));
	    		   				((TournamentEdit)getActivity()).tboolList[which] = false;
	    		   				}
	    		   			}
	    	   			})
	    	   			// Set the action buttons
	    	            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	String str = "Part of tours: ";
	    	                	for(int i = 0; i < ((TournamentEdit)getActivity()).tboolList.length; i++)
	    	                	{
	    	                		if(((TournamentEdit)getActivity()).tboolList[i])
	    	                			str = str + (((RaymonTour)getActivity().getApplication()).getTourlist()).get(i).toString();
	    	                		
	    	                	}
	    	                	
	    	                	if(((TournamentEdit)getActivity()).mSelectedTour.size() > 0)
	    	                	{
	    	                		((TournamentEdit)getActivity()).btour = true;
	    	                		Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
	    	                		dialog.dismiss();
	    	                		if(TournamentEdit.CURR_STATE == TournamentEdit.STATE_TOUR)
	    	                		{
	    	                			((TournamentEdit)getActivity()).hookupDateButton();
	    	                			TournamentEdit.CURR_STATE = TournamentEdit.STATE_DATE;
	    	                		}
	    	                	}
	    	                	else
	    	                	{
	    	                		Toast.makeText(getDialog().getContext(),"At least one tour must be selected...",Toast.LENGTH_LONG).show();
	    	                		dialog.dismiss();
	    	                		if(TournamentEdit.CURR_STATE == TournamentEdit.STATE_TOUR)
	    	                		{
	    	                			((TournamentEdit)getActivity()).hookupTour();
	    	                			TournamentEdit.CURR_STATE = TournamentEdit.STATE_TOUR;
	    	                		}
	    	                	}

	    	                }
	    	            })
	    	            .setNegativeButton("Add New Tour", new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	((TournamentEdit)getActivity()).mSelectedTour.clear();
	    	                	for(int i = 0; i < ((TournamentEdit)getActivity()).tboolList.length; i++)
	    	                	{
	    	                		((TournamentEdit)getActivity()).tboolList[i] = false;
	    	                	}
	    	                	((TournamentEdit)getActivity()).mSelectedPlayers.clear();
	    	                	for(int i = 0; i < ((TournamentEdit)getActivity()).boolList.length; i++)
	    	                	{
	    	                		((TournamentEdit)getActivity()).boolList[i] = false;
	    	                	}
	    	                	PlayerFragment fragment = new PlayerFragment();
	    	                    getFragmentManager().beginTransaction()
	    	                            .replace(R.id.player_container, fragment)
	    	                            .commit();
	    	                    ((TournamentEdit)getActivity()).CURR_STATE = ((TournamentEdit)getActivity()).STATE_INTRO;
	    	                	startActivity(new Intent(((TournamentEdit)getActivity()),TourEdit.class));
	    	                	dialog.dismiss();
	    	                    
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