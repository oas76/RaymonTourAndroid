package com.oas76.RaymonTour;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
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
    private static final int HOLE_LOADER_ID = 4;
    
    private static String EDIT_ACTIVITY = "Tournamnet";
    private static Activity myActivity = null;
    
	static ArrayList<GolfPlayer> playerlist = new ArrayList<GolfPlayer>();
	ArrayAdapter<GolfPlayer> ap = null;
	static ArrayList<GolfCourse> courselist = new ArrayList<GolfCourse>();
	ArrayAdapter<GolfCourse> ac = null;
	static ArrayList<GolfTournament> tournamentlist = new ArrayList<GolfTournament>();
	ArrayAdapter<GolfTournament> at = null;
	static ArrayList<Tour> tourlist = new ArrayList<Tour>();
	ArrayAdapter<Tour> atour = null;

	
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
    			GolfTournament tournemanet = (GolfTournament)l.getItemAtPosition(pos);
    			int tournament_id = tournemanet.getTournamentID();
    			intent = new Intent(myActivity, TournamentEdit.class);
    			intent.putExtra("id", tournament_id);
    			break;
    		case 2:
    			EDIT_ACTIVITY = "Tour";
    			Tour tour = (Tour)l.getItemAtPosition(pos);
    			int tour_id = tour.getTourID();
    			intent = new Intent(myActivity, TourEdit.class);
    			intent.putExtra("id", tour_id);
    			break;
    		case 3:
    			EDIT_ACTIVITY = "Player";
    			GolfPlayer player = (GolfPlayer)l.getItemAtPosition(pos);
    			int player_id = player.getPlayerID();
    			intent = new Intent(myActivity, PlayerEdit.class);
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
    	Object[] obj = null;
    	listView = new ListView(getActivity());
    	at = new GolfTournamentAdapter(listView.getContext(),R.layout.listview_tournament_row, tournamentlist);
    	ac = new GolfCourseAdapter(listView.getContext(), R.layout.listview_course_row, courselist);
    	ap = new GolfPlayerAdapter(listView.getContext(), R.layout.listview_course_row, playerlist);
    	atour = new GolfTourAdapter(listView.getContext(), R.layout.listview_course_row, tourlist);
    	
    	
    	switch(getArguments().getInt(ARG_SECTION_NUMBER))
    	{
    		case 1:
    			EDIT_ACTIVITY = "Tournament";
    			break;
    		case 2:
    			EDIT_ACTIVITY = "Tour";
    			break;
    		case 3:
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
  			//at = new GolfTournamentAdapter(listView.getContext(),R.layout.listview_tournament_row, tournamentlist);
  			listView.setAdapter(at);
    	}
    	else if(EDIT_ACTIVITY.equals("Course"))
    	{   		
    		//ac = new GolfCourseAdapter(listView.getContext(), R.layout.listview_course_row, courselist);
        	listView.setAdapter(ac); 
    	}
    	else if(EDIT_ACTIVITY.equals("Player"))
    	{
    		//ap = new GolfPlayerAdapter(listView.getContext(), R.layout.listview_course_row, playerlist);
        	listView.setAdapter(ap); 

    	}   	
    	else if(EDIT_ACTIVITY.equals("Tour"))
    	{
    		//atour = new GolfTourAdapter(listView.getContext(), R.layout.listview_course_row, tourlist);
        	listView.setAdapter(atour); 
 
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
            getLoaderManager().initLoader(HOLE_LOADER_ID,null,this);
            
	}
	

    
    @Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = null;
		switch(id)
		{
			case PLAYER_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_PLAYERS,null,null,null,null);
				break;
			case COURSE_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_COURSES,null,null,null,null);
				break;
			case HOLE_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_HOLES,null,null,null,null);
				break;
			case TOURNAMENT_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_TOURNAMENTS,null,null,null,null);
				break;
			case TOUR_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_TOURS,null,null,null,null);
				break;

		}
		
		return loader;
	}
	



	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if(cursor != null)
		{
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
					break;
				case COURSE_LOADER_ID:
					index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					int name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_NAME);
					int tee_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_TEE);
					int length_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_LENGTH);
					int slope_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_SLOPE);
					int value_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_VALUE);
					int par_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_PAR);
					int cimg_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_IMGURL);
				
					if(ac != null)
						ac.clear();
					else
						courselist.clear();
				
					while(cursor.moveToNext())
					{
						GolfCourse newCourse = new GolfCourse(cursor.getInt(index));
						newCourse.setCourceName(cursor.getString(name_index));
						newCourse.setCourceTee(cursor.getString(tee_index));
						newCourse.setCourcePar(cursor.getInt(par_index));
						newCourse.setCourceLength(cursor.getInt(length_index));
						newCourse.setCourceSlope(cursor.getInt(slope_index));
						newCourse.setCourceValue(cursor.getDouble(value_index));
						// setting Img Url
					
						if(ac != null)
						{
							ac.add(newCourse);
							ac.notifyDataSetChanged();
						}
						else
						{
							courselist.add(newCourse);
						}
					}
					break;
				case TOURNAMENT_LOADER_ID:
					index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					//int name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_NAME);
					//int tee_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_TEE);
					//int length_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_LENGTH);
					//int slope_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_SLOPE);
					//int value_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_VALUE);
					//int par_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_PAR);
					//int img_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_IMGURL);
				
					if(at != null)
						at.clear();
					else
						tournamentlist.clear();
				
					while(cursor.moveToNext())
					{
						GolfTournament newTournament = new GolfTournament(cursor.getInt(index));
						//newCourse.setCourceName(cursor.getString(name_index));
						//newCourse.setCourceTee(cursor.getString(tee_index));
						//newCourse.setCourcePar(cursor.getInt(par_index));
						//newCourse.setCourceLength(cursor.getInt(length_index));
						//newCourse.setCourceSlope(cursor.getInt(slope_index));
						//newCourse.setCourceValue(cursor.getDouble(value_index));
					
						if(at != null)
						{
							at.add(newTournament);
							at.notifyDataSetChanged();
						}
						else
						{
							tournamentlist.add(newTournament);
						}
					}
					break;
				case TOUR_LOADER_ID:
					index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_NAME);
					int desc_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_DESC);
					img_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_IMG);
		
					if(atour != null)
						atour.clear();
					else
						tourlist.clear();
		
					while(cursor.moveToNext())
					{
						Tour newTour = new Tour(cursor.getInt(index));
						newTour.setTourName(cursor.getString(name_index));
						newTour.setTourDesc(cursor.getString(desc_index));
						newTour.setTourImgUrl(cursor.getString(img_index));
			
						if(atour != null)
						{
							atour.add(newTour);
							atour.notifyDataSetChanged();
						}
						else
						{
							tourlist.add(newTour);
						}
					}
					break;
					
			} //switch
			
		}// if cursor != null
		
	}


	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		switch(loader.getId())
		{
			case PLAYER_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_PLAYERS,null,null,null,null);
				break;
			case COURSE_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_COURSES,null,null,null,null);
				break;
			case HOLE_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_HOLES,null,null,null,null);
				break;
			case TOURNAMENT_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_TOURNAMENTS,null,null,null,null);
				break;


		}
		
		
	}	

	
	

	
}




