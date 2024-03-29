package com.oas76.RaymonTour;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.Date;
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
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
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
    private static final int SCORE_LOADER_ID = 5;
    private static final int TT_LOADER_ID = 6;
    
    private static String EDIT_ACTIVITY = "Tournamnet";
    private static Activity myActivity = null;
    
	ArrayAdapter<GolfPlayer> ap = null;
	ArrayAdapter<GolfCourse> ac = null;
	ArrayAdapter<GolfTournament> at = null;
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
    			//Add register score functionality here....
    			intent = new Intent(myActivity, ScoreEdit.class);
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
    			intent = new Intent(myActivity, CourseEdit.class);
    			break;
    			
    	}
    	
    	if(intent != null)
    		startActivity(intent);
    	
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Object[] obj = null;
    	listView = new ListView(getActivity());
    	
    	at = new GolfTournamentAdapter(listView.getContext(),
    									R.layout.listview_tournament_row, 
    									((RaymonTour)myActivity.getApplication()).getTournamnetlist());
    	ac = new GolfCourseAdapter(listView.getContext(), 
    								R.layout.listview_course_row, 
    								((RaymonTour)myActivity.getApplication()).getCourselist());
    	ap = new GolfPlayerAdapter(listView.getContext(), 
    								R.layout.listview_course_row, 
    								((RaymonTour)myActivity.getApplication()).getPlayerlist());
    	atour = new GolfTourAdapter(listView.getContext(), 
    								R.layout.listview_course_row, 
    								((RaymonTour)myActivity.getApplication()).getTourlist());
    	
    	
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
  			if(at != null)
    			listView.setAdapter(at);
    	}
    	else if(EDIT_ACTIVITY.equals("Course"))
    	{   		
    		if(ac != null)
    			listView.setAdapter(ac); 
    	}
    	else if(EDIT_ACTIVITY.equals("Player"))
    	{
    		if(ap != null)
    			listView.setAdapter(ap); 

    	}   	
    	else if(EDIT_ACTIVITY.equals("Tour"))
    	{
    		if(atour != null)
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
            getLoaderManager().initLoader(SCORE_LOADER_ID,null,this);
            getLoaderManager().initLoader(TT_LOADER_ID,null,this);
            
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
			case SCORE_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_SCORES,null,null,null,null);
				break;
			case TT_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_TT,null,null,null,null);
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
						((RaymonTour)myActivity.getApplication()).getPlayerlist().clear();
				
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
							((RaymonTour)myActivity.getApplication()).getPlayerlist().add(newPlayer);
						}
					}
					cursor.close();
					if(ap != null && ap.getCount() == 0)
					{
						((RaymonTour)myActivity.getApplication()).getPlayerlist().clear();
						ap.notifyDataSetChanged();
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
						((RaymonTour)myActivity.getApplication()).getCourselist().clear();
				
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
							((RaymonTour)myActivity.getApplication()).getCourselist().add(newCourse);
						}
					
					
					}
					cursor.close();
					cursor = myActivity.getContentResolver().query(TourContentProvider.CONTENT_URI_HOLES, null, null, null, null);
					// Never break course update. Make sure that hole info is added ASAP.;
				case HOLE_LOADER_ID:
					index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NAME);
					int nr_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NR);
					length_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_LENGTH);
					int index_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_INDEX );
					par_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_PAR);
					int course_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_ID);
					
					int i =0;
					while(cursor.moveToNext())
					{
						int hole_nr = cursor.getInt(nr_index);
						i=0;
						for(GolfCourse gc : ((RaymonTour)myActivity.getApplication()).getCourselist())
						{
							if(gc.getCourceID() == cursor.getInt(course_index))
							{
								gc.setHoleName(hole_nr, cursor.getString(name_index));
								gc.setHoleIndex(hole_nr, cursor.getInt(index_index));
								gc.setHoleLength(hole_nr, cursor.getInt(length_index));
								gc.setHolePar(hole_nr, cursor.getInt(par_index));
								((RaymonTour)myActivity.getApplication()).getCourselist().set(i, gc);
							}
							i++;
						}
					}
					if(ac != null && ac.getCount() == 0)
					{
						((RaymonTour)myActivity.getApplication()).getCourselist().clear();
						ac.notifyDataSetChanged();
					}
					break;


					
				case TOURNAMENT_LOADER_ID:
					index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_NAME);
					int mode_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_MODE);
					int game_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_GAME);
					int icls3_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_INDIVIDUAL_CLS3);
					int handicap_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HANDICAPED);
					int stakes_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES);
					int stakes_longest_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_LONGEST);
					int stakes_closest_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_CLOSEST);
					int stakes_3put_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_SNAKE);
					int stakes_1put_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_1PUT);
					int date_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_DATE);
					course_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_ID);
					int cstroke_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_CAPPED_STROKE);
					int winner_closest = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_CLOSEST);
					int winner_longest = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_LONGEST);
					int winner_put = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_PUT);
					int winner_sneak = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_SNEAK);
					int isOfficial = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_OFFICIAL);
					
					//int img_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY....);
				
					if(at != null)
						at.clear();
					else
						((RaymonTour)myActivity.getApplication()).getTournamnetlist().clear();
				
					while(cursor.moveToNext())
					{
						GolfTournament newTournament = new GolfTournament(cursor.getInt(index));
						newTournament.setTouramentName(cursor.getString(name_index));
						newTournament.setTournamentGame(cursor.getInt(game_index));
						newTournament.setTournamentMode(cursor.getInt(mode_index));
						newTournament.setTournamentHandicapped(cursor.getInt(handicap_index));
						newTournament.setTournamentIndividualCLS1(cursor.getInt(icls3_index));
						newTournament.setTournamentGolfCourceID(cursor.getInt(course_index));
						newTournament.setStakes(cursor.getInt(stakes_index), 
												cursor.getInt(stakes_closest_index), 
												cursor.getInt(stakes_longest_index),
												cursor.getInt(stakes_3put_index), 
												cursor.getInt(stakes_1put_index));
						newTournament.setTournamentDate(Date.valueOf(cursor.getString(date_index)));
						newTournament.setCappedStroke(cursor.getInt(cstroke_index));
						newTournament.setWinnerClosest(cursor.getInt(winner_closest));
						newTournament.setWinnerLongest(cursor.getInt(winner_longest));
						newTournament.setWinnerPut(cursor.getInt(winner_put));
						newTournament.setWinnerSneak(cursor.getInt(winner_sneak));
						newTournament.setIsOffical(cursor.getInt(isOfficial));
						

					
						if(at != null)
						{
							at.add(newTournament);
							at.notifyDataSetChanged();
						}
						else
						{
							((RaymonTour)myActivity.getApplication()).getTournamnetlist().add(newTournament);
						}
					}
					cursor.close();
					if(at != null && at.getCount() == 0)
					{
						((RaymonTour)myActivity.getApplication()).getTournamnetlist().clear();
						at.notifyDataSetChanged();
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
						((RaymonTour)myActivity.getApplication()).getTourlist().clear();
		
					while(cursor.moveToNext())
					{
						Tour newTour = new Tour(cursor.getInt(index));
						newTour.setTourName(cursor.getString(name_index));
						newTour.setTourDesc(cursor.getString(desc_index));
						newTour.setTourImgUrl(cursor.getString(img_index));
			
						if(atour != null)
						{
							atour.add(newTour);
							//atour.notifyDataSetChanged();
						}
						else
						{
							((RaymonTour)myActivity.getApplication()).getTourlist().add(newTour);
						}
					}
					cursor.close();
					if(atour != null && atour.getCount() == 0)
					{
						((RaymonTour)myActivity.getApplication()).getTourlist().clear();
						atour.notifyDataSetChanged();
					}
					break;
				case SCORE_LOADER_ID:
					break;
				case TT_LOADER_ID:
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
			case TOUR_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_TOURS,null,null,null,null);
				break;
			case SCORE_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_SCORES,null,null,null,null);
				break;
			case TT_LOADER_ID:
				loader = new CursorLoader(myActivity,TourContentProvider.CONTENT_URI_TT,null,null,null,null);
				break;


		}
		
		
	}	
	
	

	
}




