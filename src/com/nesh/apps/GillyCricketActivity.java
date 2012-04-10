package com.nesh.apps;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserFactory;

public class GillyCricketActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WebView webview = (WebView) findViewById(R.id.webview_compontent);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("https://github.com/neshkatrapati/GillyCricket/blob/master/README.md");
		notifyFeeds();
	}

	public void createNotif(String tickerText, String contentTitle,
			String contentText,String URL,int num) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.ic_launcher;

		long when = System.currentTimeMillis();

		
	    Uri u = Uri.parse(URL); 
	    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, u);
	    
		Notification notification = new Notification(icon, tickerText, when);
		Context context = getApplicationContext();
		Intent notificationIntent = launchBrowser;
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		
		notification.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_AUTO_CANCEL;

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		final int HELLO_ID = num;

		mNotificationManager.notify(HELLO_ID, notification);
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
	    		createNotif((String)title, "Gilly Cricket", (String) title ,url,i+1);
	    		
	    	}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		}
  

	public static ArrayList<ArrayList<String>> readRSSDocument() throws Exception {

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