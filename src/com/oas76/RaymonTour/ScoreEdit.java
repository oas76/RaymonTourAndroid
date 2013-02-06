package com.oas76.RaymonTour;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListFragment;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;

public class ScoreEdit extends Activity implements OnNavigationListener {
	
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static int SELECTED_VIEW = 0;
	private static int TOURNAMENT_ID = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_edit);
		final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        String input[] = new String[]{"Hole 1","Hole 2","Hole 3","Hole 4","Hole 5","Hole 6","Hole 7","Hole 8","Hole 9","Hole 10","Hole 11","Hole 12","Hole 13","Hole 14","Hole 15","Hole 16","Hole 17","Hole 18"};

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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_score_edit, menu);
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

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		ListFragment fragment = new ScoreInputFragment();
        Bundle args = new Bundle();
        args.putInt(ScoreInputFragment.ARG_SECTION_NUMBER, itemPosition + 1);
        args.putInt(ScoreInputFragment.TOURNAMENT_ID, this.TOURNAMENT_ID);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.score_input_fragment, fragment)
                .commit();
		SELECTED_VIEW = itemPosition;
		return true;
	}
	
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
            
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
        SELECTED_VIEW = getActionBar().getSelectedNavigationIndex();
    }

}
