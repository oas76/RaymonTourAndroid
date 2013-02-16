package com.oas76.RaymonTour;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;


public class TextFragment extends DialogFragment {
	String displayText = "";
	int state = 10;
	
	public void setDisplayText(String str)
	{
		displayText = str;
	}
	
	public void setState(int state)
	{
		this.state = state;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setMessage(displayText)
	    	   .setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if(state == TournamentEdit.STATE_INTRO)
					{
						((TournamentEdit)getActivity()).hookupAddPlayer();
						((TournamentEdit)getActivity()).CURR_STATE = TournamentEdit.STATE_PLAYERS;
					}
				}
			});
	      	   
	 
	    return builder.create();
       


        
    }
	


}
