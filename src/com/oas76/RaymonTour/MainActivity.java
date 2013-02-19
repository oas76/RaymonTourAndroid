package com.oas76.RaymonTour;

import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;


import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    
    //private static ImageButton addButton = null;
    
    private static int SELECTED_VIEW = 1;
    public static final String MAIN_STATE = "MainState";
    
    public static final int SELECT_TOURNAMENT = 1;
    public static final int SELECT_TOUR = 2;
    public static final int SELECT_PLAYER = 3;
    public static final int SELECT_COURSE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        String[] menu_strings = new String[4];
        menu_strings[SELECT_TOURNAMENT-1] = getString(R.string.title_section1);
        menu_strings[SELECT_TOUR-1] = getString(R.string.title_section2);
        menu_strings[SELECT_PLAYER-1] = getString(R.string.title_section3);
        menu_strings[SELECT_COURSE-1] = getString(R.string.title_section4);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        menu_strings),
                this);

        if(((RaymonTour)getApplicationContext()).getPlayerlist().size() == 0)
        {
        	actionBar.setSelectedNavigationItem(SELECT_PLAYER-1);
        	startActivity(new Intent(this, PlayerEdit.class));
        }


        
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
    	int restore_state;
		if(getIntent() != null)
		{
    		restore_state = getIntent().getExtras().getInt(MAIN_STATE);
    		getActionBar().setSelectedNavigationItem(restore_state-1);
    		SELECTED_VIEW = restore_state;
		}
 		else if(savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
            SELECTED_VIEW = savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM) + 1;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        getMenuInflater().inflate(R.menu.simple4_action_menu, menu);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this,SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.addnew:
			intent = null;
			XmlGetter getter = null;
			switch(SELECTED_VIEW)
			{
				case SELECT_TOURNAMENT:
					intent = new Intent(this,TournamentEdit.class);
					break;
				case SELECT_TOUR:
					intent = new Intent(this, TourEdit.class);
					break;
				case SELECT_PLAYER:
					//Send intent to edit player view
					intent = new Intent(this,PlayerEdit.class);
					break;
				case SELECT_COURSE:
					getter = new XmlGetter();
					getter.setContext(this);
					getter.execute("");
					break;
			
			}
			if(intent != null)
	    		startActivity(intent);
	    	else
	    		Toast.makeText(this,"Downloading course xml...",Toast.LENGTH_LONG).show();
			break;			
		}
		return super.onOptionsItemSelected(item);
	
	}
		
    
    
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
    	this.reDrawSectionFragment(position + 1);
        /*ListFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        SELECTED_VIEW = position + 1;
        */
        return true;
    }
    
    @Override
    public void onStart()
    {
    	super.onStart();
    	int return_mode = SELECTED_VIEW;
    	Intent intent = getIntent();
    	
    	if(intent != null)
    	{
    		Bundle args = intent.getExtras();
    		if(args != null)
    			return_mode = args.getInt("ReturnMode");
    	}
    	reDrawSectionFragment(return_mode);
    	
    }
    
    
    public void reDrawSectionFragment(int position)
    {
        ListFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(SectionFragment.ARG_SECTION_NUMBER, position);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        SELECTED_VIEW = position;
    }
    
    //private void hookupButton()
    //{
    //
    //}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// Ignore results. Just redraw fragments
    	reDrawSectionFragment(SELECTED_VIEW);
    	/*ListFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(SectionFragment.ARG_SECTION_NUMBER, SELECTED_VIEW);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
         */
    }//onAcrivityResult
    

    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
} // MainActivity
    

