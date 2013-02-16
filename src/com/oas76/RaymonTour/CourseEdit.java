package com.oas76.RaymonTour;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class CourseEdit extends Activity {
	
	
	boolean editTour = false;
	boolean startmode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_edit);
		
		if(((RaymonTour)getApplicationContext()).getCourselist().size() == 0)
		{
			startmode = true;
			DialogFragment newFragment = new TextFragment();
			((TextFragment)newFragment).setDisplayText("Honestly, do you know how boring it is to input all details for a 18 hole course?... Press the «>«button, and " +
		                                           "we will download information from the INTERNET and progress!!. Yes, it can cost some money, but you should not need to do this more" +
				                                   "than a coupple of times a year. Here is an idea for you, You could make sure you do it while connected to WiFi");
				
			newFragment.show(getFragmentManager(), "Welcome to RaymonTour");
		}
		else
			startmode = false;

    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(startmode)
			getMenuInflater().inflate(R.menu.simple2_action_menu,menu);
		else
			getMenuInflater().inflate(R.menu.simple3_action_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		XmlGetter getter = null;
		switch (item.getItemId()) {
		case R.id.next:
			getter = new XmlGetter();
			getter.setContext(this);
		    getter.execute("");
		    Toast.makeText(this,"Downloading course xml...",Toast.LENGTH_LONG).show();
			intent = new Intent(this,TournamentEdit.class);
			break;
		case R.id.verify:
			getter = new XmlGetter();
			getter.setContext(this);
		    getter.execute("");
		    Toast.makeText(this,"Downloading course xml...",Toast.LENGTH_LONG).show();
		    finish();
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


