package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ScoreInputFragment extends ListFragment {
	
	Activity myActivity = null;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String TOURNAMENT_ID = "tournament_id";
	
    @Override
    public void onAttach(Activity act)
    {
    	super.onAttach(act);
   		myActivity = act;
    }
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
		int holeNr = getArguments().getInt(ARG_SECTION_NUMBER);
		int tId = getArguments().getInt(TOURNAMENT_ID);
		int pId = -1;
		int pId_index = -1;
		int cId = -1;
		int cId_index = -1;
		ArrayList<String> playerList = new ArrayList<String>();
		Cursor cur2 = null;
		
		ContentResolver cr = myActivity.getContentResolver();
		Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES, 
							  null, 
							  TourContentProvider.KEY_TOURNAMENT_ID + "=? AND " + TourContentProvider.KEY_HOLE_NR + "=?", 
							  new String[]{String.valueOf(tId),String.valueOf(holeNr)},
							  null);
		if(cur != null)
		{
			while(cur.moveToNext())
			{
				pId_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_ID);
				pId = cur.getInt(pId_index);
				cId_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_ID);
				cId = cur.getInt(cId_index);
				playerList.add(String.valueOf(pId));
				
			}
		}
		
		Bundle args = new Bundle();
        args.putInt("TournamentId", tId);
        args.putInt("CourseId",cId);
        args.putInt("HoleId", holeNr);
		
		ListView lview = new ListView(getActivity());
		ArrayAdapter<String> ap = new ScoreEditAdapter(myActivity,R.layout.listview_scoreedit_row,playerList,args);
		lview.setAdapter(ap);

		
		return lview;

	}
}
