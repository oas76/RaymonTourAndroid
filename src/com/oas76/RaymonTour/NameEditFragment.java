package com.oas76.RaymonTour;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public final class NameEditFragment extends DialogFragment {
	View view = null;
	EditText text = null;
	String def_str = null;
	
	public void setDefaultName(String str)
	{
		this.def_str = str;
	}
		
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		view = getActivity().getLayoutInflater().inflate(R.layout.dialog_name, null);
		text = (EditText) view.findViewById(R.id.tname);
		text.setText(def_str);


		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Edit Tournament Name")
	    	   .setView(view)
	    	   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	 
	    	                	((TournamentEdit)getActivity()).name = text.getText().toString();
	    	                	((TournamentEdit)getActivity()).bname = true;
	    	                	Toast.makeText(view.getContext(),((TournamentEdit)getActivity()).name,Toast.LENGTH_LONG).show();
	    	                	dialog.dismiss();
	    	              		if(TournamentEdit.CURR_STATE == TournamentEdit.STATE_NAME)
	    	                	{
	    	              			TournamentEdit.CURR_STATE = TournamentEdit.STATE_NAME;
	    	              			((TournamentEdit)getActivity()).bFinished = true;
	    	              			((TournamentEdit)getActivity()).invalidateOptionsMenu();
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