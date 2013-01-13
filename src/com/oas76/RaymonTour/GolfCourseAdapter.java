package com.oas76.RaymonTour;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GolfCourseAdapter extends ArrayAdapter<GolfCourse> {

	    Context context; 
	    int layoutResourceId;    
	    GolfCourse data[] = null;
	    
	    public GolfCourseAdapter(Context context, int layoutResourceId, GolfCourse[] data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        CourseHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new CourseHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.courseName);
	            holder.txtDetails = (TextView)row.findViewById(R.id.courceDetails);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (CourseHolder)row.getTag();
	        }
	        
	        GolfCourse course = data[position];
	        holder.txtTitle.setText(course.getCourceName());
	        holder.txtDetails.setText("div details ++++");
	        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
	        
	        return row;
	    }
	    
	    static class CourseHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	        TextView txtDetails;
	    }
	}

