package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GolfPlayerPickerAdapter extends ArrayAdapter<GolfPlayer> {

	    Context context; 
	    int layoutResourceId;    
	    GolfPlayer player = null;
	    int color = 0;
	
	    
	    public GolfPlayerPickerAdapter(Context context, int layoutResourceId) {
	        super(context, layoutResourceId, TournamentEdit.mSelectedPlayers);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;

	    }
	    

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
		    PlayerHolder holder = null;
	        GolfPlayer player = TournamentEdit.mSelectedPlayers.get(position);
	        
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	            
	            holder = new PlayerHolder();
	            holder.txtTitle = (TextView)row.findViewById(R.id.player_name);
	            holder.teamIndex = (Spinner)row.findViewById(R.id.team_picker);

	            holder.teamIndex.setSelection(TournamentEdit.mSelectedPlayers.get(position).getTeamIndex());
	            holder.teamIndex.setOnItemSelectedListener(new MyOnItemSelectedListener(position,(Activity)context,row,color));
	            row.setTag(holder);
	        }
	        else
	        {
	            holder = (PlayerHolder)row.getTag();
	        }
	        
	        holder.txtTitle.setText(player.getNick() + " : " + String.valueOf(player.getTeamIndex()));
	        return row;
	    }
	    
	    static class PlayerHolder
	    {
	        TextView txtTitle;
	        Spinner teamIndex;
	        int tIndex;

	    }
	}

	class MyOnItemSelectedListener implements OnItemSelectedListener
	{
		int parent_position = 0;
		Activity activity = null;
		View view = null;
		int color = 0;
		
		public MyOnItemSelectedListener(int position1, Activity activity, View view, int color)
		{
			super();
			this.parent_position = position1;
			this.activity = activity;
			this.color = color;
			this.view = view;
		}
		
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TournamentEdit.mSelectedPlayers.get(parent_position).setTeamIndex(arg2);
			//parentView.findViewById(R.id.team_indicator).setBackgroundColor(color);
			// re-drawadapter.notifyDataSetChanged();
			//Runnable run = new Runnable(){
			//     public void run(){
			//    	 view.invalidate();;
			//     }
			//};
			//activity.runOnUiThread(run);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

