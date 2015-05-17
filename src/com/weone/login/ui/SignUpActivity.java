package com.weone.login.ui;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.weone.login.LoginApplication;
import com.weone.login.R;
import com.weone.login.R.id;
import com.weone.login.R.layout;
import com.weone.login.R.menu;
import com.weone.login.R.string;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity {
	
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mSignUpButton;
	protected Button mCancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		

		//hide the action bar. not needed for login and signup screens
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		mUsername=(EditText)findViewById(R.id.signup_usernameField);
		mPassword=(EditText)findViewById(R.id.signup_passwordField);
		mEmail=(EditText)findViewById(R.id.emailField);
		mCancelButton = (Button)findViewById(R.id.cancelButton);
		mSignUpButton=(Button)findViewById(R.id.signupButton);
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username=mUsername.getText().toString();
				String password=mPassword.getText().toString();
				String email=mEmail.getText().toString();
				
				username=username.trim();
				password=password.trim();
				email=email.trim();
				
				if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
					AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this);
					builder.setMessage(R.string.signup_error_message);
					builder.setTitle(R.string.signup_error_title);
					builder.setPositiveButton(android.R.string.ok, null);
					
					AlertDialog dialog=builder.create();
					dialog.show();
				
				}else{
					//create new user
					ParseUser newUser =new ParseUser();
					newUser.setUsername(username);
					newUser.setEmail(email);
					newUser.setPassword(password);
					newUser.put("ProfilePicUrl", "null");
					newUser.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e==null){
								//Success!! woohooo
								
								LoginApplication.updateParseInstallation(ParseUser.getCurrentUser());
								Intent intent =new Intent(SignUpActivity.this,MainActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}else{
								//error at parse.com or failed some test
								AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this);
								builder.setMessage(e.getMessage());
								builder.setTitle(R.string.signup_error_title);
								builder.setPositiveButton(android.R.string.ok, null);
								
								AlertDialog dialog=builder.create();
								dialog.show();
							}
							
						}
					});
					
				}
				
				
			}
		});
	
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
