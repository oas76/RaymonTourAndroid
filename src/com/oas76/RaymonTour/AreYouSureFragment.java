package com.oas76.RaymonTour;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;



public class AreYouSureFragment extends DialogFragment {

	String displayText = "";
	int state = 10;
	Context context = null;
	String classname = "";
	int index;
	
	public void setDisplayText(String str)
	{
		displayText = str;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
		Class myclass = context.getClass();
		this.classname = myclass.getSimpleName();
	}
	
	public void setDbIndex(int index)
	{
		this.index = index;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Are You Sure")
	  	   	   .setMessage(displayText)
	    	   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	if(classname.equals("PlayerEdit"))
	    	                		((RaymonTour)context.getApplicationContext()).deletePlayer(index);
	    	                	else if(classname.equals("TourEdit"))
	    	                		((RaymonTour)context.getApplicationContext()).deleteTour(index);
	    	                	else if(classname.equals("ScoreEdit"))
	    	                		((RaymonTour)context.getApplicationContext()).deleteTournament(index);
	    	                	
	    	                	getActivity().finish();
	    	                	
	    	                	ListFragment fragment = new SectionFragment();
	    	                    Bundle args = new Bundle();
	    	                    args.putInt(SectionFragment.ARG_SECTION_NUMBER,1);
	    	                    fragment.setArguments(args); 	                    
	    	                    getFragmentManager().beginTransaction()
	    	                            .replace(R.id.container, fragment)
	    	                            .commit();
	    	                	
	    	                }
	    	                	
	    	            })
	    	   .setNegativeButton("Add New Player", new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                    
	    	                }
	    	            });



	    return builder.create();
	    
	}
 
}