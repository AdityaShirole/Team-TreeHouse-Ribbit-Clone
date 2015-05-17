package com.weone.login.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.weone.login.R;
import com.weone.login.utils.ParseConstants;

public class ProfileActivity extends Activity {
	
	protected static final String TAG = ProfileActivity.class.getSimpleName();
	protected Uri uri;
	protected ImageView profilePic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		profilePic = (ImageView)findViewById(R.id.profilePic);
		
		uri = getUri();
		Picasso.with(ProfileActivity.this)
		.load(uri)
		.placeholder(R.drawable.avatar_empty)
		.error(R.drawable.avatar_empty)
		.resize(150, 150)
		.into(profilePic);
	}

	private Uri getUri() {
		Uri URL;
		ParseFile Picfile = ParseUser.getCurrentUser().getParseFile(ParseConstants.KEY_PROFILE_PIC);
		URL = Uri.parse(Picfile.getUrl());
		return URL;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
