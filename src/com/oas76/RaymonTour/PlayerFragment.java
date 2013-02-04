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


public class PlayerFragment extends ListFragment {
	
	ArrayList<GolfPlayer> playerList = null;
	Activity myActivity = null;
	
	
	public void setPlayerList(ArrayList<GolfPlayer> players)
	{
		playerList = players;
	}
	
    @Override
    public void onAttach(Activity act)
    {
    	super.onAttach(act);
   		myActivity = act;
    }
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
		ListView lview = new ListView(getActivity());
		ArrayAdapter<GolfPlayer> ap = new GolfPlayerPickerAdapter(myActivity, R.layout.listview_playerpicker_row);
		lview.setAdapter(ap);

		
		return lview;
		
		




	}
}
