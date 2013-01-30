package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
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

public class TournamentEdit extends Activity  {
	
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
	
	ArrayList<GolfPlayer> mSelectedPlayers = null;
	boolean[] boolList = new boolean[30];
	boolean[] tboolList = new boolean[30];
	GolfCourse gCourse = null;
	ArrayList<Tour> mSelectedTour = null;
	
	int id = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tournament_edit);
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
					context.finish();
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
		return 	(bdate && bcourse && btour && bplayer && bname);
		
	}

 

}
