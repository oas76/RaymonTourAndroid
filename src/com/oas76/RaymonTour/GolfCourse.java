package com.oas76.RaymonTour;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

public class GolfCourse {
	
	final static int LENGTH=2;
	final static int INDEX=1;
	final static int PAR=0;
	
	private int	gID = 0;
	private String gName="";
	private String gTee="";
	private String gImgUrl="";
	private int gSlope=0;
	private double gValue=0;
	private int gPar=0;
	private int gLength=0;
	private int gHoles=18;
	
	private int gTable[][];
	private ArrayList<String> gHoleNames;
	
	
	public GolfCourse(int id)
	{
		gID = id;
		gTable = new int[18][3];
		gHoleNames = new ArrayList<String>();
		
	}
	
	public GolfCourse(int id, String name){
		this(id);
		this.gName = name;
	}
	
	
		

	//input is handicap*10
	public int getShotsGiven(int boosthandicap){
		double temp = 0;
		temp = boosthandicap*gSlope/1130.0f;
		
		return (int)Math.round(temp + (gValue-gPar));
	}
	
	public int getHandicapScore(int boosthandicap, int score, int hole)
	{
		int iGivenShots = getShotsGiven(boosthandicap);
		int iFullRange = iGivenShots/18;
		int iOverRange = iGivenShots%18;
		
		if(iOverRange >= 0)
		{
			if(gTable[hole-1][INDEX]<= iOverRange)
				++iFullRange;
		}
		// Negative Handicap
		else
			if(((gTable[hole-1][INDEX]) - 19) >= iOverRange)
				iFullRange--;
		return (score-iFullRange);
	}
	
	public int getHandicapPoints(int boosthandicap, int score, int hole)
	{
		int iGivenShots = getShotsGiven(boosthandicap);
		int iFullRange = iGivenShots/18;
		int iOverRange = iGivenShots%18;
		int iRes = 2;
		int iScore = 0;
		
		if(iOverRange >= 0)
		{
			if(gTable[hole-1][INDEX]<= iOverRange)
				++iFullRange;
		}
		// Negative Handicap
		else
			if(((gTable[hole-1][INDEX]) - 19) >= iOverRange)
				iFullRange--;
		
		iScore = (score-iFullRange);
		
		// Par
		if(iScore - gTable[hole-1][PAR] == 0)
			iRes=2;
		// Bogey
		else if(iScore - gTable[hole-1][PAR] == 1)
			iRes=1;
		// Doubble and Larger
		else if(iScore - gTable[hole-1][PAR] > 1)
			iRes=0;
		// Birdie
		else if(gTable[hole-1][PAR] - iScore == 1)
			iRes=3;
		//  Eagle
		else if(gTable[hole-1][PAR] - iScore == 2)
			iRes=4;
		//  Albatross
		else if(gTable[hole-1][PAR] - iScore == 3)
			iRes=5;
		//  Doubble Albatross
		else if(gTable[hole-1][PAR] - iScore == 4)
			iRes=6;
		
		return iRes;		
		
	}
	
	public int getHoleIndex(int hole){
		return gTable[hole-1][INDEX];
	}
	
	public int getHolePar(int hole){
		return gTable[hole-1][PAR];
	}
	
	public int getHoleLength(int hole){
		return gTable[hole-1][LENGTH];
	}
	
	public String getHoleName(int hole){
		if(gHoleNames.size() >= hole)
			return gHoleNames.get(hole-1);
		else
			return " ";
	}
	
	public String getCourceName(){
		return gName;
	}
	
	public String getCourceTee(){
		return gTee;
	}
	
	public int getCourcePar(){
		return gPar;
	}
	
	public double getCourceValue(){
		return gValue;
	}
	
	public int getCourceSlope(){
		return gSlope;
	}
	
	
	public int getCourceLength(){
		return gLength;
	}

    public void setCourceHoleIndex(int[] indexArray){
    	for(int i = 0;i<18;i++)
    	{
    	   	gTable[i][INDEX] = indexArray[i];
    	}
    }
    
    public void setCourceHolePar(int[] indexArray){
    	for(int i = 0;i<18;i++)
    	   	gTable[i][PAR] = indexArray[i];
    }
    
    public void setCourceHoleLength(int[] indexArray){
    	for(int i = 0;i<18;i++)
    	   	gTable[i][LENGTH] = indexArray[i];
    }
    
    public void setCourceHoleNames(ArrayList<String> nameArray){
    	for(int i = 0;i<18&&i<nameArray.size();i++)
    	{
    	   	gHoleNames.add(i,nameArray.get(i));
    	}
    }
    
    public void setCourceName(String holename)
    {
    	gName = holename;
    }
    
    public void setCourceLength(int length)
    {
    	gLength = length;
    }

