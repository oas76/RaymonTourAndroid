/**
 * Listing 8-14: A skeleton Content Provider implementation
 */

package com.oas76.RaymonTour;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class TourContentProvider extends ContentProvider {

  public static final Uri CONTENT_URI_PLAYERS = Uri.parse("content://com.oas76.raymontour/players");
  public static final Uri CONTENT_URI_COURSES = Uri.parse("content://com.oas76.raymontour/courses");
  public static final Uri CONTENT_URI_HOLES = Uri.parse("content://com.oas76.raymontour/holes");
  public static final Uri CONTENT_URI_TOURNAMENTS = Uri.parse("content://com.oas76.raymontour/tournaments");
  public static final Uri CONTENT_URI_TOURS = Uri.parse("content://com.oas76.raymontour/tours");
  public static final Uri CONTENT_URI_SCORES = Uri.parse("content://com.oas76.raymontour/scores");
  public static final Uri CONTENT_URI_TT = Uri.parse("content://com.oas76.raymontour/tt");
  
  /**
   * Listing 8-8: Defining a UriMatcher to determine if a request is for all elements or a single row
   */
  //Create the constants used to differentiate between the different URI
  //requests.
  private static final int ALLROWS = 1;
  private static final int SINGLE_ROW = 2;
  
  private static final UriMatcher uriMatcher;
  
  //Populate the UriMatcher object, where a URI ending in 
  //'elements' will correspond to a request for all items,
  //and 'elements/[rowID]' represents a single row.
  static {
   uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
   uriMatcher.addURI("com.oas76.raymontour", 
                     "players", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
                     "players/#", SINGLE_ROW);
   uriMatcher.addURI("com.oas76.raymontour", 
           			 "courses", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
           			 "courses/#", SINGLE_ROW);
   uriMatcher.addURI("com.oas76.raymontour", 
 			         "holes", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
 			         "holes/#", SINGLE_ROW);
   uriMatcher.addURI("com.oas76.raymontour", 
	         		 "tournaments", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
	         		 "tournaments/#", SINGLE_ROW);
   uriMatcher.addURI("com.oas76.raymontour", 
   		 			"tours", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
   		 			"tours/#", SINGLE_ROW);
   uriMatcher.addURI("com.oas76.raymontour", 
	 				"scores", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
	 				"scores/#", SINGLE_ROW);
   uriMatcher.addURI("com.oas76.raymontour", 
					"tt", ALLROWS);
   uriMatcher.addURI("com.oas76.raymontour", 
					"tt/#", SINGLE_ROW);

  }
  /** */
  
  // The index (key) column name for use in where clauses.
  public static final String KEY_ID = "_id";

  // The name and column index of each column in your database.
  // These should be descriptive.

  // Golf Player Table Columns names
  static final String KEY_PLAYER_NAME = "PlayerName";
  static final String KEY_PLAYER_NIC = "PlayerNic";
  static final String KEY_PLAYER_HC = "PlayerHc";
  static final String KEY_PLAYER_WINNINGS = "PlayerWinnings";
  static final String KEY_PLAYER_IMGURL = "PlayerImgUrl"; 
  
  // Golf Course Column names
  static final String KEY_COURSE_NAME = "GolfCourseName";
  static final String KEY_COURSE_TEE = "GolfCourseTee";
  static final String KEY_COURSE_SLOPE = "GolfCourseSlope";
  static final String KEY_COURSE_VALUE = "GolfCourseValue";
  static final String KEY_COURSE_PAR = "GolfCoursePar";
  static final String KEY_COURSE_LENGTH = "GolfCourseLength";
  static final String KEY_COURSE_IMGURL = "GolfCourseImgUrl";
	
   // Golf Hole Column name
  static final String KEY_COURSE_ID = "GolfCourseId";
  static final String KEY_HOLE_NR = "GolfHoleNr";
  static final String KEY_HOLE_PAR = "GolfHolePar";
  static final String KEY_HOLE_INDEX = "GolfHoleIndex";
  static final String KEY_HOLE_LENGTH = "GolfHoleLength";
  static final String KEY_HOLE_NAME = "GolfHoleName";
 
  
  // Golf Tournament Table Columns names
  static final String KEY_GOLF_MODE = "GolfMode";
  static final String KEY_GOLF_GAME = "GolfGame";
  static final String KEY_HANDICAPED = "Handicaped";
  static final String KEY_INDIVIDUAL_CLS3 = "IndividualCLS3";
  static final String KEY_STAKES = "StakesWin";
  static final String KEY_STAKES_CLOSEST = "StakesWinClosest";
  static final String KEY_STAKES_LONGEST = "StakesWinLongest";
  static final String KEY_STAKES_SNAKE = "StakesWinSnake";
  static final String KEY_STAKES_1PUT = "StakesWin1Put";
  static final String KEY_TOURNAMENT_IMGURL = "TournamentImageURL";
  static final String KEY_TOURNAMENT_SPONSOR_PURSE = "TournamentSponsorPurse";
  static final String KEY_TOURNAMENT_NAME = "TournamentName";
  static final String KEY_TOURNAMENT_DATE = "TournamentDate";
  
  // Golf Tour table columns
  static final String KEY_TOUR_NAME = "TourName";
  static final String KEY_TOUR_IMG = "TourImg";
  static final String KEY_TOUR_DESC = "TourDesc";
  
  // Scores table
  static final String KEY_PLAYER_ID = "PlayerId";
  static final String KEY_GOLF_SCORE = "GolfScore";
  static final String KEY_TEAM_ID = "TeamId";
  
  //tt table
  static final String KEY_TOUR_ID = "TourId";
  static final String KEY_TOURNAMENT_ID = "TournamentId";
  
  
  
  /**
   * Listing 8-9: Creating the Content ProviderÕs database
   */
  private TourDatabase myOpenHelper;

  @Override
  public boolean onCreate() {
    // Construct the underlying database.
    // Defer opening the database until you need to perform
    // a query or transaction.
    myOpenHelper = new TourDatabase(getContext(),
        TourDatabase.DATABASE_NAME, 
        TourDatabase.DATABASE_VERSION);
    
    return true;
  }
  
  /**
   * Listing 8-10: Implementing queries and transactions within a Content Provider
   */
  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
    String[] selectionArgs, String sortOrder) {

    // Open the database.
	if(myOpenHelper == null)
	{
	   Toast.makeText(getContext(),"Whaaat",Toast.LENGTH_LONG).show();
	   return null;
	}
	
    SQLiteDatabase db = myOpenHelper.getReadableDatabase();
   // try {
   //   db = myOpenHelper.getWritableDatabase();
   // } catch (/*SQLite*/Exception ex) {
   //   db = myOpenHelper.getReadableDatabase();
   // }

    // Replace these with valid SQL statements if necessary.
    String groupBy = null;
    String having = null;
    
    // Use an SQLite Query Builder to simplify constructing the 
    // database query.
    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    // If this is a row query, limit the result set to the passed in row.
    switch (uriMatcher.match(uri)) {
      case SINGLE_ROW : 
        String rowID = uri.getPathSegments().get(1);
        queryBuilder.appendWhere(KEY_ID + "=" + rowID);
      default: break;
    }
    String table = uri.getPathSegments().get(0);
    
    // Specify the table on which to perform the query. This can 
    // be a specific table or a join as required.  
    if(table.equals("players"))
    {
        queryBuilder.setTables(TourDatabase.TABLE_GOLFPLAYER);
    }
    else if (table.equals("courses"))
    {
    	queryBuilder.setTables(TourDatabase.TABLE_GOLFCOURSE);
    }
    else if (table.equals("holes"))
    {
    	queryBuilder.setTables(TourDatabase.TABLE_GOLFHOLE);
    }
    else if (table.equals("tournaments"))
    {
    	queryBuilder.setTables(TourDatabase.TABLE_GOLFTOURNAMENT);
    }
    else if (table.equals("tours"))
    {
    	queryBuilder.setTables(TourDatabase.TABLE_TOUR);
    }
    else if (table.equals("scores"))
    {
    	queryBuilder.setTables(TourDatabase.TABLE_SCORE);
    }
    else if (table.equals("tt"))
    {
    	queryBuilder.setTables(TourDatabase.TABLE_TT);
    }
    // Execute the query.
    
    Cursor cursor = queryBuilder.query(db, projection, selection,
        selectionArgs, groupBy, having, sortOrder);
    
    if(table.equals("players"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_PLAYERS);
    }
    else if(table.equals("courses"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_COURSES);
    }
    else if(table.equals("holes"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_HOLES);
    }
    else if(table.equals("tournaments"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_TOURNAMENTS);
    }
    else if(table.equals("tours"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_TOURS);
    }
    else if(table.equals("tt"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_TT);
    }
    else if(table.equals("scores"))
    {
    	cursor.setNotificationUri(getContext().getContentResolver(), TourContentProvider.CONTENT_URI_SCORES);
    }
    
    
    // Return the result Cursor.
    if(cursor.getCount() == 0)
    	cursor = null;
    
    return cursor;
  }
  
  /**
   * Listing 8-11: Returning a Content Provider MIME type
   */
  @Override
  public String getType(Uri uri) {
    // Return a string that identifies the MIME type
    // for a Content Provider URI
	String returnMime = null;
	if(uri.getPathSegments().get(0).equals("players")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.players";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.players";
			
		}
    }
	else if(uri.getPathSegments().get(0).equals("courses")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.courses";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.courses";
			
		}
    }
	else if(uri.getPathSegments().get(0).equals("holes")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.holes";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.holes";
			
		}
    }
	
	else if(uri.getPathSegments().get(0).equals("tournaments")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.tournaments";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.tournaments";
			
		}
    }
	
	else if(uri.getPathSegments().get(0).equals("tours")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.tours";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.tours";
			
		}
    }
	
	else if(uri.getPathSegments().get(0).equals("scores")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.scores";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.scores";
			
		}
    }
	
	else if(uri.getPathSegments().get(0).equals("tt")) 
	{
		switch (uriMatcher.match(uri)) {
			case ALLROWS:
				returnMime = "vnd.android.cursor.dir/vnd.oas76.raymontour.tt";
			case SINGLE_ROW: 
				returnMime = "vnd.android.cursor.item/vnd.oas76.raymontour.tt";
			
		}
    }
	
	if(returnMime == null)
		throw new IllegalArgumentException("Unsupported URI: " + uri);
	return returnMime;
  }

  /**
   * Listing 8-12: Typical Content Provider transaction implementations
   */
  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    // Open a read / write database to support the transaction.
    SQLiteDatabase db = myOpenHelper.getWritableDatabase();
    
    // If this is a row URI, limit the deletion to the specified row.
    switch (uriMatcher.match(uri)) {
      case SINGLE_ROW : 
        String rowID = uri.getPathSegments().get(1);
        selection = KEY_ID + "=" + rowID
            + (!TextUtils.isEmpty(selection) ? 
              " AND (" + selection + ')' : "");
      default: break;
    }
    
    // To return the number of deleted items you must specify a where
    // clause. To delete all rows and return a value pass in "1".
    if (selection == null)
      selection = "1";
    
    String table = uri.getPathSegments().get(0);
    int deleteCount = 0;
    
    // Specify the table on which to perform the query. This can 
    // be a specific table or a join as required.  
    // Then delete
    if(table.equals("players"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_GOLFPLAYER, 
    		      selection, selectionArgs);
    }
    else if(table.equals("courses"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_GOLFCOURSE, 
    		      selection, selectionArgs);
    }
    else if(table.equals("holes"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_GOLFHOLE, 
    		      selection, selectionArgs);
    }
    
    else if(table.equals("tournaments"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_GOLFTOURNAMENT, 
    		      selection, selectionArgs);
    }
    
    else if(table.equals("tours"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_TOUR, 
    		      selection, selectionArgs);
    }
    
    else if(table.equals("scores"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_SCORE, 
    		      selection, selectionArgs);
    }
    
    else if(table.equals("tt"))
    {
    	deleteCount = db.delete(TourDatabase.TABLE_TT, 
    		      selection, selectionArgs);
    }

    // Notify any observers of the change in the data set.
    getContext().getContentResolver().notifyChange(uri, null);
    
    // Return the number of deleted items.
    return deleteCount;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    // Open a read / write database to support the transaction.
    SQLiteDatabase db = myOpenHelper.getWritableDatabase();
    
    // To add empty rows to your database by passing in an empty 
    // Content Values object you must use the null column hack
    // parameter to specify the name of the column that can be 
    // set to null.
    String nullColumnHack = null;
    
    // Insert the values into the table
    String table = uri.getPathSegments().get(0);
    long id =  -1;
    Uri contentURIRef = null;
    
    // Specify the table on which to perform the query. This can 
    // be a specific table or a join as required.  
    // Then delete
    if(table.equals("players"))
    {
        id = db.insert(TourDatabase.TABLE_GOLFPLAYER, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_PLAYERS;
        
    }   
    else if(table.equals("courses"))
    {
        id = db.insert(TourDatabase.TABLE_GOLFCOURSE, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_COURSES;
        
    } 
    else if(table.equals("holes"))
    {
        id = db.insert(TourDatabase.TABLE_GOLFHOLE, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_HOLES;
        
    } 
    
    else if(table.equals("tournaments"))
    {
        id = db.insert(TourDatabase.TABLE_GOLFTOURNAMENT, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_TOURNAMENTS;
        
    } 
    
    else if(table.equals("tours"))
    {
        id = db.insert(TourDatabase.TABLE_TOUR, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_TOURS;
        
    } 
    
    else if(table.equals("scores"))
    {
        id = db.insert(TourDatabase.TABLE_SCORE, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_SCORES;
        
    } 
    
    else if(table.equals("tt"))
    {
        id = db.insert(TourDatabase.TABLE_TT, 
                nullColumnHack, values);
        contentURIRef = CONTENT_URI_TT;
        
    } 
    
    
    // Construct and return the URI of the newly inserted row.
    if (id > -1) {
      // Construct and return the URI of the newly inserted row.
      Uri insertedId = ContentUris.withAppendedId(contentURIRef, id);
        
      // Notify any observers of the change in the data set.
      getContext().getContentResolver().notifyChange(insertedId, null);
        
      return insertedId;
    }
    else
      return null;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection,
    String[] selectionArgs) {
    
    // Open a read / write database to support the transaction.
    SQLiteDatabase db = myOpenHelper.getWritableDatabase();
    
    // If this is a row URI, limit the deletion to the specified row.
    switch (uriMatcher.match(uri)) {
      case SINGLE_ROW : 
        String rowID = uri.getPathSegments().get(1);
        selection = KEY_ID + "=" + rowID
            + (!TextUtils.isEmpty(selection) ? 
              " AND (" + selection + ')' : "");
      default: break;
    }
    
    String table = uri.getPathSegments().get(0);
    int updateCount =  -1;
    
    // Specify the table on which to perform the query. This can 
    // be a specific table or a join as required.  
    // Then delete
    if(table.equals("players"))
    {
        updateCount = db.update(TourDatabase.TABLE_GOLFPLAYER, 
        	      values, selection, selectionArgs);
    }    
    else if(table.equals("courses"))
    {
        updateCount = db.update(TourDatabase.TABLE_GOLFCOURSE, 
        	      values, selection, selectionArgs);
    } 
    else if(table.equals("holes"))
    {
        updateCount = db.update(TourDatabase.TABLE_GOLFHOLE, 
        	      values, selection, selectionArgs);
    }   
    
    else if(table.equals("tournaments"))
    {
        updateCount = db.update(TourDatabase.TABLE_GOLFTOURNAMENT, 
        	      values, selection, selectionArgs);
    }   
    
    else if(table.equals("tours"))
    {
        updateCount = db.update(TourDatabase.TABLE_TOUR, 
        	      values, selection, selectionArgs);
    }   
    
    else if(table.equals("scores"))
    {
        updateCount = db.update(TourDatabase.TABLE_SCORE, 
        	      values, selection, selectionArgs);
    }  
    
    else if(table.equals("tt"))
    {
        updateCount = db.update(TourDatabase.TABLE_TT, 
        	      values, selection, selectionArgs);
    }  

    // Notify any observers of the change in the data set.
    getContext().getContentResolver().notifyChange(uri, null);
    
    return updateCount;
  }
  
  
  private static class TourDatabase extends SQLiteOpenHelper {
		
		// All Static variables
	    // Database Version
	    static final int DATABASE_VERSION = 2;
	 
	    // Database Name
	    static final String DATABASE_NAME = "TourDatabase.db";
	    
	    //GOLF PLAYERS_DATABASE
	    static final String TABLE_GOLFPLAYER = "GolfPlayerTable";
	    static final String TABLE_GOLFCOURSE = "GolfCourseTable";
	    static final String TABLE_GOLFHOLE = "GolfHoleTable";
	    static final String TABLE_GOLFTOURNAMENT = "GolfTournamentTable";
	    static final String TABLE_TOUR = "TourTable";
	    static final String TABLE_SCORE = "ScoreTable";
	    static final String TABLE_TT = "TourTournamentTable";
	    
	    static final String CREATE_PLAYER_DB = "CREATE TABLE " + TABLE_GOLFPLAYER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PLAYER_NAME + " TEXT," + KEY_PLAYER_NIC + " TEXT," + KEY_PLAYER_HC + " REAL," + KEY_PLAYER_IMGURL + " TEXT," + KEY_PLAYER_WINNINGS + " INTEGER)";
	    static final String CREATE_COURSE_DB = "CREATE TABLE " + TABLE_GOLFCOURSE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_NAME + " TEXT," + KEY_COURSE_TEE + " TEXT," + KEY_COURSE_PAR + " INTEGER," + KEY_COURSE_SLOPE + " INTEGER," + KEY_COURSE_VALUE + " REAL," + KEY_COURSE_LENGTH + " INTEGER," + KEY_COURSE_IMGURL + " TEXT)";
	  	static final String	CREATE_HOLE_DB = "CREATE TABLE " + TABLE_GOLFHOLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_ID + " INTEGER," + KEY_HOLE_NR + " INTEGER," + KEY_HOLE_PAR + " INTEGER," + KEY_HOLE_INDEX + " INTEGER," + KEY_HOLE_LENGTH + " INTEGER," + KEY_HOLE_NAME + " TEXT)";
	    static final String CREATE_TOURNAMENT_DB = "CREATE TABLE " + TABLE_GOLFTOURNAMENT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TOURNAMENT_NAME + " TEXT," + KEY_TOURNAMENT_DATE + " DATE," + KEY_COURSE_ID + " INTEGER, " + KEY_GOLF_MODE + " INTEGER," + KEY_GOLF_GAME + " INTEGER," + KEY_HANDICAPED + " INTEGER," + KEY_INDIVIDUAL_CLS3 + " INTEGER," + KEY_STAKES + " INTEGER," + KEY_STAKES_CLOSEST + " INTEGER," + KEY_STAKES_LONGEST + " INTEGER," + KEY_STAKES_SNAKE + " INTEGER," + KEY_STAKES_1PUT + " INTEGER," + KEY_TOURNAMENT_SPONSOR_PURSE + " INTEGER, " + KEY_TOURNAMENT_IMGURL + " TEXT)"; 
	    static final String CREATE_TOUR_DB = "CREATE TABLE " + TABLE_TOUR + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TOUR_NAME + " TEXT, " + KEY_TOUR_DESC + " TEXT," + KEY_TOUR_IMG + " TEXT)";
		static final String CREATE_SCORE_DB = "CREATE TABLE " + TABLE_SCORE + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_PLAYER_ID + " INTEGER, " + KEY_TEAM_ID + " INTEGER, " + KEY_TOURNAMENT_ID + " INTEGER, " +  KEY_COURSE_ID + " INTEGER, " + KEY_HOLE_NR + " INTEGER, " + KEY_GOLF_SCORE + " INTEGER)";
	    static final String CREATE_TT_DB = "CREATE TABLE " + TABLE_TT + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TOUR_ID + " INTEGER, " + KEY_TOURNAMENT_ID + " INTEGER)";
	    
		public TourDatabase(Context context,String db_name,int db_version ) {
			super(context, db_name, null, db_version);
		}
		
	    @Override
	    public void onCreate(SQLiteDatabase _db) {
	      try
	      {
	    	  _db.execSQL(CREATE_PLAYER_DB);
	    	  _db.execSQL(CREATE_COURSE_DB);
	    	  _db.execSQL(CREATE_HOLE_DB);
	    	  _db.execSQL(CREATE_TOURNAMENT_DB);
	    	  _db.execSQL(CREATE_TOUR_DB);
	    	  _db.execSQL(CREATE_SCORE_DB);
	    	  _db.execSQL(CREATE_TT_DB);
	      }
	      catch(SQLException e)
	      {
	    	  e.printStackTrace();
	      }
	      
	    }
	    
	    @Override
	    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
	      // Log the version upgrade.
	      Log.w("TaskDBAdapter", "Upgrading from version " +
	                             _oldVersion + " to " +
	                             _newVersion + ", which will destroy all old data");

	      // Upgrade the existing database to conform to the new version. Multiple
	      // previous versions can be handled by comparing _oldVersion and _newVersion
	      // values.

	      // The simplest case is to drop the old table and create a new one.
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_GOLFPLAYER);
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_GOLFCOURSE);
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_GOLFHOLE);
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_GOLFTOURNAMENT);
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_TOUR);
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_SCORE);
	      _db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_TT);

	      // Create a new one.
	      onCreate(_db);
	    }
  }
  
  /*

  private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

    // Database name, version, and table names.
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "mainTable";

    // SQL Statement to create a new database.
    private static final String DATABASE_CREATE = "create table " +
      DATABASE_TABLE + " (" + KEY_ID +
      " integer primary key autoincrement, " +
      KEY_COLUMN_1_NAME + " text not null);";

    public MySQLiteOpenHelper(Context context, String name,
                      CursorFactory factory, int version) {
      super(context, name, factory, version);
    }

    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db) {
      _db.execSQL(DATABASE_CREATE);
    }

    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
      // Log the version upgrade.
      Log.w("TaskDBAdapter", "Upgrading from version " +
                             _oldVersion + " to " +
                             _newVersion + ", which will destroy all old data");

      // Upgrade the existing database to conform to the new version. Multiple
      // previous versions can be handled by comparing _oldVersion and _newVersion
      // values.

      // The simplest case is to drop the old table and create a new one.
      _db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
      // Create a new one.
      onCreate(_db);
    }
  }
  
  */
}
