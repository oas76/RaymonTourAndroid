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
