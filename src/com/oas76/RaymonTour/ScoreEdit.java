package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ScoreEdit extends Activity implements OnNavigationListener {
	
	// Test Git Hub Integration
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static int SELECTED_VIEW = 0;
	private static int TOURNAMENT_ID = -1;
	private int menu_entries = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_edit);
		// Allign ArrayList
		
		final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        String input[] = new String[]{"Hole 1","Hole 2","Hole 3","Hole 4","Hole 5","Hole 6","Hole 7","Hole 8","Hole 9","Hole 10","Hole 11","Hole 12","Hole 13","Hole 14","Hole 15","Hole 16","Hole 17","Hole 18","CL1S","Leaderboard"};
        menu_entries = input.length;
        
        // Store ID for tournament
        TOURNAMENT_ID = getIntent().getExtras().getInt("id");
        		
		// Show the Up button in the action bar.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        input),
                this);
        actionBar.setSelectedNavigationItem(SELECTED_VIEW);
        
        //TextView head = (TextView)findViewById(R.id.score_headline);
        //TextView head2 = (TextView)findViewById(R.id.score_headline2);
        //TextView details = (TextView)findViewById(R.id.score_details);
        //GolfTournament gt = getTournamentbyIndex(TOURNAMENT_ID);
        //GolfCourse gc = getCoursebyIndex(gt.getTournamentGolfCourceID());
        //head.setText(gc.getCourceName());
        //head2.setText("Hole " + String.valueOf(SELECTED_VIEW + 1) + " : " + gc.getHoleName(SELECTED_VIEW + 1));
        //details.setText("Par: " + gc.getHolePar(SELECTED_VIEW + 1) + " Index: " + gc.getHoleIndex(SELECTED_VIEW + 1) + " Length: " + gc.getHoleLength(SELECTED_VIEW + 1));
        
        

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.simple_action_menu,menu);
		getMenuInflater().inflate(R.menu.entry_menu, menu);
		return super.onCreateOptionsMenu(menu);
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
		case R.id.verify:
			if(SELECTED_VIEW + 1 == this.menu_entries)
			{
				((RaymonTour)getApplicationContext()).hookupDoYouWannaStore("Do you want to make these scores official? NB! it is not possible to reverse",this,TOURNAMENT_ID);
			}
		
			getActionBar().setSelectedNavigationItem(SELECTED_VIEW);
			Fragment fragment = new ScoreInputFragment();
			Bundle args = new Bundle();
			args.putInt(ScoreInputFragment.ARG_SECTION_NUMBER, SELECTED_VIEW + 1);
			args.putInt(ScoreInputFragment.TOURNAMENT_ID, this.TOURNAMENT_ID);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
	                .replace(R.id.score_input_fragment, fragment)
	                .commit();
			
			break;
		case R.id.next:
			if(SELECTED_VIEW + 1 == this.menu_entries)
				SELECTED_VIEW = -1;
			getActionBar().setSelectedNavigationItem(SELECTED_VIEW+1);
			fragment = new ScoreInputFragment();
	        args = new Bundle();
	        args.putInt(ScoreInputFragment.ARG_SECTION_NUMBER, SELECTED_VIEW + 1 + 1);
	        args.putInt(ScoreInputFragment.TOURNAMENT_ID, this.TOURNAMENT_ID);
	        fragment.setArguments(args);
	        getFragmentManager().beginTransaction()
	                .replace(R.id.score_input_fragment, fragment)
	                .commit();
		   
			break;
		case R.id.entry_menu:
			((RaymonTour)getApplicationContext()).hookupAreYouSure("This Tournament entry and all data connected to this Tournament will be deleted forever. Is this OK ?",this,TOURNAMENT_ID);
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		SELECTED_VIEW = itemPosition;
		
		if(itemPosition < 18)
		{

			ListFragment fragment = new ScoreInputFragment();
			Bundle args = new Bundle();
			args.putInt(ScoreInputFragment.ARG_SECTION_NUMBER, itemPosition + 1);
			args.putInt(ScoreInputFragment.TOURNAMENT_ID, this.TOURNAMENT_ID);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
                .replace(R.id.score_input_fragment, fragment)
                .commit();
		
			TextView head = (TextView)findViewById(R.id.score_headline);
			TextView head2 = (TextView)findViewById(R.id.score_headline2);
			TextView details = (TextView)findViewById(R.id.score_details);
			GolfTournament gt = ((RaymonTour)getApplicationContext()).getTournamentbyIndex(TOURNAMENT_ID);
			GolfCourse gc = ((RaymonTour)getApplicationContext()).getCoursebyIndex(gt.getTournamentGolfCourceID());
			head.setText(gc.getCourceName());
			head2.setText("Hole " + String.valueOf(SELECTED_VIEW + 1) + " : " + gc.getHoleName(SELECTED_VIEW + 1));
			details.setText("Par: " + gc.getHolePar(SELECTED_VIEW + 1) + " Index: " + gc.getHoleIndex(SELECTED_VIEW + 1) + " Length: " + gc.getHoleLength(SELECTED_VIEW + 1));
		}
		else if(itemPosition == 18)
		{
			ListFragment fragment = new ScoreInputFragment();
			Bundle args = new Bundle();
			args.putInt(ScoreInputFragment.ARG_SECTION_NUMBER, itemPosition + 1);
			args.putInt(ScoreInputFragment.TOURNAMENT_ID, this.TOURNAMENT_ID);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
                .replace(R.id.score_input_fragment, fragment)
                .commit();
		
			TextView head = (TextView)findViewById(R.id.score_headline);
			TextView head2 = (TextView)findViewById(R.id.score_headline2);
			TextView details = (TextView)findViewById(R.id.score_details);
			GolfTournament gt = ((RaymonTour)getApplicationContext()).getTournamentbyIndex(TOURNAMENT_ID);
			GolfCourse gc = ((RaymonTour)getApplicationContext()).getCoursebyIndex(gt.getTournamentGolfCourceID());
			head.setText(gt.getTournamentName() + " : " + gc.getCourceName());
			head2.setText("Register Games in Games");
			details.setText("                               Closest         Longest         1Put         Snake    ");
		}
		else if(itemPosition == 19)
		{
			
			ListFragment fragment = new ResultFragment();
			Bundle args = new Bundle();
			args.putInt(ScoreInputFragment.TOURNAMENT_ID, this.TOURNAMENT_ID);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
                .replace(R.id.score_input_fragment, fragment)
                .commit();
			
			TextView head = (TextView)findViewById(R.id.score_headline);
			TextView head2 = (TextView)findViewById(R.id.score_headline2);
			TextView details = (TextView)findViewById(R.id.score_details);
			GolfTournament gt = ((RaymonTour)getApplicationContext()).getTournamentbyIndex(TOURNAMENT_ID);
			GolfCourse gc = ((RaymonTour)getApplicationContext()).getCoursebyIndex(gt.getTournamentGolfCourceID());
			head.setText(gt.getTournamentName() + " : " + gc.getCourceName());
			head2.setText("Leaderboard");
			details.setText("Press the verify button to register final result");
		}
  
		
		
		
		return true;
	}
	
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
            SELECTED_VIEW = savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM);
            
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
        
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    

    
    
    

}
