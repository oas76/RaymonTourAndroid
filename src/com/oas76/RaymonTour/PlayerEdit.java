package com.oas76.RaymonTour;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class PlayerEdit extends Activity  {

	ImageButton verifyButton = null;
	boolean editPlayer = false;
	int id = -1;
	boolean startMode = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = -1;
		editPlayer = false;
				
		setContentView(R.layout.activity_player_edit);
		
		
		if(getIntent().getExtras() != null)
		{
			id = getIntent().getExtras().getInt("id");
			getActionBar().setTitle("Edit Player");
			ContentResolver cp = getContentResolver();
			Cursor cursorData = cp.query(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_PLAYERS, Integer.toString(id)), null, null, null, null);

			if(cursorData != null && cursorData.getCount() == 1)
			{
				cursorData.moveToFirst();
				
				TextView nic = (TextView)findViewById(R.id.player_nic);
				TextView name = (TextView)findViewById(R.id.player_full);
				TextView hc = (TextView)findViewById(R.id.player_hc);
				TextView winnings = (TextView)findViewById(R.id.player_winnings);
				
				int nic_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NIC);
				int name_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NAME);
				int hc_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_HC);
				int winnings_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_WINNINGS);
				
				nic.setText(cursorData.getString(nic_index));
				name.setText(cursorData.getString(name_index));
				hc.setText(Double.toString(cursorData.getDouble(hc_index)));
				winnings.setText("Total Winnings: " + String.valueOf(cursorData.getInt(winnings_index)));
			}
	
			editPlayer = true;
			
		}
		
        if(((RaymonTour)getApplicationContext()).getPlayerlist().size() == 0)
        {
			startMode = true;
        	DialogFragment newFragment = new TextFragment();
			((TextFragment)newFragment).setDisplayText("Greetings and welcome to your first experience with the Raymon Tour android app. " +
													   "Lets start with adding players!!  Input player details and press «>« to save and progress in the wizzard."+
													   "NB! You can always access edit and add new players from the «Players« field in the main window dropdown later, but lets first do a qucik spinn through the program...");
			
		    newFragment.show(getFragmentManager(), "Welcome to RaymonTour");
        	
        }
        else
        	startMode = false;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		if(startMode)
			getMenuInflater().inflate(R.menu.simple2_action_menu,menu);
		else
			getMenuInflater().inflate(R.menu.simple3_action_menu,menu);
		if(editPlayer)
			getMenuInflater().inflate(R.menu.entry_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.verify:
			if(verifyData())
			{
				finish();
			}
			break;
		case R.id.next:
			if(verifyData())
				intent = new Intent(this,TourEdit.class);
			break;
		case R.id.entry_menu:
			((RaymonTour)getApplicationContext()).hookupAreYouSure("This Player entry and all data connected to this Player will be deleted forever. Is this OK ?",this,id);
			break;
		}
		if(intent != null)
			startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
	
	
	private boolean verifyData(){
		String nic = ((EditText)findViewById(R.id.player_nic)).getText().toString();
		String name = ((EditText)findViewById(R.id.player_full)).getText().toString();
		String hc =  ((EditText)findViewById(R.id.player_hc)).getText().toString();
		double dhc = 0;
		try{
			dhc = Double.parseDouble(hc);
			}
		catch(Exception e)
		{
			hc="";
		}

		
		if(nic.equals("") || name.equals("") || hc.equals(""))
		{
			Toast.makeText(this,"All entries must be edited",Toast.LENGTH_LONG).show();
			return false;
		}
		else
		{
			
			ContentValues values = new ContentValues();
			ContentResolver cr = getContentResolver();
			
			values.put(TourContentProvider.KEY_PLAYER_NIC, nic);
			values.put(TourContentProvider.KEY_PLAYER_NAME, name);
			values.put(TourContentProvider.KEY_PLAYER_HC, dhc);
			
			if(id > -1 && editPlayer)
				cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_PLAYERS,Integer.toString(id)), values, null, null);
			else
			{
				cr.insert(TourContentProvider.CONTENT_URI_PLAYERS, values);
			}
			Toast.makeText(this,"Added:" + nic + ":" + name + ":" + hc,Toast.LENGTH_LONG).show();
			return true;
		}
	}
	
	
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
