package com.example.wifiboundservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class WifiBoundService extends Service {

	private final IBinder myBinder = new MyWifiLocalBinder();
	private static String TAG = "BoundService";
	  
	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}

	//service call to get wifi connection
	  public String getWifiConnection(){
	    String service = Context.WIFI_SERVICE;
	    final WifiManager wifi = (WifiManager) getSystemService(service);
	        
	    WifiInfo info;
	    
	    try{
	      info = wifi.getConnectionInfo();
	    }catch(Exception e){
	      Log.d(TAG,e.getMessage());
	      return "No Wifi Connection";
	    }
	        
	    String out = "";    
	    
	    //info data exists
	    if(info.getBSSID() != null){
	      int strength = WifiManager.calculateSignalLevel(info.getRssi(),100);
	      int speed = info.getLinkSpeed();
	      String units = WifiInfo.LINK_SPEED_UNITS;
	      String ssid = info.getSSID();
	      String bssid = info.getBSSID();
	      String mac = info.getMacAddress();
	      boolean bHiddenssid = info.getHiddenSSID();
	      int netID = info.getNetworkId();
	      
	      out = "Strength: " + Integer.toString(strength) + "\n" +
	        "Speed:    " + Integer.toString(speed) + " " + units + "\n" +
	        "SSID:     " + ssid  + "\n" +
	        "BSSID:    " + bssid  + "\n" +
	        "MAC Addr: " + mac  + "\n" +
	        "Hidden:     " + Boolean.toString(bHiddenssid)  + "\n" +
	        "NetID:     " + Integer.toString(netID)  + "\n";

	    }
	    //no info data
	    else{
	      out = "Wifi Connection not found!";
	    }    
	    
	    return out;
	  }
	  
	  //service call returns current time date
	  public String getCurrentTime(){
	    SimpleDateFormat dateformat =
	        new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US);
	    return (dateformat.format(new Date()));
	  }
	  
	  //service call returns date
	  public String getCurrentDate(){
	    SimpleDateFormat dateformat =
	        new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	    return (dateformat.format(new Date()));    
	  }
	  
	  //public class MyLocalBinder extends Binder{
	  public class MyWifiLocalBinder extends Binder{
	    WifiBoundService getService(){
	      return WifiBoundService.this;
	    }
	  }
}
