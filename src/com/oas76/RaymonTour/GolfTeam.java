package com.oas76.RaymonTour;

import java.util.Arrays;
import java.util.ArrayList;

public class GolfTeam {
	
	private ArrayList<GolfPlayer> players = null;
	

	public GolfTeam(ArrayList<GolfPlayer> teamPlayers)
	{
		this.setPlayers(teamPlayers);

	}
	
	
	public int getTeamHandicap()
	{
		
		int[] boostedHandicap = new int[players.size()];
		double tempHC = 0;
		double restHC = 0;
		int counter = 0;
		
		for (GolfPlayer player : players) {
			boostedHandicap[counter] = player.getPlayerBoostedHandicap();
			counter++;
		}
			
		Arrays.sort(boostedHandicap);
		
		if(boostedHandicap.length > 1)
		{
			tempHC = ((double)boostedHandicap[0])*0.75;
			for(int y = 1; y < boostedHandicap.length;y++)
			{
				restHC += boostedHandicap[y];
			}
			restHC = (restHC/((double)(boostedHandicap.length-1)))*0.25;
			tempHC += restHC;
			
		}
		else if(boostedHandicap.length == 1)
			tempHC = boostedHandicap[0];
		
		return (int)(tempHC + 0.5);
		
	}
	
	
	public String getTeamNic() {
		String ret = "";
		for(GolfPlayer gp : players)
		{
			ret += gp.getNick() + " ";
			
		}
		return ret;
	}


	public ArrayList<GolfPlayer> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<GolfPlayer> players) {
		this.players = players;
	}
	
	/*public int getTeamHCScore(ArrayList<GolfPlayer> players, GolfCourse cource)
	{
		int tempHC = this.getTeamHandicap(players);
		int lowScore = 100;
		int tmp_score = 0;
		for(GolfPlayer golfPlayer : players){
			golfPlayer.setTeamScore(golfPlayer.getTemp_stroke(), cource, tempHC);
			lowScore = Math.min(lowScore, golfPlayer.getTeamScore(hole));
		}
		return lowScore;

	}
	
	public int getTeamHCPoints(ArrayList<GolfPlayer> players, GolfCource cource)
	{
		int tempHC = getTeamHandicap(players);
		int highPoint = 0;
		
		for(GolfPlayer golfPlayer : players){
			golfPlayer.setTeamPoints(hole, golfPlayer.getStroke(hole), cource, tempHC);
			highPoint = Math.max(highPoint, golfPlayer.getTeamPoints(hole));
		}
		return highPoint;
		

	}
	*/
	
	
	
	

}
