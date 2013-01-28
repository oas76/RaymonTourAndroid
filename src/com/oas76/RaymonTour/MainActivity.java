package com.oas76.RaymonTour;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    
    private static ImageButton addButton = null;
    private static int SELECTED_VIEW = 1;
    
    private static final int SELECT_TOURNAMENT = 1;
    private static final int SELECT_TOUR = 2;
    private static final int SELECT_PLAYER = 3;
    private static final int SELECT_COURSE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        String[] menu_strings = new String[4];
        menu_strings[SELECT_TOURNAMENT-1] = getString(R.string.title_section1);
        menu_strings[SELECT_TOUR-1] = getString(R.string.title_section2);
        menu_strings[SELECT_PLAYER-1] = getString(R.string.title_section3);
        menu_strings[SELECT_COURSE-1] = getString(R.string.title_section4);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        menu_strings),
                this);
        // Get Reference to the AddButton
        addButton = (ImageButton)findViewById(R.id.addButon);
        hookupButton();
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        ListFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        SELECTED_VIEW = position + 1;
        return true;
    }
    
    private void hookupButton()
    {
    	addButton.setOnClickListener(new OnClickListener() {
    		public void onClick(View v){
    			Intent intent = null;
    			switch(SELECTED_VIEW)
    			{
    				case SELECT_TOURNAMENT:
    					intent = new Intent(v.getContext(),TournamentEdit.class);
    					break;
    				case SELECT_TOUR:
    					intent = new Intent(v.getContext(), TourEdit.class);
    					break;
    				case SELECT_PLAYER:
    					//Send intent to edit player view
    					intent = new Intent(v.getContext(),PlayerEdit.class);
    					break;
    				case SELECT_COURSE:
    					new XmlGetter().execute("");
    					break;
    			
    			}
    			if(intent != null)
    	    		startActivity(intent);
    	    	else
    	    		Toast.makeText(v.getContext(),"TBD",Toast.LENGTH_LONG).show();
    		}
    	});
    }
    
    
    private class XmlGetter extends AsyncTask<String,Void,String>{
		String line = null;
		
		@Override
	      protected String doInBackground(String... params) {
				line = getCourseXML();
				InputStream is = new ByteArrayInputStream(line.getBytes());
				try{
					ArrayList<GolfCourse> gc = CourceXMLFeed.parse(is);
					updateDb(gc);
				}
				catch(Exception e){
					e.printStackTrace();
				}
	            return line;
	      }      

	      @Override
	      protected void onPostExecute(String result) { 
	    	  Toast.makeText(getBaseContext(),line,Toast.LENGTH_LONG).show();
	      }

	      @Override
	      protected void onPreExecute() {
	      }

	      @Override
	      protected void onProgressUpdate(Void... values) {
	      }
	      
	  	
	  	  private String getCourseXML(){
	  		    	String line = null;
	  		        try {
	  		        	DefaultHttpClient httpClient = new DefaultHttpClient();
	  		            HttpPost httpPost = new HttpPost("https://dl.dropbox.com/u/3974917/RaymondProFeed/GolfCourceFeed.xml");
	  		            HttpResponse httpResponse = httpClient.execute(httpPost);
	  		            HttpEntity httpEntity = httpResponse.getEntity();
	  		            line = EntityUtils.toString(httpEntity);
	  		        } catch (UnsupportedEncodingException e) {
	  		            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
	  		        } catch (MalformedURLException e) {
	  		            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
	  		        } catch (IOException e) {
	  		            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
	  		        }
	  		        return line;
	  		}
	  	  
	  	  private void updateDb(ArrayList<GolfCourse> gc )
	  	  {
  			  ContentResolver cr = getContentResolver();
	  		  for(GolfCourse cc:gc){
	  			  // Check if course exist already in db
	  			  String name = cc.getCourceName();
	  			  String tee = cc.getCourceTee();
	  			  int id = -1;
	  			  
	  			  String selection = TourContentProvider.KEY_COURSE_NAME + "=? AND " + TourContentProvider.KEY_COURSE_TEE + "=?";	
	  			  String[] selectionArgs = { name, tee };
	  			  Cursor cur = cr.query(TourContentProvider.CONTENT_URI_COURSES, null, selection, selectionArgs, null);
	  			  boolean newEntry = true;
	  			  
	  			  // If No Match, populate database
	  			  if(cur != null)
	  				  newEntry = false;
	  			  
	  			  // Update course
	  			 ContentValues cv = new ContentValues();
	  			 cv.put(TourContentProvider.KEY_COURSE_NAME, name);
	  			 cv.put(TourContentProvider.KEY_COURSE_TEE, tee);
	  			 cv.put(TourContentProvider.KEY_COURSE_PAR, cc.getCourcePar());
	  			 cv.put(TourContentProvider.KEY_COURSE_SLOPE, cc.getCourceSlope());
	  			 cv.put(TourContentProvider.KEY_COURSE_VALUE, cc.getCourceValue());
	  			 cv.put(TourContentProvider.KEY_COURSE_LENGTH, cc.getCourceLength());
	  			 if(newEntry)
	  			 {
	  				 Uri courseId = cr.insert(TourContentProvider.CONTENT_URI_COURSES, cv);
	  				 id = Integer.parseInt(courseId.getPathSegments().get(1));
	  			 }
	  			 else
	  			 {
	  				 cur.moveToFirst();
	  				 int key_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
	  				 id = cur.getInt(key_index);
	  				 cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_COURSES,Integer.toString(id)),cv, null, null);
	  			 }
	   				  
	  			 // update holes
	  			 for ( int i = 1; i<= 18; i++)
	  			 {
	  				 cv = new ContentValues();
	  				 cv.put(TourContentProvider.KEY_COURSE_ID, id);
	  				 cv.put(TourContentProvider.KEY_HOLE_NAME, cc.getHoleName(i));
	  				 cv.put(TourContentProvider.KEY_HOLE_NR, i);
	  				 cv.put(TourContentProvider.KEY_HOLE_PAR,cc.getHolePar(i));
	  				 cv.put(TourContentProvider.KEY_HOLE_INDEX,cc.getHoleIndex(i));
	  				 cv.put(TourContentProvider.KEY_HOLE_LENGTH,cc.getHoleLength(i));
	  				 if(newEntry)
	  					 cr.insert(TourContentProvider.CONTENT_URI_HOLES, cv);
	  				 else
	  				 {
	  					 selection = TourContentProvider.KEY_COURSE_ID + "=? AND " + TourContentProvider.KEY_HOLE_NR + "=?";	
	  					 selectionArgs = new String[2];
	  					 selectionArgs[0] = Integer.toString(id);
	  					 selectionArgs[1] = Integer.toString(i);
	  					 cur = cr.query(TourContentProvider.CONTENT_URI_HOLES, null, selection, selectionArgs, null);
	  					 cur.moveToFirst();
	  					 int key_index = cur.getColumnIndexOrThrow(TourContentProvider.KEY_ID);
	  					 int hole_id = cur.getInt(key_index);
	  					 cr.update(Uri.withAppendedPath(TourContentProvider.CONTENT_URI_HOLES,Integer.toString(hole_id)), cv, null, null);
	  				 } // else
	  			  } // for
	  			  
			 } // for
	  		  
	  	  } // updatedb
	
	} // private async class
} // MainActivity
    

