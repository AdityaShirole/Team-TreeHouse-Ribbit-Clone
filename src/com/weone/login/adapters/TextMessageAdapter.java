package com.weone.login.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.weone.login.R;
import com.weone.login.utils.ParseConstants;

public class TextMessageAdapter extends ArrayAdapter<ParseObject>{
	
	protected Context mContext;
	protected List<ParseObject> mMessages;
	
	public TextMessageAdapter(Context context, List<ParseObject> messages) {
		super(context, R.layout.text_message_item, messages);
		mContext = context;
		mMessages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.text_message_item, null);
			holder = new Viewholder();
			holder.iconImageView = (ImageView)convertView.findViewById(R.id.textMessageIcon);
			holder.nameLabel = (TextView)convertView.findViewById(R.id.textSenderLabel);
			holder.timeLabel = (TextView)convertView.findViewById(R.id.textTimeLabel);
			holder.message = (TextView)convertView.findViewById(R.id.textMessage);
			convertView.setTag(holder);
		}
		else {
			holder = (Viewholder)convertView.getTag();
		}
		
		ParseObject message = mMessages.get(position);
		
		Date createdAt = message.getCreatedAt();
		long now = new Date().getTime();
		String convertedDate = DateUtils.getRelativeTimeSpanString(
				createdAt.getTime(),
				now,
				DateUtils.SECOND_IN_MILLIS).toString();
		
		holder.timeLabel.setText(convertedDate);
		
		holder.iconImageView.setImageResource(R.drawable.ic_picture);
		
		holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
		
		holder.message.setText(message.getString(ParseConstants.KEY_MESSAGE_TEXT));
		
		
		return convertView;
	}
	
	public static class Viewholder {
		ImageView iconImageView;
		TextView nameLabel;
		TextView message;
		TextView timeLabel;
	}
	
	public void refill(List<ParseObject> messages) {
		
		mMessages.clear();
		mMessages.addAll(messages);
		notifyDataSetChanged();
	}
}


