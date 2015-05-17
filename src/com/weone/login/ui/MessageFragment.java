package com.weone.login.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.weone.login.R;
import com.weone.login.adapters.MessageAdapter;
import com.weone.login.adapters.TextMessageAdapter;
import com.weone.login.utils.ParseConstants;

public class MessageFragment extends ListFragment implements OnClickListener{
	
	public static final int TYPE_TEXT = 1;
	private static final String MESSAGE_TYPE = null; 
	
	protected Button mSendText;
	protected EditText mMessageText;
	protected List<ParseObject> mTextMessages;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
		View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
		
		mSendText = (Button)rootView.findViewById(R.id.SendText);
		mMessageText = (EditText)rootView.findViewById(R.id.MessageText);
	
		return rootView;
	}
	
	@Override
	public void onResume() {
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		if(getListView() != null) {
			retrieveTextMessagesOffline();
			retrieveTextMessages();
		}else {
			Log.i(getTag(), "Listview not created yet!");
		}
		
		super.onResume();
	}
	
	private void retrieveTextMessagesOffline() {
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_TEXT_MESSAGES);
		query.fromLocalDatastore();
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				
				if(e == null) {
					//messages retrieved
					mTextMessages = messages;
					String[] textMessage = new String[mTextMessages.size()];
					
					int i = 0;
					for( ParseObject message : mTextMessages) {
						textMessage[i] = message.getString(ParseConstants.KEY_MESSAGE_TEXT);	
						i++;
					}
					
						if(getListView().getAdapter() == null) {
							TextMessageAdapter adapter = new TextMessageAdapter(
									getActivity(),
									mTextMessages);
							setListAdapter(adapter);
						}
						else {
							//refill the list
							( (TextMessageAdapter)getListView().getAdapter() ).refill(mTextMessages);
						}
					
				}
			}
		});
		
	}
	
	private void retrieveTextMessages() {
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_TEXT_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_IDS,
				ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		//query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
//				getActivity().setProgressBarIndeterminateVisibility(false);
//				
//				if(e == null) {
//					//messages retrieved
//					mTextMessages = messages;
//					String[] textMessage = new String[mTextMessages.size()];
//					
//					int i = 0;
//					for( ParseObject message : mTextMessages) {
//						textMessage[i] = message.getString(ParseConstants.KEY_MESSAGE_TEXT);	
//						i++;
//					}
//					
//					if(getListView().getAdapter() == null) {
//						TextMessageAdapter adapter = new TextMessageAdapter(
//								getActivity(),
//								mTextMessages);
//						setListAdapter(adapter);
//					}
//					else {
//						//refill the list
//						( (TextMessageAdapter)getListView().getAdapter() ).refill(mTextMessages);
//					}
//
//				}
				
				if( e == null) {
					//success
					
					
					
					ParseObject.pinAllInBackground(ParseConstants.CLASS_TEXT_MESSAGES, messages, new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							
							if(e == null) {
								Log.i(getTag(), "Text Messages Pinned in background");
							}
							else {
								Log.e(getTag(), e.getMessage());
							}
						}
					});
					
					//messages retrieved
					mTextMessages = messages;
					String[] textMessage = new String[mTextMessages.size()];
					
					int i = 0;
					for( ParseObject message : mTextMessages) {
						textMessage[i] = message.getString(ParseConstants.KEY_MESSAGE_TEXT);	
						i++;
					}
					
					if(getListView().getAdapter() == null) {
						TextMessageAdapter adapter = new TextMessageAdapter(
								getActivity(),
								mTextMessages);
						setListAdapter(adapter);
					}
					else {
						//refill the list
						( (TextMessageAdapter)getListView().getAdapter() ).refill(mTextMessages);
					}
				
					
				}
				else {
					//error
					Log.e(getTag(), e.getMessage());
				}
			}
		});
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		
		
		mSendText.setOnClickListener(this);
		
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		
		String text = mMessageText.getText().toString();
		
		if(text != null){
		
			Intent messageIntent = new Intent(getActivity(), RecipientsActivity.class);
			messageIntent.putExtra("MESSAGE_TYPE", "TEXT");
			messageIntent.putExtra(ParseConstants.KEY_MESSAGE_TEXT,text);
			Log.i(getTag(), "Starting recipients activity from message fragment");
			mMessageText.setText("");
			startActivity(messageIntent);
			retrieveTextMessages();
		}
		else {
			Toast.makeText(getActivity(), "Message cannot be empty", Toast.LENGTH_LONG).show();
		}
	}
	
	 protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				//Toast.makeText(getActivity(), "We're refreshing!", Toast.LENGTH_SHORT).show();
				retrieveTextMessages();
			}
		};
	
}
