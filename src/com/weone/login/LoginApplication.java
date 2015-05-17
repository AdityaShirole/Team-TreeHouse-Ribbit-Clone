package com.weone.login;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.weone.login.ui.MainActivity;
import com.weone.login.utils.ParseConstants;

public class LoginApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.enableLocalDatastore(this);
		ParseCrashReporting.enable(this);
		 

		Parse.initialize(this, "06Uj6XHUjNNwEhEmvpiygvSTHpPgTLs5sK1j9iXb",
				"qDyGhhk3adfQbRN6EhYEf3eQK69151K8dtGUnF2C");

		ParsePush.subscribeInBackground("", new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d("com.parse.push",
							"successfully subscribed to the broadcast channel.");
				} else {
					Log.e("com.parse.push", "failed to subscribe for push", e);
				}
			}
		});
		PushService.setDefaultPushCallback(this, MainActivity.class);
		// ParseInstallation.getCurrentInstallation().saveInBackground();
		
	}
	
	 public static void updateParseInstallation (ParseUser user) {
		 ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		 installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
		 installation.saveInBackground();
	 }
	 
	 
}

