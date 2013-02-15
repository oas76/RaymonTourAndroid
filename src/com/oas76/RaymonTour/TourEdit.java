package com.oas76.RaymonTour;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class TourEdit extends Activity {
	
	
	ImageButton verifyButton = null;
	int id = -1;
	boolean editTour = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_edit);


		
		if(getIntent().getExtras() != null)
		{
			id = getIntent().getExtras().getInt("id");
			ContentResolver cp = getContentResolver();
			Cursor cursorData = cp.query(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TOURS, Integer.toString(id)), null, null, null, null);

			if(cursorData != null && cursorData.getCount() == 1)
			{
				cursorData.moveToFirst();
				
				TextView name = (TextView)findViewById(R.id.tour_name);
				TextView desc = (TextView)findViewById(R.id.tour_desc);
				ImageView img = (ImageView)findViewById(R.id.tour_img);

				
				int name_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_NAME);
				int desc_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_DESC);
				int img_index = cursorData.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_IMG);
				
				name.setText(cursorData.getString(name_index));
				desc.setText(cursorData.getString(desc_index));
				//img.setImageURI(Uri.parse(cursorData.getString(img_index)));
			}
	
			editTour = true;
		}
        
		if(((RaymonTour)getApplicationContext()).getTourlist().size() == 0)
        {
				DialogFragment newFragment = new TextFragment();
				((TextFragment)newFragment).setDisplayText("Allrigth...we now need to set up a tour for your players. Think PGA tour, LPGA tour etc." +
				                                           "Create virtual tours that can last for days, weeks and even years. A tour should contain multipple golf tournaments" + 
						                                   " and a tournament can be part of multipple tours. Confused ? You will get the hang of it. By the way, «v«, «+«and «>«is the same as before," + 
				                                           " and naturally, you can later access your tours from the main menu dropdown.");
				
			    newFragment.show(getFragmentManager(), "Welcome to RaymonTour");

        }
		
		
	}

	
	private boolean verifyData()
	{
		String name = ((EditText)findViewById(R.id.tour_name)).getText().toString();
		String desc =  ((EditText)findViewById(R.id.tour_desc)).getText().toString();
		if(desc.equals("") || name.equals("") )
		{
			Toast.makeText(this,"All entries must be edited",Toast.LENGTH_LONG).show();
			return false;
		}
		else
		{
			ContentValues values = new ContentValues();
			ContentResolver cr = getContentResolver();
			
			values.put(TourContentProvider.KEY_TOUR_DESC, desc);
			values.put(TourContentProvider.KEY_TOUR_NAME, name);
			
			if(id > -1 && editTour)
				cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TOURS,Integer.toString(id)), values, null, null);
			else
			{
				cr.insert(TourContentProvider.CONTENT_URI_TOURS, values);
			}
			return true;
		}

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.verify:
			verifyData();
			break;
		case R.id.next:
			intent = new Intent(this,CourseEdit.class);
			break;
		case R.id.add:
			intent = new Intent(this,TourEdit.class);
			break;
		}
		if(intent != null)
			startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
	
	
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}


