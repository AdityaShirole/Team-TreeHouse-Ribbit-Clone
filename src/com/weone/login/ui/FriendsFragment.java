package com.weone.login.ui;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.weone.login.LoginApplication;
import com.weone.login.R;
import com.weone.login.adapters.UserAdapter;
import com.weone.login.utils.ParseConstants;

public class FriendsFragment extends Fragment {
	
	protected static final String TAG = FriendsFragment.class.getSimpleName();
	
	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendRelations;
	protected ParseUser mCurrentUser;
	protected GridView mGridView;
	
	 @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.user_grid, container, false);
         
         mGridView = (GridView)rootView.findViewById(R.id.friendsGrid);
         TextView emptyTextView = (TextView)rootView.findViewById(android.R.id.empty);
         mGridView.setEmptyView(emptyTextView);
         mGridView.setOnItemClickListener(mOnItemClicklistener);
         //getFriendsFromParse();
         
         
         
         return rootView;
     }
	 
	 public boolean isOnline() {
		 ConnectivityManager cm =
			        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

			    return cm.getActiveNetworkInfo() != null && 
			       cm.getActiveNetworkInfo().isConnectedOrConnecting();
		 
	 }
	 
	 @Override
		public void onResume() {
			super.onResume();
			
			mCurrentUser = ParseUser.getCurrentUser();
			mFriendRelations = mCurrentUser
						.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
			//getFriendsFromParse();
			getFriendsLocally();
			if(isOnline()) {
				Log.i(TAG, "parse was called.");
				getFriendsFromParse();
			}
			/*	
			mCurrentUser = ParseUser.getCurrentUser();
			mFriendRelations = mCurrentUser
					.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
			
			getActivity().setProgressBarIndeterminateVisibility(true);
			
			ParseQuery<ParseUser> query = mFriendRelations.getQuery();
			query.addAscendingOrder(ParseConstants.KEY_USERNAME);
			query.findInBackground(
					new FindCallback<ParseUser>() {

						@Override
						public void done(List<ParseUser> friends, ParseException e) {
							getActivity().setProgressBarIndeterminateVisibility(false);
							mFriends = friends;
							
							if(e == null){
								String[] usernames = new String[mFriends.size()];
								int i = 0;
								for (ParseUser user : mFriends) {
									usernames[i] = user.getUsername();
									i++;
								}
								
								if(mGridView.getAdapter() == null) {
									UserAdapter adapter = new UserAdapter(getActivity(), mFriends);
									mGridView.setAdapter(adapter);
								}
								else {
									( (UserAdapter)mGridView.getAdapter() ).refill(mFriends);
								}
								
								ParseObject.pinAllInBackground(ParseConstants.PIN_FRIENDS, friends, new SaveCallback() {
									
									@Override
									public void done(ParseException e) {
										if(e == null) {
											//success
											Log.i(TAG, "Friends have been pinned");
										}
										else {
											Log.e(TAG, "Friends not pinned.");
										}
										
									}
								});
								
							}
							else{
								Log.e(TAG, e.getMessage());

								AlertDialog.Builder builder = new AlertDialog.Builder(
										getActivity());
								builder.setMessage(e.getMessage());
								builder.setTitle(R.string.error_title);
								builder.setPositiveButton(android.R.string.ok, null);

								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
					*/
			
	 }

	 public void getFriendsLocally() {
		 //getActivity().setProgressBarIndeterminateVisibility(true);
		 
		 ParseQuery<ParseUser> query = mFriendRelations.getQuery();
		 query.fromLocalDatastore();
		 query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		 query.findInBackground(
				new FindCallback<ParseUser>() {
					

					@Override
					public void done(List<ParseUser> friends, ParseException e) {
						//getActivity().setProgressBarIndeterminateVisibility(false);
						mFriends = friends;
						
						if(e == null){
							String[] usernames = new String[mFriends.size()];
							int i = 0;
							for (ParseUser user : mFriends) {
								usernames[i] = user.getUsername();
								i++;
							}
							
								if(mGridView.getAdapter() == null) {
									UserAdapter adapter = new UserAdapter(getActivity(), mFriends);
									mGridView.setAdapter(adapter);
								}
								else {
									( (UserAdapter)mGridView.getAdapter() ).refill(mFriends);
								}
									
							}
							else{
								Log.e(TAG, e.getMessage());

							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setMessage(e.getMessage());
							builder.setTitle(R.string.error_title);
							builder.setPositiveButton(android.R.string.ok, null);

							AlertDialog dialog = builder.create();
							dialog.show();
						}
					}
				});
	 }
	 
	public void getFriendsFromParse() {
		//getActivity().setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseUser> query = mFriendRelations.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		query.findInBackground(
				new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> friends, ParseException e) {
						//getActivity().setProgressBarIndeterminateVisibility(false);
						mFriends = friends;
						
						if(e == null){
							String[] usernames = new String[mFriends.size()];
							int i = 0;
							for (ParseUser user : mFriends) {
								usernames[i] = user.getUsername();
								i++;
							}
							
							if(mGridView.getAdapter() == null) {
								UserAdapter adapter = new UserAdapter(getActivity(), mFriends);
								mGridView.setAdapter(adapter);
							}
							else {
								( (UserAdapter)mGridView.getAdapter() ).refill(mFriends);
							}
							
							ParseObject.pinAllInBackground(ParseConstants.PIN_FRIENDS, friends, new SaveCallback() {
								
								@Override
								public void done(ParseException e) {
									if(e == null) {
										//success
										Log.i(TAG, "Friends have been pinned");
									}
									else {
										Log.e(TAG, "Friends not pinned.");
									}
									
								}
							});
							
						}
						else{
							Log.e(TAG, e.getMessage());

							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity());
							builder.setMessage(e.getMessage());
							builder.setTitle(R.string.error_title);
							builder.setPositiveButton(android.R.string.ok, null);

							AlertDialog dialog = builder.create();
							dialog.show();
						}
					}
				});
	}
	 
	
	protected OnItemClickListener mOnItemClicklistener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			
		}
	};
	 
}
