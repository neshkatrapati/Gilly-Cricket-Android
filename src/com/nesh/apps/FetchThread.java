package com.nesh.apps;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserFactory;

public class FetchThread extends Thread{
	GillyCricketActivity g;
	public FetchThread(GillyCricketActivity g){
		
		this.g = g;
		
	}
	
	public void run(){
		int minutes = 5;
		while(true){
			notifyFeeds();
			try {
				Thread.sleep(minutes*60*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public void notifyFeeds(){
    	
    	
    	ArrayList<ArrayList<String>> news;
		try {
			news = readRSSDocument();

	    	for (int i = 0; i< news.size(); i++){
	    		ArrayList<String> n = news.get(i);
	    		String title = n.get(0);
	    		String url = n.get(1);
	    		//Toast.makeText(GillyCricketActivity.this, (String)title, Toast.LENGTH_SHORT);
	    		g.createNotif((String)title, "Gilly Cricket", (String) title ,url,i+1);
	    		
	    	}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
  
	public ArrayList<ArrayList<String>> readRSSDocument() throws Exception {

		RssParser parser = RssParserFactory.createDefault();
		Rss rss = parser.parse(new URL("http://static.cricinfo.com/rss/livescores.xml"));
		// Get all XML elements in the feed
		Collection items = rss.getChannel().getItems();
		ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
		if (items != null && !items.isEmpty()) {
			// Iterate over our main elements. Should have one for each article
			for (Iterator i = items.iterator(); i.hasNext(); System.out
					.println()) {
				Item item = (Item) i.next();
				ArrayList<String> b = new ArrayList<String>();
				b.add(item.getTitle().toString());
				b.add(item.getLink().toString());
				a.add(b);
				
				//System.out.println("Title: " + item.getTitle());
				//System.out.println("Link: " + item.getLink());
				//System.out.println("Description: " + item.getDescription());
			}

		}
		return a;
	}
}
