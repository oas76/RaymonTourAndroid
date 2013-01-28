package com.oas76.RaymonTour;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
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
			
	verifyButton = (ImageButton)findViewById(R.id.tour_verify);
	hookupButton();

		
		
	}
	
	private void hookupButton(){
		final Activity context = this;
		verifyButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
			{
				String name = ((EditText)findViewById(R.id.tour_name)).getText().toString();
				String desc =  ((EditText)findViewById(R.id.tour_desc)).getText().toString();
				if(desc.equals("") || name.equals("") )
				{
					Toast.makeText(v.getContext(),"All entries must be edited",Toast.LENGTH_LONG).show();
					return;
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
					context.onBackPressed();
	
				}
				
			}
			
		});
	}

}


