package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

public class ScoreEditAdapter extends ArrayAdapter<GolfTeam> {

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<GolfTeam> listdata = null;
	    Bundle args = null;
	    GolfCourse course = null;
	    GolfTournament tournament = null;
	    int holenr = 0;
	    Cursor cur = null;
	    int score = 0;
	    
	    public ScoreEditAdapter(Context context, int layoutResourceId, ArrayList<GolfTeam> playerList, Bundle args) {
	        super(context, layoutResourceId, playerList);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.listdata = playerList;
	        this.args = args;
	    }
	    
	    private ArrayList<GolfPlayer> getFirstPlayerFromTeams()
	    {
	    	ArrayList<GolfPlayer> ret_list = new ArrayList<GolfPlayer>();
	    	for(GolfTeam gt : listdata)
	    	{
	    		ret_list.add(gt.getPlayers().get(0));
	    	}
	    	return ret_list;
	    }
	    
	    
		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = null;
	        PlayerHolder holder = null;
	        int score_id = -1;
	        int team_id = 0;
	        int color = 0;
	        int smart_id = 0 ;
	        int[] total_score = null;
	        this.tournament = ((RaymonTour)context.getApplicationContext()).getTournamentbyIndex(args.getInt("TournamentId"));
	        this.holenr = args.getInt("HoleId");
	        this.course = ((RaymonTour)context.getApplicationContext()).getCoursebyIndex(this.tournament.getTournamentGolfCourceID());


	        
	        ContentResolver cr = getContext().getContentResolver();
	        cur = cr.query(TourContentProvider.CONTENT_URI_SCORES,
	        				null,
	        				TourContentProvider.KEY_HOLE_NR + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=? AND " + TourContentProvider.KEY_PLAYER_ID + "=?",
	        				new String[]{String.valueOf(holenr), String.valueOf(tournament.getTournamentID()), String.valueOf(listdata.get(position).getPlayers().get(0).getPlayerID())},
	        				null);
	        if(cur != null && cur.getCount() == 1)
	        {
	        	// Correct score entry found
	        	cur.moveToNext();
	        	int score_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_SCORE);
	        	int score_id_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
	        	score = cur.getInt(score_index);
	        	score_id = cur.getInt(score_id_index);	        
	        	
