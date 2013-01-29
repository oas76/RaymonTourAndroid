package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GolfPlayerPickerAdapter extends ArrayAdapter<GolfPlayer> {

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<GolfPlayer> listdata = null;
	    PlayerHolder holder = null;
	    
	    public GolfPlayerPickerAdapter(Context context, int layoutResourceId, ArrayList<GolfPlayer> listdata) {
	        super(context, layoutResourceId, listdata);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.listdata = listdata;
	    }
	    

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PlayerHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.courseName);
	            holder.teamIndex = (Spinner)row.findViewById(R.id.team_picker);
	            holder.teamIndex.setOnItemSelectedListener(new OnItemSelectedListener() {
	                
	            	@Override
	                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	                    holder.tIndex = position;
	                    Toast.makeText((Activity)context, "Selected: " + holder.teamIndex.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
	                }

	                @Override
	                public void onNothingSelected(AdapterView<?> parentView) {
	                    holder.tIndex = 0;
	                }
				});
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (PlayerHolder)row.getTag();
	        }
	        
	        GolfPlayer player = listdata.get(position);
	        holder.txtTitle.setText(player.getNick() + "  "  +  holder.teamIndex.getItemAtPosition(holder.tIndex).toString());
	        holder.imgIcon.setImageResource(R.drawable.ic_launcher);
	        
	        return row;
	    }
	    
	    static class PlayerHolder
	    {
	        ImageView imgIcon;
	        TextView txtTitle;
	        Spinner teamIndex;
	        int tIndex;
	    }
	}

