package com.oas76.RaymonTour;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		id = -1;
		editPlayer = false;
				
		setContentView(R.layout.activity_player_edit);
		
		if(getIntent().getExtras() != null)
		{
			id = getIntent().getExtras().getInt("id");
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

		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		verifyButton = (ImageButton)findViewById(R.id.player_verify);
		hookupButton();
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_player_edit, menu);
		return true;
	}
	*/

	/*
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
	*/
	
	private void hookupButton(){
		final Activity context = this;
		verifyButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				String nic = ((EditText)findViewById(R.id.player_nic)).getText().toString();
				String name = ((EditText)findViewById(R.id.player_full)).getText().toString();
				String hc =  ((EditText)findViewById(R.id.player_hc)).getText().toString();
				double dhc = 36.0;
				if(nic.equals("") || name.equals("") || hc.equals(""))
				{
					Toast.makeText(v.getContext(),"All entries must be edited",Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					Toast.makeText(v.getContext(),"Added:" + nic + ":" + name + ":" + hc,Toast.LENGTH_LONG).show();
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
					setResult(RESULT_OK); 
					finish();
	
				}
				
			}
			
		});
	}

}
