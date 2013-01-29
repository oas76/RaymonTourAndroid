package com.oas76.RaymonTour;

public class GolfPlayer {
	private final static int STROKE=0;
	private final static int SCORE=1;
	private final static int TEAMSCORE=2;
	
	private int pID = 0;
	private String pName="";
	private String pNick="";
	private String pImgUrl="";
	private int pBoostedHandicap=0;
	private int pWinnings=0;
	private int[][] pScore = null;
	private int pTeamIndex = 0;

	
	public GolfPlayer(int id) {
		pID = id;
		pScore = new int[18][3];		
	}
	
	public GolfPlayer(int id, String nic) {
		this(id);
		this.pNick = nic;
	}
	

	public void setPlayerHandicap(int boostedHandicap)
	{
		pBoostedHandicap = boostedHandicap;
	}
	
	public String getPlayerName(){
		return pName;
	}
	
	public String getNick(){
		return pNick;
	}
	
	public int getPlayerBoostedHandicap(){
		return pBoostedHandicap;
	}
	
	public double getPlayerHC(){
		return ((double)pBoostedHandicap/10.0);
	}
	
	public String getImgUrl(){
		return pImgUrl;
	}
	
	public int getPlayerID(){
		return pID;
	}
	
	public int getPlayerWinnings(){
		return pWinnings;
	}
	
	public void setTeamIndex(int index){
		pTeamIndex = index;
	}
	
	public int getTeamIndex(){
		return pTeamIndex;
	}
	/*
	
	public void setScore(int hole, int stroke, GolfCource cource)
	{
		pScore[hole-1][STROKE] = stroke;
		pScore[hole-1][SCORE] = cource.getHandicapScore(pBoostedHandicap, stroke, hole);
	}
	
	public void setTeamScore(int hole, int stroke, GolfCource cource, int boostedhandicap)
	{
		pScore[hole-1][STROKE] = stroke;
		pScore[hole-1][SCORE] = cource.getHandicapScore(pBoostedHandicap, stroke, hole);
		pScore[hole-1][TEAMSCORE] = cource.getHandicapScore(boostedhandicap, stroke, hole);		
	}
	
	public void setTeamPoints(int hole, int stroke, GolfCource cource, int boostedhandicap)
	{
		pScore[hole-1][STROKE] = stroke;
		pScore[hole-1][SCORE] = cource.getHandicapPoints(pBoostedHandicap, stroke, hole);
		pScore[hole-1][TEAMSCORE] = cource.getHandicapPoints(boostedhandicap, stroke, hole);
	}
	
	public int getPoints(int hole){
		return pScore[hole-1][SCORE];
	}
	
	public int getScore(int hole){
		return pScore[hole-1][SCORE];
	}
	
	public int getTeamScore(int hole){
		return pScore[hole-1][TEAMSCORE];
	}
	
	public int getTeamPoints(int hole){
		return pScore[hole-1][TEAMSCORE];
	}
	
	public int getStroke(int hole){
		return pScore[hole-1][STROKE];
	}
	*/

	public void setPlayerName(String name)
	{
		pName = name;
	}

	public void setPlayerNick(String nick) 
	{
		pNick = nick;
	}

	public void setPlayerWinnings(int winnings)
	{
		pWinnings = winnings;
	}

	public void setPlayerHC(double hc)
	{
		pBoostedHandicap = (int)(hc*10.0);
	}

	public void setPlayerImg(String imgUrl) 
	{
		pImgUrl = imgUrl;
		
	}
	
	@Override
	public String toString()
	{
		return pNick;
	}
	
	
	

}
