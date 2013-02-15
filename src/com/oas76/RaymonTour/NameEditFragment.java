package com.oas76.RaymonTour;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public final class NameEditFragment extends DialogFragment {
	View view = null;
	EditText text = null;


	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		view = getActivity().getLayoutInflater().inflate(R.layout.dialog_name, null);
		text = (EditText) view.findViewById(R.id.tname);
		text.setText("Dette er en test");


		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Edit Tournament Name")
	    	   .setView(view)
	    	   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	 
	    	                	((TournamentEdit)getActivity()).name = text.getText().toString();
	    	                	((TournamentEdit)getActivity()).bname = true;
	    	                	dialog.dismiss();
	    	                }
	    	            });
	    	           
	          

	 
	    return builder.create();
       


        
    }
	


}