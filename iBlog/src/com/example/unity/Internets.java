package com.example.unity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class Internets {
	public boolean isConnection(Context con){
		ConnectivityManager vm=(ConnectivityManager) con.getSystemService(con.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo[]=vm.getAllNetworkInfo();
		for (int i = 0; i < netInfo.length; i++) {
			if(netInfo[i].getState()==State.CONNECTED){
				return true;
			}
		}
		return false;
	}
}
 