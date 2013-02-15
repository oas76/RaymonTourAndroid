package com.oas76.RaymonTour;

import java.util.ArrayList;
import java.util.List;

import com.oas76.RaymonTour.GolfPlayerAdapter.PlayerHolder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultAdapter extends ArrayAdapter<GolfPlayer> {
    Context context; 
    int layoutResourceId;    
    ArrayList<GolfPlayer> listdata = null;
    Bundle args = null;

	public ResultAdapter(Context context, int resource, ArrayList<GolfPlayer> objects, Bundle args) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.listdata = objects;
		this.args = args;
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        PlayerHolder holder = null;
        
        int tId = args.getInt(ScoreInputFragment.TOURNAMENT_ID);
        GolfTournament gt = ((RaymonTour)context.getApplicationContext()).getTournamentbyIndex(tId);
        int gId = gt.getTournamentGolfCourceID();
        GolfCourse gc = ((RaymonTour)context.getApplicationContext()).getCoursebyIndex(gId);
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new PlayerHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.courseName);
            holder.txtDetails = (TextView)row.findViewById(R.id.courceDetails);
            holder.txtScore = (TextView)row.findViewById(R.id.resultScore);
            
            row.setTag(holder);
        }
        
        GolfPlayer player = listdata.get(position);
        holder.txtTitle.setText(player.getNick());
        holder.txtDetails.setText("");
        holder.txtScore.setText(String.valueOf(player.getTemp_stroke()));
        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
        
        return row;
    }
    
    static class PlayerHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtDetails;
        TextView txtScore;
    }
}


