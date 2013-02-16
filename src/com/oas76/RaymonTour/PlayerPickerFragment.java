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
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;


public final class PlayerPickerFragment extends DialogFragment {

	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		ArrayList<CharSequence> list = new ArrayList<CharSequence>();
		if(((TournamentEdit)getActivity()).mSelectedPlayers == null)
			((TournamentEdit)getActivity()).mSelectedPlayers = new ArrayList<GolfPlayer>(); 
		
		for(GolfPlayer gp : ((RaymonTour)getActivity().getApplication()).getPlayerlist())
		{
			list.add(gp.getNick());
			
		}
		
		int count = list.size();
		CharSequence[] charList = list.toArray(new CharSequence[count]);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Select Player")
	    	   .setMultiChoiceItems(charList,((TournamentEdit)getActivity()).boolList,
                      new DialogInterface.OnMultiChoiceClickListener() {
	    		   		@Override
	    		   		public void onClick(DialogInterface dialog, int which,
	    		   				boolean isChecked) {
	    		   			if (isChecked) {
	    		   				// If the user checked the item, add it to the selected items
	    		   				ArrayList<GolfPlayer> playerlist = (((RaymonTour)getActivity().getApplication()).getPlayerlist());
	    		   				((TournamentEdit)getActivity()).mSelectedPlayers.add( playerlist.get(which) );
	    		   				((TournamentEdit)getActivity()).boolList[which] = true;
	    		   			} else if (((TournamentEdit)getActivity()).mSelectedPlayers.contains(which)) {
	    		   				// Else, if the item is already in the array, remove it 
	    		   				((TournamentEdit)getActivity()).mSelectedPlayers.remove(((RaymonTour)getActivity().getApplication()).getPlayerlist().get(which));
	    		   				((TournamentEdit)getActivity()).boolList[which] = false;
	    		   				}
	    		   			}
	    	   			})
	    	   			// Set the action buttons
	    	            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	PlayerFragment fragment = new PlayerFragment();
	    	                    getFragmentManager().beginTransaction()
	    	                            .replace(R.id.player_container, fragment)
	    	                            .commit();
	    	                    
	    	                    if(((TournamentEdit)getActivity()).mSelectedPlayers.size() > 0 )
	    	                    	((TournamentEdit)getActivity()).bplayer = true;
	    	                    	
	    	                	
	    	                	dialog.dismiss();
	    	                }
	    	            })
	    	            .setNegativeButton("Add New Player", new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	((TournamentEdit)getActivity()).mSelectedPlayers.clear();
	    	                	startActivity(new Intent(((TournamentEdit)getActivity()),PlayerEdit.class));
	    	                	dialog.dismiss();
	    	                    
	    	                }
	    	            });

	    return builder.create();
       


        
    }
	


}