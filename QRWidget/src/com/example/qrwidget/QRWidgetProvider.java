package com.example.qrwidget;


import java.io.InputStream;






import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class QRWidgetProvider extends AppWidgetProvider {
	
	 private static final String SYNC_CLICKED    = "automaticWidgetSyncButtonClick";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget1);
        watchWidget = new ComponentName(context, QRWidgetProvider.class);
        //Llamo a funcion cuando hago click
        remoteViews.setOnClickPendingIntent(R.id.botonGenerar, getPendingSelfIntent(context, SYNC_CLICKED));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }
    
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (SYNC_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget1);
            watchWidget = new ComponentName(context, QRWidgetProvider.class);
            
            // Aca hago lo que tengo que hacer
         // These code snippets use an open-source library. http://unirest.io/java
            try {
				HttpResponse<InputStream> response = Unirest.post("https://neutrinoapi-qr-code.p.mashape.com/qr-code")
				.header("X-Mashape-Key", "<required>")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.field("bg-color", "#ffffff")
				.field("content", "http://www.neutrinoapi.com")
				.field("fg-color", "#000000")
				.field("height", 128)
				.field("width", 128)
				.asBinary();
				remoteViews.setTextViewText(R.id.botonGenerar, "Probando");
				
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            
            
            
            

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }
    /*
    public void pump(InputStream in,int size) {
        byte[] buffer = new byte[4096]; // Or whatever constant you feel like using
        int done = 0;
        while (done < size) {
            int read = in.read(buffer);
            if (read == -1) {
                throw new IOException("Something went horribly wrong");
            }
            out.write(buffer, 0, read);
            done += read;
        }
        // Maybe put cleanup code in here if you like, e.g. in.close, out.flush, out.close
    }*/


}
