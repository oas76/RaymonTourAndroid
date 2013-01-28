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

public class GolfTourAdapter extends ArrayAdapter<Tour> {

	    Context context; 
	    int layoutResourceId;    
	    GolfPlayer data[] = null;
	    ArrayList<Tour> listdata = null;
	    
	    
	    public GolfTourAdapter(Context context, int layoutResourceId, ArrayList<Tour> listdata) {
	        super(context, layoutResourceId, listdata);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.listdata = listdata;
	    }
	    

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        TourHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new TourHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.courseName);
	            holder.txtDesc = (TextView)row.findViewById(R.id.courceDetails);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (TourHolder)row.getTag();
	        }
	        
	        Tour tour = listdata.get(position);
	        holder.txtTitle.setText(tour.getTourName());
	        holder.txtDesc.setText(tour.getTourDesc());
	        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
	        
	        return row;
	    }
	    
	    static class TourHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	        TextView txtDesc;
	    }
	}
