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

public class DropDownAdapter extends ArrayAdapter<CharSequence> {

	    Context context; 
	    int layoutResourceId;    
	    ArrayList<CharSequence> listdata = null;
	    
	    
	    public DropDownAdapter(Context context, int layoutResourceId, ArrayList<CharSequence> listdata) {
	        super(context, layoutResourceId, listdata);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.listdata = listdata;
	    }
	    

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ItemHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new ItemHolder();
	            holder.txtTitle = (TextView)row.findViewById(R.id.list_text);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ItemHolder)row.getTag();
	        }
	        
	        CharSequence str = listdata.get(position);
	        holder.txtTitle.setText(str.toString());
	        
	        return row;
	    }
	    

	    @Override
	    public View getDropDownView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ItemHolder holder = null;
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new ItemHolder();
	            holder.txtTitle = (TextView)row.findViewById(R.id.list_text);
	            
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (ItemHolder)row.getTag();
	        }
	        
	        CharSequence str = listdata.get(position);
	        holder.txtTitle.setText(str.toString());
	        
	        return row;
	    }
	    
	    static class ItemHolder
	    {
	        TextView txtTitle;	        
	    }
	}