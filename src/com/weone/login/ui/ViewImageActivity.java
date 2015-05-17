package com.weone.login.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.squareup.picasso.Picasso;
import com.weone.login.R;
import com.weone.login.R.id;
import com.weone.login.R.layout;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ViewImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image);
		
		
		ImageView imageView = (ImageView)findViewById(R.id.imageView);
		Uri imageUri = getIntent().getData();
		
		Picasso.with(this).load(imageUri.toString()).into(imageView);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				finish();
			}
		}, 50*1000);
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
