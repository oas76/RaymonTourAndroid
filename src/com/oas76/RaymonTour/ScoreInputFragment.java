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
		if(holeNr > 18)
			holeNr = 18;
		
		int tId = getArguments().getInt(TOURNAMENT_ID);
		int pId = -1;
		int pId_index = -1;
		ArrayList<GolfTeam> playerList = new ArrayList<GolfTeam>();
		ArrayList<GolfPlayer> tmpList = null;
		int layoutId = 0;
		
		ContentResolver cr = myActivity.getContentResolver();
		
		Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES, 
							  null, 
							  TourContentProvider.KEY_TOURNAMENT_ID + "=? AND " + TourContentProvider.KEY_HOLE_NR + "=? AND " + TourContentProvider.KEY_TEAM_ID + "=?", 
							  new String[]{String.valueOf(tId),String.valueOf(holeNr), "0"},
							  null);
		if(cur != null)
		{
			// This is an individual game. ( If not all have team, then individual ) 
			playerList.clear();
			while(cur.moveToNext())
			{
				pId_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_ID);
				pId = cur.getInt(pId_index);
				tmpList = new ArrayList<GolfPlayer>();
				tmpList.add(((RaymonTour)myActivity.getApplicationContext()).getPlayerbyIndex(pId));
				playerList.add(new GolfTeam(tmpList));
			}
		}
		
		else
		{
			// Assume 4 teams.. Create GolfTeams for each of them
			playerList.clear();
			for(int i = 1; i<=4; i++)
			{
				if( ((RaymonTour)myActivity.getApplicationContext()).getPlayersByTeamIndex(i, tId).getPlayers().size() > 0)
					playerList.add(((RaymonTour)myActivity.getApplicationContext()).getPlayersByTeamIndex(i, tId));
				
			}
		}
		
		GolfTournament gt = ((RaymonTour)myActivity.getApplicationContext()).getTournamentbyIndex(tId);
		// Evaluate Game&Mode to set correct score lists
		switch(gt.getTournamentGame())
		{
			case GolfTournament.INDIVIDUAL:
			case GolfTournament.BEST_BALL:
			case GolfTournament.FOUR_SOME:
			case GolfTournament.GREEN_SOME:
			case GolfTournament.BLOOD_SOME:
			case GolfTournament.SCRAMBLE:
			break;
		}
		
		switch(gt.getTournamentMode())
		{
			case GolfTournament.POINTS_TOUR:
			case GolfTournament.STROKE_TOUR:
				layoutId = R.layout.listview_scoreedit_row;
				break;
			case GolfTournament.POINTS_TOUR_MATCH:
			case GolfTournament.STROKE_TOUR_MATCH:
				layoutId = R.layout.listview_matchedit_view;
				break;
			default:
				layoutId = R.layout.listview_scoreedit_row;
		}
		
		if(getArguments().getInt(ARG_SECTION_NUMBER) == 19)
			layoutId = R.layout.listview_cl1sedit_view;
		

		
		Bundle args = new Bundle();
        args.putInt("TournamentId", tId);
        args.putInt("HoleId", holeNr);
		
		ListView lview = new ListView(getActivity());
		ArrayAdapter<GolfTeam> ap = new ScoreEditAdapter(myActivity,layoutId,playerList,args);
		lview.setAdapter(ap);

		
		return lview;

	}
	
	

	
}
