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
	ImageButton verify = null;
	int tournament_id = -1;
	
	
	int tYear = 0;
	int tMonth = 0;
	int tDate = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tournament_edit);
		// Show the Up button in the action bar.
		
		Spinner spinner = (Spinner) findViewById(R.id.course_picker);
		ArrayList<CharSequence> ac = new ArrayList<CharSequence>();
		ac.add("Quinta Da M");
		ac.add("Gr¿nmo");
		ac.add("Oslo GK");

		DropDownAdapter adapter = new DropDownAdapter(this,
		         R.layout.listview_dropdown,ac);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.listview_dropdown);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		datePicker =(ImageButton)findViewById(R.id.date_button);
		hookupDateButton();
		
		tournamentSettings = (ImageButton)findViewById(R.id.tournament_settings);
		hookupTournamentSettings();
		
		addPlayer = (ImageButton)findViewById(R.id.tournament_addplayer);
		hookupAddPlayer();
		
		verify = (ImageButton)findViewById(R.id.tournament_verify);
		hookupVerify();
		
		

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
				Intent intent = new Intent(context,SettingsActivity.class);
				startActivity(intent);
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
	
	public void setDate(int year, int month, int date){
		Toast.makeText(this,"Year:Month:Date" + String.valueOf(year) + ":" + String.valueOf(month) + ":" + String.valueOf(date),Toast.LENGTH_LONG).show();
		tYear = year;
		tMonth = month;
		tDate = date;
	}

 

}
