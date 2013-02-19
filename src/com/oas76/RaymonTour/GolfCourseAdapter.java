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

public class GolfCourseAdapter extends ArrayAdapter<GolfCourse> {

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<GolfCourse> data = null;
	    

	    public GolfCourseAdapter(Context context2, int listviewCourseRow,
				ArrayList<GolfCourse> courselist) {
	        super(context2, listviewCourseRow, courselist);
	        this.layoutResourceId = listviewCourseRow;
	        this.context = context2;
	        this.data = courselist;
		}

		@Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = null;
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

	        GolfCourse course = data.get(position);
	        holder.txtTitle.setText(course.getCourceName() + " ( " + course.getCourceTee() + " )");
	        holder.txtDetails.setText("Par: " + Integer.toString(course.getCourcePar()) + " Length: " + String.valueOf(course.getCourceLength()) + " C.R/Slope: " + String.valueOf(course.getCourceValue()) + "/" + course.getCourceSlope());
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

