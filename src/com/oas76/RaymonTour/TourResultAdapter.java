package com.oas76.RaymonTour;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TourResultAdapter extends ArrayAdapter<GolfPlayer> {
    Context context; 
    int layoutResourceId;    
    ArrayList<GolfPlayer> listdata = null;
    HashMap<Integer,Integer[]> map = null;

	public TourResultAdapter(Context context, int resource, ArrayList<GolfPlayer> objects, HashMap<Integer, Integer[]> map) {
		super(context, resource, objects);
		this.context = context;
		this.layoutResourceId = resource;
		this.listdata = objects;
		this.map = map;
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        PlayerHolder holder = null;
        GolfPlayer player = listdata.get(position);
        
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
        

        holder.txtTitle.setText(player.getNick());
        holder.txtDetails.setText("");
        holder.txtScore.setText(" Winnings: " + String.valueOf((map.get(player.getPlayerID()))[0]) + " Cost: " + String.valueOf((map.get(player.getPlayerID()))[1]));
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