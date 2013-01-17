package com.oas76.RaymonTour;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class PlayerView extends Activity {
	
	int playerId = -1;
	ContentResolver cp = null;
	Cursor cursorData = null;
	ImageButton deleteButton = null;
	ImageButton editButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Retrive id from intent
		playerId = getIntent().getExtras().getInt("id");
		cp = getContentResolver();
		//Toast.makeText(this,"ID:" + Integer.toString(playerId),Toast.LENGTH_LONG).show();
		
		cursorData = cp.query(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_PLAYERS, Integer.toString(playerId)), null, null, null, null);

		setContentView(R.layout.activity_player_view);
		
		if(cursorData != null && cursorData.getCount() == 1)
		{
		
			TextView nic = (TextView)findViewById(R.id.view_player_nic);
			TextView name = (TextView)findViewById(R.id.view_player_full);
			TextView hc = (TextView)findViewById(R.id.view_player_hc);
			TextView winnings = (TextView)findViewById(R.id.view_player_winnings);
			
			int nic_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NIC);
			int name_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NAME);
			int hc_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_HC);
			int winninings_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_WINNINGS);
			
			cursorData.moveToFirst();
			nic.setText(cursorData.getString(nic_index));
			name.setText(cursorData.getString(name_index));
			hc.setText(Double.toString(cursorData.getDouble(hc_index)));
			winnings.setText(Integer.toString(cursorData.getInt(winninings_index)));
			
			//Toast.makeText(this,"Nic:" + cursorData.getString(nic_index),Toast.LENGTH_LONG).show();
	
		}
		

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		deleteButton = (ImageButton)findViewById(R.id.player_delete);
		editButton = (ImageButton)findViewById(R.id.player_edit);
		hookupButtons();
		
		
		


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_player_view, menu);
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
	
	
	private void hookupButtons(){
		final Activity context = this;
		deleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				cp = getContentResolver();
				cp.delete(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_PLAYERS, Integer.toString(playerId)),null,null);
				finish();
				
			}
			
		});
		
		editButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				Intent editIntent = new Intent(context, PlayerEdit.class);
				startActivity(editIntent);
			}
			
		});
	}

}
