package com.oas76.RaymonTour;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;

public class GolfTournament {
		
		// Fundamenta scoring mode
		static final int STROKE_TOUR = 1;
		static final int POINTS_TOUR = 2;
		static final int STROKE_TOUR_MATCH = 3;
		static final int POINTS_TOUR_MATCH = 4;
	
		// Game mode
		static final int INDIVIDUAL = 1;
		static final int BEST_BALL  = 2;
		static final int FOUR_SOME  = 3;
		static final int GREEN_SOME = 4;
		static final int BLOOD_SOME = 5;
		static final int SCRAMBLE	= 6;

		
		private int gID = 0;
		private Date gDate = null;
		private int gCourceId = 0;
		private int gNrOfPlayers = 0;
		private int gNrOfTeams = 0;
		private boolean gIndividualCLS3 = false;
		private boolean gHandicaped = true;
		private boolean gCappedStroke = true;
		private int gTourMode = STROKE_TOUR;
		private int gTourGame = INDIVIDUAL;
		private String gTournamentName = "";
		private int gNrOfHoles = 18;
		
		private int gStakesWinn = 0;
		private int gStakesClosest = 0;
		private int gStakesLongest = 0;
		private int gStakesOnePut = 0;
		private int gStakesSnake = 0;
		private int gPurse = 0;
		private String gImgUrl;
		
		private int gWinnerClosest = -1;
		private int gWinnerLongest = -1;
		private int gWinnerPut = -1;
		private int gWinnerSneak = -1;
		
		private boolean gIsOffical = false;
		
		private ArrayList<Integer> gTournamentPlayers = null;
				
		public GolfTournament(int id)
		{
			gID = id;
			gTournamentPlayers = new ArrayList<Integer>();
		}
		
		public GolfTournament(int id, String name)
		{
			this(id);
			this.gTournamentName = name;		
		}
		
		
		public int getTournamentID(){
			return gID;
		}
		
		public void setPlayer(int p_ID)
		{
		 	gTournamentPlayers.add(Integer.valueOf(p_ID));
		}	
		
		public void setInividualCLS3(boolean bool)
		{
			gIndividualCLS3 = bool;
		}
		
		public void setStakes(int win, int closest, int longest, int snake, int onePut)
		{
			gStakesWinn = win;
			gStakesClosest = closest;
			gStakesLongest = longest;
			gStakesOnePut = onePut;
			gStakesSnake = snake;
		}
		
		public void setTouramentName(String name)
		{
			gTournamentName = name;
		}
		
		public void setTournamentGame(int game)
		{
			gTourGame = game;
		}
		
		public void setTournamentMode(int mode)
		{
			gTourMode = mode;
		}

		public String getTournamentName() 
		{
			return gTournamentName;
		}

		public int getTournamentNrHoles() 
		{
			return gNrOfHoles;
		}

		public int getTournamentMode() 
		{
			return gTourMode;
		}

		public int getTournamentGame()
		{
			return gTourGame;	
		}

		public boolean getTournamentHandicaped() 
		{
			return gHandicaped;
		}

		public boolean getTournamentIndivdualCLS3() 
		{
			return gIndividualCLS3;
		}

		public int getTournamentNrOfPlayers() 
		{
			return gNrOfPlayers;
		}

		public int getTournametNrOfTeams()
		{
			return gNrOfTeams;
		}

		public int getTournamentStakes() 
		{
			return gStakesWinn;
		}

		public int getTournamentClosestStakes() 
		{
			return gStakesClosest;
		}

		public int getTournamentLongestStakes() 
		{
			return gStakesLongest;
		}

		public int getTournamentSnakeStakes() 
		{
			return gStakesSnake;
		}

		public int getTournament1PutStakes()
		{
			return gStakesOnePut;
		}

		public int getTournamentSponsorPurse() 
		{
			return gPurse;
		}

		public String getTournamentImgUrl() 
		{
			return gImgUrl;
		}

		public void setTournamentGolfCourceID(int courceid) 
		{
			gCourceId = courceid;
		}
		
		public int getTournamentGolfCourceID() 
		{
			return gCourceId;
		}
		
		public void setTournamentDate(Date date)
		{
			gDate = date;
		}
		
		public Date getTournamentDate()
		{
			return gDate;
		}

		public void setTournamentNrOfHoles(int nrOfHoles) 
		{
			gNrOfHoles = nrOfHoles;
		}

		public void setTournamentHandicapped(int handicaped) 
		{
			gHandicaped = (handicaped == 1);
		}

		public void setTournamentIndividualCLS1(int indCLS1) 
		{
			gIndividualCLS3 = (indCLS1 == 1);
		}

		public void setTournamentNrOfPlayers(int players) 
		{
			gNrOfPlayers = players;
		}

		public void setTournamentNrOfTeams(int teams) 
		{
			gNrOfTeams = teams;
			
		}

