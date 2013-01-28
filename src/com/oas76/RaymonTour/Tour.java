package com.oas76.RaymonTour;

public class Tour {
	
	private String tName;
	private String tImgUrl = "";
	private String tDesc;
	private int tID;
	

	public Tour(int id)
	{
		tID = id;
	}
	
	
	public String getTourName(){
		return tName;
	}
	
	public String getTourImgUrl(){
		return tImgUrl;
	}
	
	public int getTourID(){
		return tID;
	}
	
	public String getTourDesc(){
		return tDesc;
	}
	
	public void setTourID(int ID)
	{
		tID = ID;
	}
	
	public void setTourName(String name)
	{
		tName = name;
	}
	
	public void setTourImgUrl(String url)
	{
		tImgUrl = url;
	}
	
	public void setTourDesc(String desc)
	{
		tDesc = desc;
	}
	
	
	
	
	
}
