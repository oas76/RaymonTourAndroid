package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreEditAdapter extends ArrayAdapter<String> {

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<String> listdata = null;
	    Bundle args = null;
	    GolfCourse course = null;
	    GolfTournament tournament = null;
	    ArrayList<GolfPlayer> gList = null;
	    int holenr = 0;
	    
	    public ScoreEditAdapter(Context context, int layoutResourceId, ArrayList<String> listdata, Bundle args) {
	        super(context, layoutResourceId, listdata);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.listdata = listdata;
	        this.args = args;
	        this.course = getCoursebyIndex(args.getInt("CourseId"));
	        this.tournament = getTournamentbyIndex(args.getInt("TournamentId"));
	        this.holenr = args.getInt("HoleId");
	        this.gList = getPlayersByIndex(listdata);
        
	    }
	    

	    private ArrayList<GolfPlayer> getPlayersByIndex(ArrayList<String> listdata2) 
	    {
	    	ArrayList<GolfPlayer> players = new ArrayList<GolfPlayer>();
	    	for(String str : listdata2)
	    	{
	    		for(GolfPlayer gp : SectionFragment.playerlist)
	    		{
	    			if(gp.getPlayerID() == Integer.valueOf(str))
	    			{
	    				players.add(gp);
	    				break;
	    			}
	    		}
	    	}
	    			
			return players;
		}


		private GolfTournament getTournamentbyIndex(int int1) {
			for(GolfTournament gt : SectionFragment.tournamentlist)
			{
				if(gt.getTournamentID() == int1)
					return gt;
			}
			return null;
		}


		private GolfCourse getCoursebyIndex(int int1) {
			for(GolfCourse gc : SectionFragment.courselist)
			{
				if(gc.getCourceID() == int1)
					return gc;
			}
			return null;
		}


		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        PlayerHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PlayerHolder();
	            holder.txtNic = (TextView)row.findViewById(R.id.score_playerNic);
	            holder.txtTeam = (TextView)row.findViewById(R.id.score_Team);
	            holder.txtScore = (TextView)row.findViewById(R.id.score_individualScore);
	            holder.txtTeamScore = (TextView)row.findViewById(R.id.score_teamScore);
	            holder.editStrokes = (EditText)row.findViewById(R.id.score_scoreInput);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (PlayerHolder)row.getTag();
	        }
	        
	        String player = listdata.get(position);
	        holder.txtNic.setText(gList.get(position).getNick());
	        //holder.txtDetails.setText("HC: " + String.valueOf(player.getPlayerHC()));
	        
	        
	        return row;
	    }
	    
	    static class PlayerHolder
	    {
	        TextView txtNic;
	        TextView txtTeam;
	        TextView txtScore;
	        TextView txtTeamScore;
	        EditText editStrokes;
	    }
	}
