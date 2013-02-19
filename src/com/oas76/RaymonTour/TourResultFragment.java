package com.oas76.RaymonTour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TourResultFragment extends ListFragment {
	
	Activity myActivity = null;
	ArrayAdapter<GolfPlayer> ap = null;
	ArrayList<GolfPlayer> al = null;
	ListView listView = null;
	HashMap<Integer,Integer[]> map = null;

    
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
    	int id = getArguments().getInt("ID");
    	listView = new ListView(myActivity);
    	
    	
    	map = ((RaymonTour)myActivity.getApplicationContext()).getTourbyIndex(id).tourWinningsList(myActivity);
    	Set<Integer> keys = map.keySet();
    	ArrayList<GolfPlayer> players = new ArrayList<GolfPlayer>();
    	for(Integer i : keys)
    	{
    		players.add(((RaymonTour)getActivity().getApplicationContext()).getPlayerbyIndex(i));
    	}
    	
    	Collections.sort(players, new Comparator<GolfPlayer>() {

			@Override
			public int compare(GolfPlayer lhs, GolfPlayer rhs) {
				// TODO Auto-generated method stub
				return (map.get(lhs.getPlayerID())[0] - map.get(rhs.getPlayerID())[0])*-1;
			}
    		
    	});
    	
    	
     	ap = new TourResultAdapter(listView.getContext(),
    								R.layout.listview_result_row, 
    								players,
    								map);

        listView.setAdapter(ap); 
        return listView;
    }

    
    

}