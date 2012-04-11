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
		webview.loadUrl("http://neshkatrapati.github.com/Gilly-Cricket-Android/");
		FetchThread ft = new FetchThread(this);
		ft.start();
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
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;

		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		final int HELLO_ID = num;

		mNotificationManager.notify(HELLO_ID, notification);
	}



	
}