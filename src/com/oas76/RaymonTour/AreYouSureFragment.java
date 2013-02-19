package com.oas76.RaymonTour;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;



public class AreYouSureFragment extends DialogFragment {

	String displayText = "";
	int state = 10;
	Context context = null;
	String classname = "";
	int index;
	int return_mode = 0;
	boolean setOfficial = false;
	
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
	
	public void setOfficialData(boolean official)
	{
		this.setOfficial = official;
	}
	
	public void setOfficialDataNow(int id)
	{
		((RaymonTour)context.getApplicationContext()).updatePlayerWinnings(id);
		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(TourContentProvider.KEY_TOURNAMENT_OFFICIAL, 1);
		cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TOURNAMENTS, String.valueOf(id)), 
				  values,
				  null,
				  null);
		
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Are You Sure")
	  	   	   .setMessage(displayText)
	    	   .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                	
	    	                	if(setOfficial)
	    	                	{
	    	                		setOfficialDataNow(index);
	    	                	}
	    	                	else if(classname.equals("PlayerEdit"))
	    	                	{
	    	                		((RaymonTour)context.getApplicationContext()).deletePlayer(index);
	    	                		return_mode = MainActivity.SELECT_PLAYER;
	    	                  	}
	    	                	else if(classname.equals("TourEdit"))
	    	                	{
	    	                		((RaymonTour)context.getApplicationContext()).deleteTour(index);
	    	                		return_mode = MainActivity.SELECT_TOUR;
	    	                	}
	    	                	else if(classname.equals("ScoreEdit"))
	    	                	{
	    	                		((RaymonTour)context.getApplicationContext()).deleteTournament(index);
	    	                		return_mode = MainActivity.SELECT_TOURNAMENT;
	    	                	}
	    	                	
	    	                	// Send intent to MainActivity for re-draw
	    	                	if(!setOfficial)
	    	                	{
	    	                		Intent intent = new Intent(getActivity(), MainActivity.class);
	    	                		intent.putExtra("ReturnMode", return_mode);
	    	                		getActivity().startActivity(intent);
	    	                	}
	    	                	
	    	                }
	    	                	
	    	            })
	    	   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    	                @Override
	    	                public void onClick(DialogInterface dialog, int id) {
	    	                    
	    	                }
	    	            });



	    return builder.create();
	    
	}
 
}