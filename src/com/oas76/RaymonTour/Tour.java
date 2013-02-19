package com.oas76.RaymonTour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class Tour {
	
	private String tName;
	private String tImgUrl = "";
	private String tDesc;
	private int tID;
	

	public Tour(int id)
	{
		tID = id;
	}
	
	
	public String getTourName(){
		return tName;
	}
	
	public String getTourImgUrl(){
		return tImgUrl;
	}
	
	public int getTourID(){
		return tID;
	}
	
	public String getTourDesc(){
		return tDesc;
	}
	
	public void setTourID(int ID)
	{
		tID = ID;
	}
	
	public void setTourName(String name)
	{
		tName = name;
	}
	
	public void setTourImgUrl(String url)
	{
		tImgUrl = url;
	}
	
	public void setTourDesc(String desc)
	{
		tDesc = desc;
	}
	
	@Override
	public String toString()
	{
		return this.getTourName();
	}
	
	public HashMap<Integer, Integer[]> tourWinningsList(Context context)
	{
		HashMap<Integer, Integer[]> map = new HashMap<Integer,Integer[]>();

		ContentResolver cr = context.getContentResolver();
		Cursor cur = cr.query(TourContentProvider.CONTENT_URI_TT,
				              null,
				              TourContentProvider.KEY_TOUR_ID + "=?",
				              new String[]{String.valueOf(this.getTourID())},
				              null);
		if(cur != null)
		{
			while(cur.moveToNext())
			{
				
				int tournament_id = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_ID));
				if(((RaymonTour)context.getApplicationContext()).getTournamentbyIndex(tournament_id) != null)
				{
					HashMap<Integer, Integer[]> tmp_map = ((RaymonTour)context.getApplicationContext()).getTournamentbyIndex(tournament_id).tournamentWinningsList(context);
					Set<Entry<Integer,Integer[]>> set = tmp_map.entrySet();
					for( Entry<Integer, Integer[]> entry : set)
					{
						if(map.get(entry.getKey()) == null)
						{
							map.put(entry.getKey(),entry.getValue());
						}
						else
						{
							map.get(entry.getKey())[0] += entry.getValue()[0];
							map.get(entry.getKey())[1] += entry.getValue()[1];
						}
					}
				}
					
					
					
				
			}
		cur.close();
		}
		
		return map;
	}
	
	
	
	
}