	public void setCourceTee(String tee) 
	{
		gTee = tee;		
	}

	public void setCourcePar(int par) 
	{
		gPar = par;
		
	}

	public void setCourceSlope(int slope)
	{
		gSlope = slope;
		
	}

	public void setNrOfHoles(int nrOfHoles) 
	{
		gHoles = nrOfHoles;
		
	}

	public void setCourceValue(double value) 
	{
		gValue = value;
		
	}

	public void setHolePar(int hole, int par) 
	{
		gTable[hole-1][PAR] = par;
	}
	
	public void setHoleIndex(int hole, int index) 
	{
		gTable[hole-1][INDEX] = index;
	}
	
	public void setHoleLength(int hole, int length) 
	{
		gTable[hole-1][LENGTH] = length;
	}
	
	public void setHoleName(int hole, String name) 
	{
		while(gHoleNames.size() < hole)
		{
			gHoleNames.add(" ");
		}
		gHoleNames.set(hole-1, name);
	}

	public int getCourceID() 
	{
		return gID;
	}
	
	@Override
	public String toString()
	{
		return this.getCourceName();
	}
	
	public int[] getTotalGameScore(Context c, GolfPlayer player, GolfTournament tournament)
	{
		int res[] = new int[2];
		ContentResolver cr = c.getContentResolver();
		Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES, 
								null,
								TourContentProvider.KEY_PLAYER_ID + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=?",
								new String[]{String.valueOf(player.getPlayerID()),String.valueOf(tournament.getTournamentID())},
								null);
		if(cur != null)
		{
			while(cur.moveToNext())
			{
				int hole = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NR));
				int score = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_SCORE));
				res[0] += score;
				res[1] += getGameScore(hole, score, player, tournament);
			}
		}
		
		return res;
	}
	
	
	
	

	public int getGameScore(int holenr, int score, GolfPlayer golfPlayer, GolfTournament tournament) {
		int res = 0;
		
		if(score == 0)
			return 0;
		
		if(tournament.getCappedStroke())
		{
			score = Math.min(score, (getHolePar(holenr) + 5));
		}
		
		switch(tournament.getTournamentMode())
		{
		case GolfTournament.STROKE_TOUR:
			if(tournament.getTournamentHandicaped())
				res = getHandicapScore(golfPlayer.getPlayerBoostedHandicap(), score, holenr);
			else
				res = score;
			break;			
		case GolfTournament.POINTS_TOUR:
			if(tournament.getTournamentHandicaped())
				res = getHandicapPoints(golfPlayer.getPlayerBoostedHandicap(), score, holenr);
			else
				res = getHandicapPoints(0, score, holenr);
			break;
		default:
			res = score;
		}
		return res;
	}
	
	public int[] getTotalMatchScore(Context c, ArrayList<GolfPlayer> players, GolfTournament tournament)
	{
		int[] res = new int[players.size()];
		int i = 0;
		ContentResolver cr = c.getContentResolver();
		Cursor cur = null;
		
		for(GolfPlayer player : players)
		{
			
			cur = cr.query(TourContentProvider.CONTENT_URI_SCORES, 
							null,
						    TourContentProvider.KEY_PLAYER_ID + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=?",
							new String[]{String.valueOf(player.getPlayerID()),String.valueOf(tournament.getTournamentID())},
							null);
			if(cur != null)
			{
				while(cur.moveToNext())
				{
					int score = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_SCORE));
					res[i] += score;
				}
			}
			cur.close();
			i++;
			
		}
		return res;
		
	}
	
	public int[] getTotalGameTeamScore(Context c, GolfTeam team, GolfTournament tournament)
	{
		int res[] = new int[2];
		GolfPlayer standIn = new GolfPlayer(team.getPlayers().get(0).getPlayerID());
		standIn.setPlayerHandicap(team.getTeamHandicap());
		
		
		ContentResolver cr = c.getContentResolver();
		Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES, 
								null,
								TourContentProvider.KEY_PLAYER_ID + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=?",
								new String[]{String.valueOf(standIn.getPlayerID()),String.valueOf(tournament.getTournamentID())},
								null);
		if(cur != null)
		{
			while(cur.moveToNext())
			{
				int hole = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NR));
				int score = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_SCORE));
				res[0] += score;
				res[1] += getGameScore(hole, score, standIn, tournament);
			}
		}
		
		return res;
	}
	
	
	
	public int getGameTeamScore(int holenr,int score, GolfTeam team, GolfTournament tournament) {
		
		GolfPlayer standIn = new GolfPlayer(team.getPlayers().get(0).getPlayerID());
		standIn.setPlayerHandicap(team.getTeamHandicap());
		
		return getGameScore(holenr, score, standIn, tournament);
	}
	
	
    
    
}
