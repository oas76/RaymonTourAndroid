package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GolfTournamentAdapter extends ArrayAdapter<GolfTournament> {

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<GolfTournament> data = null;
	    
	    public GolfTournamentAdapter(Context context, int layoutResourceId, ArrayList<GolfTournament> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        TournamentHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new TournamentHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (TournamentHolder)row.getTag();
	        }
	        
	        GolfTournament tournament = data.get(position);
	        holder.txtTitle.setText(tournament.getTournamentName());
	        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
	        
	        return row;
	    }
	    
	    static class TournamentHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	    }
	}
