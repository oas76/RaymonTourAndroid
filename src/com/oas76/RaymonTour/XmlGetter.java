package com.oas76.RaymonTour;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


public class XmlGetter extends AsyncTask<String,Void,String>{
	  String line = null;
	  Context context = null;
	  
	  public void setContext(Context con)
	  {
		  this.context = con;
	  }
	
	
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
    	  Toast.makeText(context,line,Toast.LENGTH_LONG).show();

      }

      @Override
      protected void onPreExecute() {
      }

      @Override
      protected void onProgressUpdate(Void... values) {
    	  Toast.makeText(context,"Downloading...",Toast.LENGTH_LONG).show();
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
  		            Toast.makeText(context,line,Toast.LENGTH_LONG).show();
  		        } catch (MalformedURLException e) {
  		            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
  		            Toast.makeText(context,line,Toast.LENGTH_LONG).show();
  		        } catch (IOException e) {
  		            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
  		            Toast.makeText(context,line,Toast.LENGTH_LONG).show();
  		        }
  		        return line;
  		}
  	  
  	  private void updateDb(ArrayList<GolfCourse> gc )
  	  {
		  ContentResolver cr = context.getContentResolver();
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