		public void setTournamentPurse(int purse) 
		{
			gPurse = purse;
		}

		public void setTournamentImgUrl(String imgUrl) 
		{
			gImgUrl = imgUrl;
		}

		public boolean getCappedStroke() {
			return gCappedStroke;
		}

		public void setCappedStroke(int gCappedStroke) {
			this.gCappedStroke = (gCappedStroke == 1);
		}

		public int getWinnerClosest() {
			return gWinnerClosest;
		}

		public void setWinnerClosest(int gWinnerClosest) {
			this.gWinnerClosest = gWinnerClosest;
		}

		public int getWinnerLongest() {
			return gWinnerLongest;
		}

		public void setWinnerLongest(int gWinnerLongest) {
			this.gWinnerLongest = gWinnerLongest;
		}

		public int getWinnerPut() {
			return gWinnerPut;
		}

		public void setWinnerPut(int gWinnerPut) {
			this.gWinnerPut = gWinnerPut;
		}

		public int getWinnerSneak() {
			return gWinnerSneak;
		}

		public void setWinnerSneak(int gWinnerSneak) {
			this.gWinnerSneak = gWinnerSneak;
		}

		public boolean getIsOffical() {
			return gIsOffical;
		}

		public void setIsOffical(int gIsOffical) {
			this.gIsOffical = (gIsOffical == 1);
		}

		
		public HashMap<Integer,Integer[]> tournamentWinningsList(Context context)
		{
			final int WINNINGS = 0;
			final int COST = 1;
			
			int tId = this.getTournamentID();
			HashMap<Integer, Integer[]> map = new HashMap<Integer, Integer[]>();
			
			// winner is first entry in result list
		    ArrayList<GolfPlayer> resultlist = tournamentResultList(context);
			ArrayList<GolfPlayer> winners = new ArrayList<GolfPlayer>();
			
			int i = 0;
			winners.add(resultlist.get(i));
			while(resultlist.size() > i+1)
			{
				if(resultlist.get(i).getTemp_stroke() == resultlist.get(++i).getTemp_stroke())
					winners.add(resultlist.get(i));
				else
					break;
			}
			
			int nrOfWinners = winners.size();
			int nrOfPlayers = resultlist.size();
			int stakesWin = 0;
			int stakesClose = 0;
			int stakesLong = 0;
			int stakes1Put = 0;
			int stakesSneak = 0;
			int tournamentPurse = 0;
			int winnerLongest = -1;
			int winnerClosest = -1;
			int winnerPut = -1;
			int winnerSneak = -1;
			boolean bIsReg = false;
			
			ContentResolver cr = context.getApplicationContext().getContentResolver();
			Cursor cur = cr.query(TourContentProvider.CONTENT_URI_TOURNAMENTS,
								  null,
								  TourContentProvider.KEY_ID + "=?",
								  new String[]{String.valueOf(this.getTournamentID())},
								  null);
			if(cur != null && cur.getCount() == 1)
			{
				cur.moveToNext();
				bIsReg = (cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_OFFICIAL)) == 0 ? false : true);
				stakesWin = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES));
				stakesLong = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_LONGEST));
				stakesClose = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_CLOSEST));
				stakesSneak = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_SNAKE));
				stakes1Put = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_1PUT));
				tournamentPurse = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_SPONSOR_PURSE));
				
				winnerLongest = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_LONGEST));
				winnerClosest = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_CLOSEST));
				winnerPut = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_PUT));
				winnerSneak = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_SNEAK));
			}
			cur.close();
			
			// Fill all in list in mapview.
			for(GolfPlayer gp : resultlist)
			{				
				Integer res[] = new Integer[2];
				res[WINNINGS] = 0;	
				res[COST] = 0;
				map.put(gp.getPlayerID(), res);
			}
			
			
			int main_price = (stakesWin*nrOfPlayers + tournamentPurse)/nrOfWinners;
			
			for(GolfPlayer gp : winners)
			{
				Integer res[] = new Integer[2];
				res[WINNINGS] = main_price;	
				res[COST] = 0;
				map.put(gp.getPlayerID(), res);
			}
			
			int sneak_price = stakesSneak;
			int sneak_cost = stakesSneak*nrOfPlayers;
			int smartid = -1;
			
			for(GolfPlayer gp : resultlist)
			{
				Integer res[] = new Integer[2];
				int pid = gp.getPlayerID();
				int team_id = ((RaymonTour)context.getApplicationContext()).getTeamIndexByTournament(pid,this.getTournamentID());
				int nrOfTeamPlayers = ((RaymonTour)context.getApplicationContext()).getNrOfTeamPlayersByTournament(pid,this.getTournamentID());
				
				res[WINNINGS] = (map.get(pid))[WINNINGS];
				res[COST] = (map.get(pid))[COST];
				
				if(team_id > 0)
				{
					smartid = team_id;
				}
				else
				{
					smartid = pid;
				}
				
				res = map.get(pid);
				res[COST] = (winnerLongest > -1 ? stakesLong : 0 ) + (winnerClosest > -1 ? stakesClose : 0 ) + (winnerPut > -1 ? stakes1Put : 0 ) + stakesWin;
			
				if(smartid == winnerLongest)
				{
					res[WINNINGS] = map.get(pid)[WINNINGS] + (stakesLong*nrOfPlayers)/nrOfTeamPlayers;
					map.put(pid, res);
				}
				if(smartid == winnerClosest)
				{
					res[WINNINGS] = map.get(pid)[WINNINGS] + (stakesClose*nrOfPlayers)/nrOfTeamPlayers;
					map.put(pid, res);
				}
				if(smartid == winnerPut)
				{
					res[WINNINGS] = map.get(pid)[WINNINGS] + (stakes1Put*nrOfPlayers)/nrOfTeamPlayers;
					map.put(pid, res);
				}
				if(smartid != winnerSneak && winnerSneak != -1)
				{
					res[WINNINGS] = map.get(pid)[WINNINGS] + sneak_price;
					map.put(pid, res);
				}
				if(smartid == winnerSneak)
				{
					res[COST] = map.get(pid)[COST] + stakesSneak*(nrOfPlayers-nrOfTeamPlayers)/nrOfTeamPlayers;
					map.put(pid, res);
					
				}
					
			}
			return map;
		}
		
		public ArrayList<GolfPlayer> tournamentResultList(Context context)
		{
			int tId = this.getTournamentID();
			HashMap<Integer, GolfPlayer> map = new HashMap<Integer, GolfPlayer>();
			Collection<GolfPlayer> res = null;
			ArrayList<GolfPlayer> list = new ArrayList<GolfPlayer>();
			ContentResolver cr = context.getApplicationContext().getContentResolver();
			Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES,
								  null,
								  TourContentProvider.KEY_TOURNAMENT_ID + "=?",
								  new String[]{String.valueOf(this.getTournamentID())},
								  null);
			if(cur != null)
			{
				// Re-set before start calculateing score
				ArrayList<GolfPlayer> listmap = new ArrayList<GolfPlayer>();
				listmap.addAll(map.values());
				for(GolfPlayer pp : listmap)
				{
					map.get(pp.getPlayerID()).setTemp_stroke(0);
				}
				
				for(GolfPlayer gp: ((RaymonTour)context.getApplicationContext()).getPlayerlist() )
				{
					gp.setTemp_stroke(0);
				}
				
				
				while(cur.moveToNext())
				{
					int player_id = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_ID));
					int team_id = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_TEAM_ID));
					int course_id = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_ID));
					int stroke = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_SCORE));
					int hole_nr = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NR));
					GolfPlayer player = ((RaymonTour)context.getApplicationContext()).getPlayerbyIndex(player_id);
					if(map.get(player.getPlayerID()) == null)
						map.put(player.getPlayerID(), player);
					GolfCourse course = ((RaymonTour)context.getApplicationContext()).getCoursebyIndex(course_id);
					int score = 0;
					if(team_id > 0)
					{
						GolfTeam team = ((RaymonTour)context.getApplicationContext()).getPlayersByTeamIndex(team_id,this.getTournamentID());
						for(GolfPlayer gp : team.getPlayers())
						{
							if(map.get(gp.getPlayerID()) == null)
								map.put(gp.getPlayerID(), gp);
							score = map.get(gp.getPlayerID()).getTemp_stroke() + course.getGameTeamScore(hole_nr, stroke, team, this);
							map.get(gp.getPlayerID()).setTemp_stroke(score);
							score = 0;
						}
							
						
					}
					else
					{
						score =  map.get(player.getPlayerID()).getTemp_stroke() + course.getGameScore(hole_nr, stroke, player, this);
						map.get(player.getPlayerID()).setTemp_stroke(score);
					}
					
					
				}
				
			}
			
			res = map.values();
			list.clear();
			list.addAll(res);
			
			if(this.getTournamentMode() == this.STROKE_TOUR )
			{
				Collections.sort(list,new Comparator<GolfPlayer>(){

					@Override
					public int compare(GolfPlayer lhs, GolfPlayer rhs) {
					// TODO Auto-generated method stub
					return (lhs.getTemp_stroke() - rhs.getTemp_stroke());
					}
				
				
				});
				
			}
			else
			{
				Collections.sort(list,new Comparator<GolfPlayer>(){

					@Override
					public int compare(GolfPlayer lhs, GolfPlayer rhs) {
					// TODO Auto-generated method stub
					return (lhs.getTemp_stroke() - rhs.getTemp_stroke())*-1;
					}
				
				
				});
			}
			
			return list;
			
		}
			
	}
