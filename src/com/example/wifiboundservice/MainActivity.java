package com.example.wifiboundservice;

import com.example.wifiboundservice.WifiBoundService.MyWifiLocalBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static String TAG = "MainActivity";  
	WifiBoundService myService;
	boolean isBound = false;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(this, WifiBoundService.class);
	    bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
	}
	
	//creates new service connection
	  private ServiceConnection myConnection = new ServiceConnection(){
	    
	    public void onServiceConnected(ComponentName className, IBinder service){
	      MyWifiLocalBinder binder = (MyWifiLocalBinder) service;
	      myService = binder.getService();
	      isBound = true;
	    }
	    
	    public void onServiceDisconnected(ComponentName arg0){
	      isBound = false;
	    }
	  };
	  
	  //"ShowWifi" onClick()
	  public void showWifiConnection(View view){
	    String wifi = myService.getWifiConnection();
	    TextView myTextView = (TextView)findViewById(R.id.myTextView);
	    myTextView.setText(wifi);    
	  }
	  
	  //"ShowTime" onClick()
	  public void showTime(View view){
	    String currentTime = myService.getCurrentDate();    
	    TextView myTextView = (TextView)findViewById(R.id.myTextView);
	    myTextView.setText(currentTime);
	  }
}
