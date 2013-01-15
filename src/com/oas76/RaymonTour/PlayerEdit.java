package com.oas76.RaymonTour;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class PlayerEdit extends Activity  {

	ImageButton verifyButton = null;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_edit);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		verifyButton = (ImageButton)findViewById(R.id.player_verify);
		hookupButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_player_edit, menu);
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
					try {
						dhc = Double.parseDouble(hc);
						}
					catch(Exception e)
					{
						Toast.makeText(v.getContext(),"Handicap must be numerical",Toast.LENGTH_LONG).show();
						return;
					}

					Toast.makeText(v.getContext(),"Added:" + nic + ":" + name + ":" + hc,Toast.LENGTH_LONG).show();
					ContentValues values = new ContentValues();
					ContentResolver cr = getContentResolver();
					
					values.put(TourContentProvider.KEY_PLAYER_NIC, nic);
					values.put(TourContentProvider.KEY_PLAYER_NAME, name);
					values.put(TourContentProvider.KEY_PLAYER_HC, dhc);
					
					cr.insert(TourContentProvider.CONTENT_URI_PLAYERS, values);
					
					//((EditText)findViewById(R.id.player_nic)).setText("");
					//((EditText)findViewById(R.id.player_full)).setText("");
					//((EditText)findViewById(R.id.player_hc)).setText("");
					finish();
				}
				
			}
			
		});
	}

}
