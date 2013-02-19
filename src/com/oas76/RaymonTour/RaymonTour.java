package com.oas76.RaymonTour;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Application;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

public class RaymonTour extends Application {
	
	
	
	private ArrayList<GolfCourse> courselist;
	private ArrayList<GolfPlayer> playerlist;
	private ArrayList<Tour> tourlist;
	private ArrayList<GolfTournament> tournamnetlist;
	private ContentObserver observer;
	private Handler handler;
	
	public RaymonTour()
	{
		this.courselist = new ArrayList<GolfCourse>();
		this.playerlist = new ArrayList<GolfPlayer>();
		this.tourlist = new ArrayList<Tour>();
		this.tournamnetlist = new ArrayList<GolfTournament>();
		this.observer = null;
		this.handler = new Handler();
	}
	
	
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		registerObserver();
		// Triger db rady with minor "update"
		ContentResolver cr = getContentResolver();
		ContentValues value = new ContentValues();
		value.put(TourContentProvider.KEY_TOUR_ID, 1);
		cr.insert(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TT, String.valueOf(0)),value);
		cr.delete(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_TT, String.valueOf(0)), null, null);
	}
	
	@Override
	public void onTerminate()
	{
		super.onTerminate();
		unregisterObserver();
	}
	
	
	public ContentObserver getContentObserver()
	{
		return observer;
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
	
	public int getTeamIndexByTournament(int playerid, int tournamentid)
	{
		ContentResolver cr = getContentResolver();
		int team_id = 0;
        Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES,
        				null,
        				TourContentProvider.KEY_HOLE_NR + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=? AND " + TourContentProvider.KEY_PLAYER_ID + "=?",
        				new String[]{"1", String.valueOf(tournamentid), String.valueOf(playerid)},
        				null);
        if(cur != null && cur.getCount() == 1)
        {
        	cur.moveToNext();
        	team_id = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_TEAM_ID));
        	
        	cur.close();
        }
        return team_id;
	}
	
	public int getNrOfTeamPlayersByTournament(int playerid, int tournamentid)
	{
		int team_id = getTeamIndexByTournament(playerid, tournamentid);
		int count = 0;
		if(team_id == 0)
			count = 1;
		else
		{
			GolfTeam players = getPlayersByTeamIndex(team_id, tournamentid);
			count = players.getPlayers().size();
		}
		return count;
		
	}
	
    public GolfTeam getPlayersByTeamIndex(int index, int tournament_id)
    {
    	ArrayList<GolfPlayer> res = new ArrayList<GolfPlayer>();
    	ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(TourContentProvider.CONTENT_URI_SCORES,
        				null,
        				TourContentProvider.KEY_HOLE_NR + "=? AND " + TourContentProvider.KEY_TOURNAMENT_ID + "=? AND " + TourContentProvider.KEY_TEAM_ID + "=?",
        				new String[]{"1", String.valueOf(tournament_id), String.valueOf(index)},
        				null);
        if(cur != null)
        {
        	// Correct score entry found
        	res.clear();
        	while(cur.moveToNext())
        	{
        		int player_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_ID);
        		int player_id = cur.getInt(player_index);
        		res.add(getPlayerbyIndex(player_id));
        	}
        cur.close();
            	
        }
        
        return new GolfTeam(res);
    }
	

    public Tour getTourbyIndex(int int1) {
		for(Tour gt : this.getTourlist())
		{
			if(gt.getTourID() == int1)
				return gt;
		}
		return null;
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
	
	public void hookupDoYouWannaStore(String txt, Context context, int index)
	{
		Class mycontext = context.getClass();
		String classname = mycontext.getSimpleName();
		
		DialogFragment newFragment = new AreYouSureFragment();
		((AreYouSureFragment)newFragment).setDisplayText(txt);
		((AreYouSureFragment)newFragment).setContext(context);
		((AreYouSureFragment)newFragment).setDbIndex(index);
		((AreYouSureFragment)newFragment).setOfficialData(true);

		
		
		if(classname.equals("TourEdit"))
			newFragment.show(((TourEdit)context).getFragmentManager(), "AreYouSure");
		else if(classname.equals("PlayerEdit"))
			newFragment.show(((PlayerEdit)context).getFragmentManager(), "AreYouSure");
		else if(classname.equals("ScoreEdit"))
			newFragment.show(((ScoreEdit)context).getFragmentManager(), "AreYouSure");
	}

	
	public void hookupAreYouSure(String txt, Context context, int index)
	{
		
		Class mycontext = context.getClass();
		String classname = mycontext.getSimpleName();
		
		DialogFragment newFragment = new AreYouSureFragment();
		((AreYouSureFragment)newFragment).setDisplayText(txt);
		((AreYouSureFragment)newFragment).setContext(context);
		((AreYouSureFragment)newFragment).setDbIndex(index);

		
		
		if(classname.equals("TourEdit"))
			newFragment.show(((TourEdit)context).getFragmentManager(), "AreYouSure");
		else if(classname.equals("PlayerEdit"))
			newFragment.show(((PlayerEdit)context).getFragmentManager(), "AreYouSure");
		else if(classname.equals("ScoreEdit"))
			newFragment.show(((ScoreEdit)context).getFragmentManager(), "AreYouSure");
	}
	
	
	public void deletePlayer(int index)
	{

		ContentResolver cr = getContentResolver();
		cr.delete(TourContentProvider.CONTENT_URI_PLAYERS,
				  TourContentProvider.KEY_ID + "=?",
				  new String[]{String.valueOf(index)});
		cr.delete(TourContentProvider.CONTENT_URI_SCORES,
				  TourContentProvider.KEY_PLAYER_ID + "=?",
				  new String[]{String.valueOf(index)});
		observer.onChange(false);
	}
	
	public void deleteTour(int index)
	{

		ContentResolver cr = getContentResolver();
		cr.delete(TourContentProvider.CONTENT_URI_TOURS,
				  TourContentProvider.KEY_ID + "=?",
				  new String[]{String.valueOf(index)});
		cr.delete(TourContentProvider.CONTENT_URI_TT,
				  TourContentProvider.KEY_TOUR_ID + "=?",
				  new String[]{String.valueOf(index)});
		observer.onChange(false);
	}
	
	public void deleteTournament(int index)
	{

		ContentResolver cr = getContentResolver();
		cr.delete(TourContentProvider.CONTENT_URI_TOURNAMENTS,
				  TourContentProvider.KEY_ID + "=?",
				  new String[]{String.valueOf(index)});
		cr.delete(TourContentProvider.CONTENT_URI_TT,
				  TourContentProvider.KEY_TOURNAMENT_ID + "=?",
				  new String[]{String.valueOf(index)});
		cr.delete(TourContentProvider.CONTENT_URI_SCORES,
				  TourContentProvider.KEY_TOURNAMENT_ID + "=?",
				  new String[]{String.valueOf(index)});
		observer.onChange(false);
	}
	
	
	private void registerObserver()
	{
		ContentResolver cr = getContentResolver();
		observer = new DataObserver(handler);
		cr.registerContentObserver(TourContentProvider.CONTENT_URI_TOURNAMENTS, true, observer);
		cr.registerContentObserver(TourContentProvider.CONTENT_URI_COURSES,true, observer);
		cr.registerContentObserver(TourContentProvider.CONTENT_URI_HOLES,true, observer);
		cr.registerContentObserver(TourContentProvider.CONTENT_URI_TOURS,true, observer);
		cr.registerContentObserver(TourContentProvider.CONTENT_URI_PLAYERS,true, observer);
		cr.registerContentObserver(TourContentProvider.CONTENT_URI_TT,true, observer);

		
		
	}
	
	private void unregisterObserver()
	{
		ContentResolver cr = getContentResolver();
		if(observer != null) 
		{
			cr.unregisterContentObserver(observer);
			observer = null;
		}
		
		
	}
	
	class DataObserver extends ContentObserver
	{

		public DataObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange)
		{

			ContentResolver cr = getContentResolver();
			Cursor cursor = cr.query(TourContentProvider.CONTENT_URI_TOURNAMENTS,
								  null,
						          null,
						          null,
						          null);
			
			getTournamnetlist().clear();
			if(cursor != null)
			{
				
				while(cursor.moveToNext())
				{
					int index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					int name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_NAME);
					int mode_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_MODE);
					int game_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_GOLF_GAME);
					int icls3_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_INDIVIDUAL_CLS3);
					int handicap_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HANDICAPED);
					int stakes_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES);
					int stakes_longest_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_LONGEST);
					int stakes_closest_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_CLOSEST);
					int stakes_3put_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_SNAKE);
					int stakes_1put_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_STAKES_1PUT);
					int date_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_DATE);
					int course_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_ID);
					int cstroke_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_CAPPED_STROKE);
					int winner_closest = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_CLOSEST);
					int winner_longest = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_LONGEST);
					int winner_put = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_PUT);
					int winner_sneak = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_WINNER_SNEAK);
					int isOfficial = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOURNAMENT_OFFICIAL);
					GolfTournament newTournament = new GolfTournament(cursor.getInt(index));
					newTournament.setTouramentName(cursor.getString(name_index));
					newTournament.setTournamentGame(cursor.getInt(game_index));
					newTournament.setTournamentMode(cursor.getInt(mode_index));
					newTournament.setTournamentHandicapped(cursor.getInt(handicap_index));
					newTournament.setTournamentIndividualCLS1(cursor.getInt(icls3_index));
					newTournament.setTournamentGolfCourceID(cursor.getInt(course_index));
					newTournament.setStakes(cursor.getInt(stakes_index), 
										cursor.getInt(stakes_closest_index), 
										cursor.getInt(stakes_longest_index),
										cursor.getInt(stakes_3put_index), 
										cursor.getInt(stakes_1put_index));
					newTournament.setTournamentDate(Date.valueOf(cursor.getString(date_index)));
					newTournament.setCappedStroke(cursor.getInt(cstroke_index));
					newTournament.setWinnerClosest(cursor.getInt(winner_closest));
					newTournament.setWinnerLongest(cursor.getInt(winner_longest));
					newTournament.setWinnerPut(cursor.getInt(winner_put));
					newTournament.setWinnerSneak(cursor.getInt(winner_sneak));
					newTournament.setIsOffical(cursor.getInt(isOfficial));
					getTournamnetlist().add(newTournament);
				}// while
			cursor.close();
			}// if

		cursor = cr.query(TourContentProvider.CONTENT_URI_COURSES,
				  null,
		          null,
		          null,
		          null);
		
		getCourselist().clear();
		if(cursor != null)
		{
			
			while(cursor.moveToNext())
			{
				int index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
				int name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_NAME);
				int tee_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_TEE);
				int length_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_LENGTH);
				int slope_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_SLOPE);
				int value_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_VALUE);
				int par_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_PAR);
				int cimg_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_IMGURL);
				GolfCourse newCourse = new GolfCourse(cursor.getInt(index));
				newCourse.setCourceName(cursor.getString(name_index));
				newCourse.setCourceTee(cursor.getString(tee_index));
				newCourse.setCourcePar(cursor.getInt(par_index));
				newCourse.setCourceLength(cursor.getInt(length_index));
				newCourse.setCourceSlope(cursor.getInt(slope_index));
				newCourse.setCourceValue(cursor.getDouble(value_index));
				getCourselist().add(newCourse);
			} // While
			cursor.close();
			cursor = cr.query(TourContentProvider.CONTENT_URI_HOLES, null, null, null, null);
			if(cursor != null)
			{
					int index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
					int name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NAME);
					int nr_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_NR);
					int length_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_LENGTH);
					int index_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_INDEX );
					int par_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_HOLE_PAR);
					int course_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_COURSE_ID);
				
					int i =0;
					while(cursor.moveToNext())
					{
						int hole_nr = cursor.getInt(nr_index);
						i=0;
						for(GolfCourse gc : getCourselist())
						{
							if(gc.getCourceID() == cursor.getInt(course_index))
							{
								gc.setHoleName(hole_nr, cursor.getString(name_index));
								gc.setHoleIndex(hole_nr, cursor.getInt(index_index));
								gc.setHoleLength(hole_nr, cursor.getInt(length_index));
								gc.setHolePar(hole_nr, cursor.getInt(par_index));
								getCourselist().set(i, gc);
							}
							i++;
						}
					
					}// while
			cursor.close();
			} // if
				
	  } // if
	  cursor = cr.query(TourContentProvider.CONTENT_URI_TOURS,
			  			null,
			  			null,
			  			null,
			  			null);
	  
	  getTourlist().clear();
	  if(cursor != null)
	  {
			
		    int index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
			int name_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_NAME);
			int desc_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_DESC);
			int img_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_TOUR_IMG);
			
			
			while(cursor.moveToNext())
			{
				Tour newTour = new Tour(cursor.getInt(index));
				newTour.setTourName(cursor.getString(name_index));
				newTour.setTourDesc(cursor.getString(desc_index));
				newTour.setTourImgUrl(cursor.getString(img_index));
				getTourlist().add(newTour);
			}
			
	  cursor.close();
	  }// if
	  cursor = cr.query(TourContentProvider.CONTENT_URI_PLAYERS,
			  			null,
			  			null,
			  			null,
			  			null);
	  
	  getPlayerlist().clear();
	  if(cursor != null)
	  {
		    int index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
			int nic_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NIC);
			int full_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_NAME);
			int hc_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_HC);
			int img_index = cursor.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_IMGURL);
			
		
			while(cursor.moveToNext())
			{
				GolfPlayer newPlayer = new GolfPlayer(cursor.getInt(index));
				newPlayer.setPlayerName(cursor.getString(full_index));
				newPlayer.setPlayerNick(cursor.getString(nic_index));
				newPlayer.setPlayerImg(cursor.getString(img_index));
				newPlayer.setPlayerHC(cursor.getDouble(hc_index));
				getPlayerlist().add(newPlayer);
			}
	  cursor.close();
	  }// if
				
	}// method
  }// Inner class		


	public void updatePlayerWinnings(int id) {
		
		GolfTournament tournament = getTournamentbyIndex(id);
		if(!tournament.getIsOffical())
		{
			HashMap<Integer, Integer[]> map = tournament.tournamentWinningsList(this);
			Set<Entry<Integer, Integer[]>> set = map.entrySet();
			for(Entry<Integer, Integer[]> entry : set)
			{
				int pId = entry.getKey();
				int winnings = entry.getValue()[0];
				int cost = entry.getValue()[1];
			
				ContentResolver cr = getApplicationContext().getContentResolver();
				Cursor cur = cr.query(TourContentProvider.CONTENT_URI_PLAYERS,
								  	  null, 
								  	  TourContentProvider.KEY_ID + "=?",
								  	  new String[]{String.valueOf(pId)},
									  null);
				if(cur != null && cur.getCount() == 1)
				{
					cur.moveToNext();
					int player_winnings = cur.getInt(cur.getColumnIndexOrThrow(TourContentProvider.KEY_PLAYER_WINNINGS));
					winnings += player_winnings;
					cur.close();
				}
				ContentValues values = new ContentValues();
				values.put(TourContentProvider.KEY_PLAYER_WINNINGS, winnings);
				
				cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_PLAYERS,String.valueOf(pId)),
						                       values, 
											   null,
											   null);
			}
		}
			
			
		
	}
	

} // Class

	
	
	

