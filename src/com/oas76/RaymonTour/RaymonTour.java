package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.app.Application;

public class RaymonTour extends Application {
	
	private ArrayList<GolfCourse> courselist;
	private ArrayList<GolfPlayer> playerlist;
	private ArrayList<Tour> tourlist;
	private ArrayList<GolfTournament> tournamnetlist;
	
	public RaymonTour()
	{
		this.courselist = new ArrayList<GolfCourse>();
		this.playerlist = new ArrayList<GolfPlayer>();
		this.tourlist = new ArrayList<Tour>();
		this.tournamnetlist = new ArrayList<GolfTournament>();
	}
	
	
	public ArrayList<GolfCourse> getCourselist() {
		return courselist;
	}
	public void setCourselist(ArrayList<GolfCourse> courselist) {
		this.courselist = courselist;
	}
	public ArrayList<GolfPlayer> getPlayerlist() {
		return playerlist;
	}
	public void setPlayerlist(ArrayList<GolfPlayer> playerlist) {
		this.playerlist = playerlist;
	}
	public ArrayList<Tour> getTourlist() {
		return tourlist;
	}
	public void setTourlist(ArrayList<Tour> tourlist) {
		this.tourlist = tourlist;
	}
	public ArrayList<GolfTournament> getTournamnetlist() {
		return tournamnetlist;
	}
	public void setTournamnetlist(ArrayList<GolfTournament> tournamnetlist) {
		this.tournamnetlist = tournamnetlist;
	}
	

    public GolfTournament getTournamentbyIndex(int int1) {
		for(GolfTournament gt : this.getTournamnetlist())
		{
			if(gt.getTournamentID() == int1)
				return gt;
		}
		return null;
	}


	// Access database for better result
	public GolfCourse getCoursebyIndex(int int1) {
		
		for(GolfCourse gc : this.getCourselist())
		{
			if(gc.getCourceID() == int1)
				return gc;
		}
		return null;
	}
	
	// Access database for better result
	public GolfPlayer getPlayerbyIndex(int int1) {
		for(GolfPlayer gp : this.getPlayerlist())
		{
			if(gp.getPlayerID() == int1)
				return gp;
		}
		return null;
	}

}
