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


public final class PlayerPickerFragment extends DialogFragment {

	
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
	    builder.setTitle("Select Player")
	    	   .setMultiChoiceItems(charList,((TournamentEdit)getActivity()).boolList,
                      new DialogInterface.OnMultiChoiceClickListener() {
	    		   		@Override
	    		   		public void onClick(DialogInterface dialog, int which,
	    		   				boolean isChecked) {
	    		   			if (isChecked) {
	    		   				// If the user checked the item, add it to the selected items
	    		   				((TournamentEdit)getActivity()).mSelectedPlayers.add(SectionFragment.playerlist.get(which));
	    		   				((TournamentEdit)getActivity()).boolList[which] = true;
	    		   			} else if (((TournamentEdit)getActivity()).mSelectedPlayers.contains(which)) {
	    		   				// Else, if the item is already in the array, remove it 
	    		   				((TournamentEdit)getActivity()).mSelectedPlayers.remove(SectionFragment.playerlist.get(which));
	    		   				((TournamentEdit)getActivity()).boolList[which] = false;
	    		   				}
	    		   			}
	    	   			})
	    	   			// Set the action buttons
	    	            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	PlayerFragment fragment = new PlayerFragment();
	    	                	fragment.setPlayerList(((TournamentEdit)getActivity()).mSelectedPlayers);
	    	                    getFragmentManager().beginTransaction()
	    	                            .replace(R.id.player_container, fragment)
	    	                            .commit();
	    	                	
	    	                	
	    	                	dialog.dismiss();
	    	                }
	    	            })
	    	            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	((TournamentEdit)getActivity()).mSelectedPlayers.clear();
	    	                	dialog.dismiss();
	    	                    
	    	                }
	    	            });
	          
	    //.setAdapter(new ArrayAdapter<GolfPlayer>(getActivity(), android.R.layout.simple_list_item_1, SectionFragment.playerlist), new DialogInterface.OnClickListener() {
	    //    public void onClick(DialogInterface dialog, int which) {
	               // The 'which' argument contains the index position
	               // of the selected item
	           //}
	 
	    return builder.create();
       


        
    }
	


}