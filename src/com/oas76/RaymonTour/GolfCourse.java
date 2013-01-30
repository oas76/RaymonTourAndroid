package com.oas76.RaymonTour;

import java.util.ArrayList;

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
		return gHoleNames.get(hole-1);
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
    
    
}
