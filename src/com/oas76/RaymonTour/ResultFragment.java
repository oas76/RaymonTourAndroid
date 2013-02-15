package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultFragment extends ListFragment {
	
	Activity myActivity = null;
	ArrayAdapter<GolfPlayer> ap = null;
	ArrayList<GolfPlayer> al = null;
	ListView listView = null;

    
    @Override
    public void onAttach(Activity act)
    {
    	super.onAttach(act);
   		myActivity = act;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Object[] obj = null;
    	listView = new ListView(myActivity);
    	int tId = getArguments().getInt(ScoreInputFragment.TOURNAMENT_ID);
    	Bundle args = new Bundle();
    	args.putInt(ScoreInputFragment.TOURNAMENT_ID, tId);
    	
    	al = ((RaymonTour)myActivity.getApplicationContext()).getTournamentbyIndex(tId).tournamentResultList(myActivity);
    	ap = new ResultAdapter(listView.getContext(),
    								R.layout.listview_result_row, 
    								al,
    								args);

    		//ap = new GolfPlayerAdapter(listView.getContext(), R.layout.listview_course_row, playerlist);
        listView.setAdapter(ap); 
        return listView;
    }

    
    

}
