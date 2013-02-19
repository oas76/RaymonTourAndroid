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

public class GolfPlayerAdapter extends ArrayAdapter<GolfPlayer> {

	    Context context; 
	    int layoutResourceId;    
	    GolfPlayer data[] = null;
	    ArrayList<GolfPlayer> listdata = null;
	    
	    public GolfPlayerAdapter(Context context, int layoutResourceId, ArrayList<GolfPlayer> listdata) {
	        super(context, layoutResourceId, listdata);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.listdata = listdata;
	    }
	    

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = null;
	        PlayerHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PlayerHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.courseName);
	            holder.txtDetails = (TextView)row.findViewById(R.id.courceDetails);
	            
	            row.setTag(holder);
	        }
	
	        GolfPlayer player = listdata.get(position);
	        holder.txtTitle.setText(player.getNick());
	        holder.txtDetails.setText("HC: " + String.valueOf(player.getPlayerHC()));
	        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
	        
	        return row;
	    }
	    
	    static class PlayerHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	        TextView txtDetails;
	    }
	}

