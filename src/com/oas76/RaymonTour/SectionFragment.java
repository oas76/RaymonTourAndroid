package com.oas76.RaymonTour;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public final class SectionFragment extends ListFragment {
	
    public static final String ARG_SECTION_NUMBER = "section_number";
    private static String EDIT_ACTIVITY = "Tournamnet";
    private static Activity myActivity = null;

    
    @Override
    public void onAttach(Activity act)
    {
    	super.onAttach(act);
   		myActivity = act;
    }
    
    
    @Override
    public void onListItemClick(ListView l, View view, int pos, long id)
    {
    	//Toast.makeText(myActivity,"Click",Toast.LENGTH_LONG).show();
    	Intent intent = new Intent(myActivity, TournamentEdit.class);
    	startActivity(intent);
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Create a new TextView and set its text to the fragment's section
        // number argument value.
    	String[] strings = null;
    	
    	switch(getArguments().getInt(ARG_SECTION_NUMBER))
    	{
    		case 1:
    			strings = new String[] { "Tournament1", "Tournamnet2", "Tournament3" };
    			EDIT_ACTIVITY = "Tournamnet";
    			break;
    		case 2:
    			strings = new String[] { "Tour1", "Tour2", "Tour3" };
    			EDIT_ACTIVITY = "Tour";
    			break;
    		case 3:
    			strings = new String[] { "Odd", "PŒl", "Anders" };
    			EDIT_ACTIVITY = "Player";
    			break;
    		case 4:
    			strings = new String[] { "Gr¿nmo", "Quinta da Marhina", "Valderama" };
    			EDIT_ACTIVITY = "Course";
    			break;
    		default:
    			strings = new String[] { "...Loading..." };
    	
    	}
    	
    
    	
    	
    	
    	
        ListView listView = new ListView(myActivity);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(listView.getContext(),
        												   android.R.layout.simple_list_item_1,
        												   android.R.id.text1,
        												   strings); 
        
        listView.setAdapter(aa); 
       
        
        return listView;
    }	
	
}