		        int team_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_TEAM_ID);
		        team_id = cur.getInt(team_index);	     
		        cur.close();
	        }
	        
	        switch(team_id)
	        {
	        case 0:
	        	color = Color.TRANSPARENT;
	        	break;
	        case 1:
	        	color = Color.RED;
	        	break;
	        case 2:
	        	color = Color.GREEN;
	        	break;
	        case 3:
	        	color = Color.BLUE;
	        	break;
	        case 4:
	        	color = Color.YELLOW;
	        	break;
	        }
	        
	        // Set total current score
	        if(layoutResourceId == R.layout.listview_scoreedit_row)
	        	total_score = course.getTotalGameTeamScore(context, listdata.get(position), tournament);
	        else if(layoutResourceId == R.layout.listview_matchedit_view)
	        	total_score = course.getTotalMatchScore(context,getFirstPlayerFromTeams(), tournament);
        	
	        	
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PlayerHolder();
	            holder.txtNic = (TextView)row.findViewById(R.id.score_playerNic);
	            holder.teamIndicator = (View)row.findViewById(R.id.team_indicator);
	            holder.teamIndicator.setBackgroundColor(color);

	            
	            if(layoutResourceId == R.layout.listview_scoreedit_row)
	            {
	            	holder.txtScore = (TextView)row.findViewById(R.id.score_individualScore);
	            	holder.editStrokes = (EditText)row.findViewById(R.id.score_scoreInput);
	            	holder.editStrokes.setOnFocusChangeListener(new MyOnFocusChangeListener(score_id));	
	            }
	            else if(layoutResourceId == R.layout.listview_matchedit_view)
	            {
	            	holder.matchCheck = (CheckBox)row.findViewById(R.id.score_matchInput);
	            	holder.matchCheck.setOnCheckedChangeListener(new MyOnCheckChangeListener(score_id,row));
	            	holder.txtScore = (TextView)row.findViewById(R.id.score_status);
	            }
	            else if(layoutResourceId == R.layout.listview_cl1sedit_view)
	            {
	            	if(team_id == 0)
	            		smart_id = listdata.get(position).getPlayers().get(0).getPlayerID();
	            	else
	            		smart_id = team_id;
	            		
	            	holder.longestCheck = (CheckBox)row.findViewById(R.id.score_longest);
	            	holder.longestCheck.setOnCheckedChangeListener(new OnScoreCheckChangeListener(context,tournament.getTournamentID(),smart_id,"Longest"));
	            	if(tournament.getWinnerLongest() == smart_id )
	            		holder.longestCheck.setChecked(true);
	            	else
	            		holder.longestCheck.setChecked(false);
	            	holder.closestCheck = (CheckBox)row.findViewById(R.id.score_closest);
	            	holder.closestCheck.setOnCheckedChangeListener(new OnScoreCheckChangeListener(context,tournament.getTournamentID(),smart_id,"Closest"));
	            	if(tournament.getWinnerClosest() == smart_id)
	            		holder.closestCheck.setChecked(true);
	            	else
	            		holder.closestCheck.setChecked(false);
	            	holder.putCheck = (CheckBox)row.findViewById(R.id.score_1Put);
	            	holder.putCheck.setOnCheckedChangeListener(new OnScoreCheckChangeListener(context,tournament.getTournamentID(),smart_id,"Put"));
	            	if(tournament.getWinnerPut() == smart_id)
	            		holder.putCheck.setChecked(true);
	            	else
	            		holder.putCheck.setChecked(false);
	            	holder.sneakCheck = (CheckBox)row.findViewById(R.id.score_snake);
	            	holder.sneakCheck.setOnCheckedChangeListener(new OnScoreCheckChangeListener(context,tournament.getTournamentID(),smart_id,"Snake"));
	            	if(tournament.getWinnerSneak() == smart_id)
	            		holder.sneakCheck.setChecked(true);
	            	else
	            		holder.sneakCheck.setChecked(false);
	  
	            }
	            
	            
	            row.setTag(holder);
	        }
	        
	        holder.txtNic.setText(listdata.get(position).getTeamNic() + ((tournament.getTournamentHandicaped())? "  given:" + String.valueOf(course.getShotsGiven(listdata.get(position).getTeamHandicap())) : ""));
	  
	        if(score > 0)
	        {
	        	if(layoutResourceId == R.layout.listview_scoreedit_row)
	        	{
	        		holder.editStrokes.setText(String.valueOf(score));
	        		int reg_score = 0;
	        		if(team_id > 0)
	        			reg_score = course.getGameTeamScore(holenr, score,listdata.get(position), tournament);
	        		else
	        			reg_score = course.getGameScore(holenr,score,listdata.get(position).getPlayers().get(0),tournament);
	        		
	        		
	        		holder.txtScore.setText(String.valueOf(reg_score) + "  " + String.valueOf(total_score[0]) + "/" + String.valueOf(total_score[1]));
	        	}
	        	else if(layoutResourceId == R.layout.listview_matchedit_view)
	        	{
	        		holder.matchCheck.setChecked(true);
	        		String res_txt = getMatchText(position,total_score);
	        		holder.txtScore.setText(res_txt);
	      					
	        	}
	        		
	        	
	        }
	        else
	        {
	  	        if(layoutResourceId == R.layout.listview_scoreedit_row)
	  	        	holder.txtScore.setText("0  " + String.valueOf(total_score[0]) + "/" + String.valueOf(total_score[1]));
	  	    	else if(layoutResourceId == R.layout.listview_matchedit_view)
	  	    	{
	        		holder.matchCheck.setChecked(false);
	        		String res_txt = getMatchText(position,total_score);
	        		holder.txtScore.setText(res_txt);
	      					
	  	    	}
	  	     	
	  	
	  	    }
	        return row;
		}
		
	    
	    private String getMatchText(int position, int[] total_score) {
    		int result = 0;
    		String res_txt = "";
    		
			if(position == 0)
    			result = total_score[0] - total_score[1];
    		else
    			result = total_score[1] - total_score[0];
			
			if(result > 0)
				res_txt = String.valueOf(result) + " UP";
			else if(result < 0)
				res_txt = String.valueOf((result*-1)) + " DOWN";
			else
				res_txt = "EVEN";
			return res_txt;
		}


		static class PlayerHolder
	    {
	        TextView txtNic;
	        TextView txtScore;
	        EditText editStrokes;
	        CheckBox matchCheck;
	        CheckBox longestCheck;
	        CheckBox closestCheck;
	        CheckBox putCheck;
	        CheckBox sneakCheck;
	        View teamIndicator;
	    }
	}


	class OnScoreCheckChangeListener implements OnCheckedChangeListener
	{
		int teamid;
		int tournamentid;
		String game;
		Context con;
	
		public OnScoreCheckChangeListener(Context context, int tournament_id,int team_id, String game)
		{
			super();
			this.teamid = team_id;
			this.tournamentid = tournament_id;
			this.game = game;
			this.con = context;
		}

		@Override
		public void onCheckedChanged(CompoundButton xx,
				boolean isChecked) {
			
			ContentResolver cr = con.getApplicationContext().getContentResolver();
			ContentValues value = new ContentValues();
			
			if(game.equals("Longest"))
			{
				if(isChecked)
					value.put(TourContentProvider.KEY_WINNER_LONGEST, teamid);
				else
					value.put(TourContentProvider.KEY_WINNER_LONGEST,-1);
			}
			else if(game.equals("Closest"))
			{
				if(isChecked)
					value.put(TourContentProvider.KEY_WINNER_CLOSEST, teamid);
				else
					value.put(TourContentProvider.KEY_WINNER_CLOSEST,-1);
			}
			else if(game.equals("Put"))
			{
				if(isChecked)
					value.put(TourContentProvider.KEY_WINNER_PUT, teamid);
				else
					value.put(TourContentProvider.KEY_WINNER_PUT,-1);
			}
			else if(game.equals("Snake"))
			{
				if(isChecked)
					value.put(TourContentProvider.KEY_WINNER_SNEAK, teamid);
				else
					value.put(TourContentProvider.KEY_WINNER_SNEAK,-1);
			}
			
			cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TOURNAMENTS,String.valueOf(tournamentid)),
					         value,
					         null,
					         null);


		}


	}

	class MyOnCheckChangeListener implements OnCheckedChangeListener
	{
		int index;
		View v;
		
		public MyOnCheckChangeListener(int scoreIndex, View v)
		{
			super();
			this.index = scoreIndex;
			this.v = v;
		}

		@Override
		public void onCheckedChanged(CompoundButton xx,
				boolean isChecked) {
			
			ContentResolver cr = v.getContext().getContentResolver();
			
			ContentValues values = new ContentValues();
			values.put(TourContentProvider.KEY_GOLF_SCORE, (isChecked?1:0));
			int update = cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_SCORES,String.valueOf(index)),
					  values, 
					  null, 
					  null);

		}


	}

	class MyOnFocusChangeListener implements OnFocusChangeListener
	{
		int index;
		
		public MyOnFocusChangeListener(int scoreIndex)
		{
			super();
			this.index = scoreIndex;
			
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			ContentResolver cr = v.getContext().getContentResolver();
			ContentValues values = new ContentValues();
			int stored_value = 0;
			try{
				stored_value = Integer.parseInt(((EditText)v).getText().toString());
			}
			catch(Exception e){
				return;
			}
			values.put(TourContentProvider.KEY_GOLF_SCORE, String.valueOf(stored_value));
			cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_SCORES,String.valueOf(index)),
					  values, 
					  null, 
					  null);
			
			
			
			
			
			
		}
		
	}
