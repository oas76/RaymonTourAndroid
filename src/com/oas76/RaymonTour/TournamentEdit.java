package com.oas76.RaymonTour;


import java.util.ArrayList;


import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class TournamentEdit extends Activity implements OnSharedPreferenceChangeListener   {
	
	String name = null;
	int tournament_id = -1;
	
	boolean bdate = false;
	boolean bcourse = false;
	boolean btour = false;
	boolean bplayer = false;
	boolean bname = false;
	boolean bOK = false;
	boolean bFinished = false;
	
	
	int tYear = 0;
	int tMonth = 0;
	int tDate = 0;
	
	static final int STATE_INTRO = -1;
	static final int STATE_PLAYERS = 0;
	static final int STATE_COURSE = 1;
	static final int STATE_TOUR = 2;
	static final int STATE_NAME = 4;
	static final int STATE_DATE = 5;
	static final int STATE_SETTINGS = 6;
	
	static int CURR_STATE = STATE_INTRO;
	
	static ArrayList<GolfPlayer> mSelectedPlayers = null;
	boolean[] boolList = new boolean[30];
	boolean[] tboolList = new boolean[30];
	GolfCourse gCourse = null;
	ArrayList<Tour> mSelectedTour = null;
	
	int id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tournament_edit);
		
	    if(((RaymonTour)getApplicationContext()).getTournamnetlist().size() == 0)
	    {
	    	   CURR_STATE = STATE_INTRO;
	    	   DialogFragment newFragment = new TextFragment();
	    	   ((TextFragment)newFragment).setState(STATE_INTRO);
	    	   ((TextFragment)newFragment).setDisplayText("OK.. Now to the real stuff. Setting up the tournament. You have added player, tour and courses. The rest is a walk in the park" + 
	   				   									  "Just input and continue to press «>«. The system will allow you to press «V« when data is collected." +
	   				   									  " Remember to check out the standard Android Settings menu before saving the tournament.... OK, that is it... you are now on your own" +
	   				   									  " Happy golfing :)");
	    	   
	   		   newFragment.show(getFragmentManager(), "Welcome to RaymonTour");
	    }
	    else
	    {
	    	if(CURR_STATE == STATE_INTRO)
	    	{
	    		hookupAddPlayer();
	    		CURR_STATE = STATE_PLAYERS;
	    	}
	    }
		
	    getActionBar().setDisplayHomeAsUpEnabled(false);

		// If started with intent with id
		if(getIntent().getExtras() != null)
			id = getIntent().getExtras().getInt("id");
	
	}
	
	@Override
	public void onStart()
	{
		if(((RaymonTour)getApplicationContext()).getTournamnetlist().size() != 0)
		{
			if(CURR_STATE == STATE_INTRO)
			{
				hookupAddPlayer();
				CURR_STATE = STATE_PLAYERS;
			}
		}
		super.onStart();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(bOK)
			getMenuInflater().inflate(R.menu.simple3_action_menu,menu);
		else if(bFinished)
			getMenuInflater().inflate(R.menu.simple3_action_menu,menu);
		else
			getMenuInflater().inflate(R.menu.simple2_action_menu,menu);
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.next:
				if(CURR_STATE == STATE_INTRO)
				{
					hookupAddPlayer();
					CURR_STATE = STATE_PLAYERS;
				}
				else if(CURR_STATE == STATE_PLAYERS)
				{
					hookupCourse();
					CURR_STATE = STATE_COURSE;
				}
				else if(CURR_STATE == STATE_COURSE)
				{
					hookupTour();
					CURR_STATE = STATE_TOUR;
				}
				else if(CURR_STATE == STATE_TOUR)
				{
					hookupDateButton();
					CURR_STATE = STATE_DATE;
				}
				else if(CURR_STATE == STATE_DATE)
				{
					hookupName();
					CURR_STATE = STATE_NAME;
					bOK = true;
					invalidateOptionsMenu();
				}
				else if(CURR_STATE == STATE_SETTINGS)
				{
					if(hookupVerify())
					{
						CURR_STATE  = STATE_INTRO;
						bOK = false;
						bFinished = false;
						startActivity(new Intent(this, MainActivity.class));
					}
					else
					{
						CURR_STATE = STATE_INTRO;
						bOK = false;
						bFinished = false;
						invalidateOptionsMenu();
					}
				}
				break;
		case R.id.verify:
			if(hookupVerify())
			{
				CURR_STATE  = STATE_INTRO;
				startActivity(new Intent(this, MainActivity.class));
			}
			else
				CURR_STATE = STATE_INTRO;
			break;
		case R.id.menu_settings:
			Intent intent = new Intent(this,SettingsActivity.class);
			startActivity(intent);
			break;
				
		}
		return super.onOptionsItemSelected(item);
	}
	

	
	void hookupName()
	{
		
		DialogFragment newFragment = new NameEditFragment();
		if(gCourse != null)
			((NameEditFragment)newFragment).setDefaultName(gCourse.getCourceName() + " - " + String.valueOf(tDate) + "/" + String.valueOf(tMonth) + "/" + String.valueOf(tYear));
		else
			((NameEditFragment)newFragment).setDefaultName("Raymon Tournament");
	    newFragment.show(getFragmentManager(), "namepicker");
	    

	}
	
	void hookupDateButton(){
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");

	}
	
	boolean hookupVerify(){
		// Check input field,if not empty
		if(!(name.equals("")) )
		{
			bname = true;
			
		}
				
		if(setData())
		{
			mSelectedPlayers.clear();
			mSelectedTour.clear();
			return true;
		}
		else
		{
			Toast.makeText(this,"Some mandatory info is missing before creating a tournament. Press «>",Toast.LENGTH_LONG).show();
			return false;
		}

	}
	
	void hookupAddPlayer(){
		DialogFragment newFragment = new PlayerPickerFragment();
		newFragment.show(getFragmentManager(), "playerPicker");

	}
	
	void hookupCourse(){
		DialogFragment newFragment = new CoursePickerFragment();
	    newFragment.show(getFragmentManager(), "coursePicker");
	}
	
	void hookupTour(){
		DialogFragment newFragment = new TourPickerFragment();
	    newFragment.show(getFragmentManager(), "tourPicker");
	}
	
	public void setDate(int year, int month, int date){
		Toast.makeText(this,"Year:Month:Date" + String.valueOf(year) + ":" + String.valueOf(month) + ":" + String.valueOf(date),Toast.LENGTH_LONG).show();
		bdate = true;
		tYear = year;
		tMonth = month;
		tDate = date;
	}
	
	public boolean setData()
	{
		if(bdate && bcourse && btour && bplayer && bname)
		{
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	        ContentValues values = new ContentValues();
			ContentResolver cr = getContentResolver();
			
			values.put(TourContentProvider.KEY_GOLF_MODE, Integer.parseInt(prefs.getString("mode_preference","1")));
			values.put(TourContentProvider.KEY_GOLF_GAME, Integer.parseInt(prefs.getString("game_preference","1")));		
			values.put(TourContentProvider.KEY_HANDICAPED, prefs.getBoolean("use_handicap",true));
			values.put(TourContentProvider.KEY_INDIVIDUAL_CLS3, prefs.getBoolean("individual_cls3", false));
			values.put(TourContentProvider.KEY_STAKES_1PUT, Integer.parseInt(prefs.getString("stakes_1put", "20")));
			values.put(TourContentProvider.KEY_STAKES_CLOSEST, Integer.parseInt(prefs.getString("stakes_closest", "20")));
			values.put(TourContentProvider.KEY_STAKES_LONGEST, Integer.parseInt(prefs.getString("stakes_longest", "20")));
			values.put(TourContentProvider.KEY_STAKES_SNAKE, Integer.parseInt(prefs.getString("stakes_3put", "20")));
			values.put(TourContentProvider.KEY_STAKES, Integer.parseInt(prefs.getString("stakes_tournament", "100")));
			values.put(TourContentProvider.KEY_TOURNAMENT_SPONSOR_PURSE, Integer.parseInt(prefs.getString("purse", "0")));
			values.put(TourContentProvider.KEY_TOURNAMENT_IMGURL, "");
			values.put(TourContentProvider.KEY_TOURNAMENT_NAME, name);
			values.put(TourContentProvider.KEY_COURSE_ID, gCourse.getCourceID());
			values.put(TourContentProvider.KEY_TOURNAMENT_DATE, String.valueOf(tYear) + "-" + String.valueOf(tMonth+1) + "-" + String.valueOf(tDate));
			values.put(TourContentProvider.KEY_CAPPED_STROKE, prefs.getBoolean("cap_score",true));
			values.put(TourContentProvider.KEY_WINNER_CLOSEST, -1);
			values.put(TourContentProvider.KEY_WINNER_LONGEST, -1);
			values.put(TourContentProvider.KEY_WINNER_PUT, -1);
			values.put(TourContentProvider.KEY_WINNER_SNEAK, -1);
			values.put(TourContentProvider.KEY_TOURNAMENT_OFFICIAL, 0);
			
			
			Uri new_entry_uri= cr.insert(TourContentProvider.CONTENT_URI_TOURNAMENTS, values);
			String rowID = new_entry_uri.getPathSegments().get(1);
			
			//cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_PLAYERS,Integer.toString(id)), values, null, null);
			Cursor res = null;
			int index = -1;
			int scoreId = -1;
			
			cr = getContentResolver();
			for(GolfPlayer gp:mSelectedPlayers)
			{

				for(int i = 1; i <= 18; i++)
				{
					values = new ContentValues();
					res = null;
					values.put(TourContentProvider.KEY_PLAYER_ID, gp.getPlayerID());
					values.put(TourContentProvider.KEY_TEAM_ID, gp.getTeamIndex());
					values.put(TourContentProvider.KEY_TOURNAMENT_ID,rowID);
					values.put(TourContentProvider.KEY_COURSE_ID, gCourse.getCourceID());
					values.put(TourContentProvider.KEY_HOLE_NR, i);
				
					res = cr.query(TourContentProvider.CONTENT_URI_SCORES, 
						 new String[]{TourContentProvider.KEY_ID}, 
						 TourContentProvider.KEY_PLAYER_ID + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=? AND " + TourContentProvider.KEY_HOLE_NR + "=?",
						 new String[]{ String.valueOf(gp.getPlayerID()) , String.valueOf(rowID), String.valueOf(i) },
						 null);
					boolean updateEntry=(res != null ? true : false );
					if(updateEntry)
					{
						res.moveToFirst();
						index = res.getColumnIndex(TourContentProvider.KEY_ID);
						scoreId = res.getInt(index);
						cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_SCORES,String.valueOf(scoreId)), values, null, null);
					}
					else
					{
						values.put(TourContentProvider.KEY_GOLF_SCORE,0);
						cr.insert(TourContentProvider.CONTENT_URI_SCORES, values);
						
					}
				}
			}
			
			cr = getContentResolver();
			for(Tour tt : mSelectedTour)
			{
				values = new ContentValues();
				res = null;
				values.put(TourContentProvider.KEY_TOUR_ID, tt.getTourID());
				values.put(TourContentProvider.KEY_TOURNAMENT_ID, rowID);
				res = cr.query(TourContentProvider.CONTENT_URI_TT, 
						 new String[]{TourContentProvider.KEY_ID}, 
						 TourContentProvider.KEY_TOUR_ID + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=?",
						 new String[]{ String.valueOf(tt.getTourID()) , String.valueOf(rowID) },
						 null);
				
				boolean updateEntry=(res != null ? true : false );
				if(updateEntry)
				{
					res.moveToFirst();
					index = res.getColumnIndex(TourContentProvider.KEY_ID);
					scoreId = res.getInt(index);
					cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TT,String.valueOf(scoreId)), values, null, null);
				}
				else
				{
					cr.insert(TourContentProvider.CONTENT_URI_TT, values);					
				}
	
			}

			        
	        return true;
	     
	      
		}
		else
			return false;
		
		
		
	}
	
	@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
		CURR_STATE = STATE_INTRO;
    }
	

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// No changes 
		
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
	@Override
	public void onBackPressed() {
		if(mSelectedPlayers != null)
			mSelectedPlayers.clear();
		for(int i = 0; i < boolList.length; i++)
			boolList[i] = false;
		for(int i = 0; i < tboolList.length; i++)
			tboolList[i] = false;
		gCourse = null;
		name = null;
		if(mSelectedTour != null)
			mSelectedTour.clear();
		CURR_STATE = STATE_INTRO;
		this.finish();
	    super.onBackPressed();
	}

 

}
