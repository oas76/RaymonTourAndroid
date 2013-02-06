package com.oas76.RaymonTour;

import java.sql.SQLXML;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class TournamentEdit extends Activity implements OnSharedPreferenceChangeListener   {
	
	ImageButton datePicker = null;
	ImageButton tournamentSettings = null;
	ImageButton addPlayer = null;
	ImageButton addCourse = null;
	ImageButton addTour = null;
	ImageButton verify = null;
	EditText name = null;
	int tournament_id = -1;
	
	boolean bdate = false;
	boolean bcourse = false;
	boolean btour = false;
	boolean bplayer = false;
	boolean bname = false;
	
	
	int tYear = 0;
	int tMonth = 0;
	int tDate = 0;
	
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
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// Show the Up button in the action bar.
		// If started with intent with id
		if(getIntent().getExtras() != null)
			id = getIntent().getExtras().getInt("id");
		
		name = (EditText)findViewById(R.id.tournament_name);
		
		datePicker =(ImageButton)findViewById(R.id.date_button);
		hookupDateButton();
		
		tournamentSettings = (ImageButton)findViewById(R.id.tournament_settings);
		hookupTournamentSettings();
		
		addPlayer = (ImageButton)findViewById(R.id.player_button);
		hookupAddPlayer();
		
		verify = (ImageButton)findViewById(R.id.tournament_verify);
		hookupVerify();
		
		addCourse = (ImageButton)findViewById(R.id.course_button);
		hookupCourse();
		
		addTour = (ImageButton)findViewById(R.id.tour_button);
		hookupTour();
		
		
		
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tournament_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	private void hookupDateButton(){
		final Activity context = this;
		datePicker.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "datePicker");
			}
			
		});
	}
	
	private void hookupTournamentSettings(){
		final Activity context = this;
		tournamentSettings.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				Intent intent = new Intent(context,SettingsActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	private void hookupVerify(){
		final Activity context = this;
		verify.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				// Check input field,if not empty
				if(!((TournamentEdit)context).name.getText().toString().equals("") )
				{
					bname = true;
					Toast.makeText(context,((TournamentEdit)context).name.getText().toString(),Toast.LENGTH_LONG).show();
				}
				
				if(((TournamentEdit)context).setData())
				{
					mSelectedPlayers.clear();
					mSelectedTour.clear();
					setResult(RESULT_OK);
					finish();
				}
				else
					Toast.makeText(context,"All fields must be set before creating a tournament",Toast.LENGTH_LONG).show();
					
			}
			
		});
	}
	
	private void hookupAddPlayer(){
		final Activity context = this;
		addPlayer.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				DialogFragment newFragment = new PlayerPickerFragment();
			    newFragment.show(getFragmentManager(), "playerPicker");

			}
			
		});
	}
	
	private void hookupCourse(){
		final Activity context = this;
		addCourse.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				DialogFragment newFragment = new CoursePickerFragment();
			    newFragment.show(getFragmentManager(), "coursePicker");

			}
			
		});
	}
	
	private void hookupTour(){
		final Activity context = this;
		addTour.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				DialogFragment newFragment = new TourPickerFragment();
			    newFragment.show(getFragmentManager(), "tourPicker");

			}
			
		});
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
			values.put(TourContentProvider.KEY_TOURNAMENT_NAME, name.getText().toString());
			values.put(TourContentProvider.KEY_COURSE_ID, gCourse.getCourceID());
			values.put(TourContentProvider.KEY_TOURNAMENT_DATE, String.valueOf(tYear) + "-" + String.valueOf(tMonth+1) + "-" + String.valueOf(tDate));
			
			
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
						values.put(TourContentProvider.KEY_GOLF_SCORE,-1);
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
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// No changes 
		
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

 

}
