package com.oas76.RaymonTour;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public final class SectionFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	
    public static final String ARG_SECTION_NUMBER = "section_number";
    private static final int TOURNAMENT_LOADER_ID = 0;
    private static final int TOUR_LOADER_ID = 1;
    private static final int PLAYER_LOADER_ID = 2;
    private static final int COURSE_LOADER_ID = 3;
    
    private static String EDIT_ACTIVITY = "Tournamnet";
    private static Activity myActivity = null;
    
	ArrayList<GolfPlayer> playerlist = new ArrayList<GolfPlayer>();
	ArrayAdapter<GolfPlayer>ap = null;
	ListView listView = null;

    
    @Override
    public void onAttach(Activity act)
    {
    	super.onAttach(act);
   		myActivity = act;
    }
    
    
    @Override
    public void onListItemClick(ListView l, View view, int pos, long id)
    {
    	Intent intent = null;
    	
    	switch(getArguments().getInt(ARG_SECTION_NUMBER))
    	{
    		case 1:
    			EDIT_ACTIVITY = "Tournament";
    			intent = new Intent(myActivity, TournamentEdit.class);
    			break;
    		case 2:
    			EDIT_ACTIVITY = "Tour";
    			//intent = new Intent(myActivity, TourEdit.class);
    			break;
    		case 3:
    			EDIT_ACTIVITY = "Player";
    			GolfPlayer player = (GolfPlayer)l.getItemAtPosition(pos);
    			int player_id = player.getPlayerID();
    			intent = new Intent(myActivity, PlayerView.class);
    			intent.putExtra("id", player_id);
    			break;
    		case 4:
    			EDIT_ACTIVITY = "Course";
    			//intent = new Intent(myActivity, CourseEdit.class);
    			break;
    			
    	}
    	
    	if(intent != null)
    		startActivity(intent);
    	else
    		Toast.makeText(myActivity,"Click",Toast.LENGTH_LONG).show();
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Create a new TextView and set its text to the fragment's section
        // number argument value.
    	ArrayAdapter<?> aa = null;
    	Object[] obj = null;
        listView = new ListView(myActivity);
    	
    	switch(getArguments().getInt(ARG_SECTION_NUMBER))
    	{
    		case 1:
    			EDIT_ACTIVITY = "Tournament";
    			break;
    		case 2:
    			obj = new String[] { "Tour1", "Tour2", "Tour3" };
    			EDIT_ACTIVITY = "Tour";
    			break;
    		case 3:
    			obj = new String[] { "Odd", "PŒl", "Anders" };
    			EDIT_ACTIVITY = "Player";
    			
    			break;
    		case 4:
    			EDIT_ACTIVITY = "Course";
    			break;
    		default:
    			obj = new String[] { "...Loading..." };
    	
    	}
    	
   	
    	if(EDIT_ACTIVITY.equals("Tournament"))
    	{
  			obj = new GolfTournament[] {
					new GolfTournament(1,"Oslo Open"),
					new GolfTournament(2,"Portugal dag 1"),
					new GolfTournament(3, "Gr¿nmo dag 1")
				};
			aa = new GolfTournamentAdapter (listView.getContext(),
											R.layout.listview_tournament_row, 
											(GolfTournament[])obj);
	
    	}
    	else if(EDIT_ACTIVITY.equals("Course"))
    	{
    		GolfCourse course1 = new GolfCourse(1,"Gr¿nmo");
    		GolfCourse course2 = new GolfCourse(2,"Oslo GK");
    		GolfCourse course3 = new GolfCourse(3,"Quinta Da Mahrina");
    		
    		obj = new GolfCourse[] {
    				course1,
    				course2,
    				course3
    		};
    		aa = new GolfCourseAdapter(listView.getContext(), R.layout.listview_course_row, (GolfCourse[])obj);
    		
    	}
    	else if(EDIT_ACTIVITY.equals("Player"))
    	{
    		/*
    		GolfPlayer player1 = new GolfPlayer(1, "PŒggen");
    		GolfPlayer player2 = new GolfPlayer(2, "SMU");
    		GolfPlayer player3 = new GolfPlayer(3, "Andy");
    		GolfPlayer player4 = new GolfPlayer(4, "Oddis");
    		
    		obj = new GolfPlayer[] {
    				player1,
    				player2,
    				player3,
    				player4
    		};
    		*/
  
    		ap = new GolfPlayerAdapter(listView.getContext(), R.layout.listview_course_row, playerlist);
        	listView.setAdapter(ap); 

    	}
    	
    	else
    	{
    		aa = new ArrayAdapter<String>(listView.getContext(),
        								  android.R.layout.simple_list_item_1,
        								  android.R.id.text1,
        								  (String[]) obj); 
        
    	}
    	

    	return listView;
    }


	
	@Override
	public void onActivityCreated(Bundle b)
	{
			super.onActivityCreated(b);

            getLoaderManager().initLoader(PLAYER_LOADER_ID,null,this);
            getLoaderManager().initLoader(TOUR_LOADER_ID,null,this);
            getLoaderManager().initLoader(TOURNAMENT_LOADER_ID,null,this);
            getLoaderManager().initLoader(COURSE_LOADER_ID,null,this);
            
	}
	

    
    @Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = null;
		switch(id)
		{
			case PLAYER_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_PLAYERS,null,null,null,null);
		}
		
		return loader;
	}
	



	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		switch(loader.getId())
		{
			case PLAYER_LOADER_ID:
				int index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
				int nic_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NIC);
				int full_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NAME);
				int hc_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_HC);
				int img_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_IMGURL);
				
				if(ap != null)
					ap.clear();
				else
					playerlist.clear();
				
				while(cursor.moveToNext())
				{
					GolfPlayer newPlayer = new GolfPlayer(cursor.getInt(index));
					newPlayer.setPlayerName(cursor.getString(full_index));
					newPlayer.setPlayerNick(cursor.getString(nic_index));
					newPlayer.setPlayerImg(cursor.getString(img_index));
					newPlayer.setPlayerHC(cursor.getDouble(hc_index));
					
					if(ap != null)
					{
						ap.add(newPlayer);
						ap.notifyDataSetChanged();
					}
					else
					{
						playerlist.add(newPlayer);
					}
				}

				
		}
		
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch(loader.getId())
		{
			case PLAYER_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_PLAYERS,null,null,null,null);
		}
		
		
	}	
	
}


