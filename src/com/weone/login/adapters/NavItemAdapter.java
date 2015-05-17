package com.weone.login.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weone.login.R;

public class NavItemAdapter extends ArrayAdapter<String> {
	protected Context mContext;
	protected List<String> mItems;
	
	public NavItemAdapter(Context context, List<String> items) {
		super(context, R.layout.nav_item, items);
		mContext = context;
		mItems = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.nav_item, null);
			holder = new ViewHolder();
			holder.mNavItemIcon = (ImageView)convertView.findViewById(R.id.navItemIcon);
			holder.mNavItemTitle = (TextView)convertView.findViewById(R.id.navItemTitle);
			
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.mNavItemTitle.setText(mItems.get(position));
		
		if(mItems.get(position).equalsIgnoreCase("Edit Friends")) {
			holder.mNavItemIcon.setImageResource(R.drawable.ic_action_user_add_purple);
		}
		
		
		return convertView;
	}
	
	public static class ViewHolder {
		TextView mNavItemTitle;
		ImageView mNavItemIcon;
	};
	
}
