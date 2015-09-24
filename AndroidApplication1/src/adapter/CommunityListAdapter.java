package adapter;

import java.util.List;

import com.example.mobpactgertun.R;

import model.CommunityItems;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityListAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private List<CommunityItems> communityItems;
	
	

	public CommunityListAdapter(Activity activity,
			List<CommunityItems> communityItems) {
		super();
		this.activity = activity;
		this.communityItems = communityItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return communityItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return communityItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.community_item, null);
		TextView name = (TextView) convertView.findViewById(R.id.communityname);
		TextView created = (TextView) convertView.findViewById(R.id.created);
		ImageView image = (ImageView) convertView.findViewById(R.id.imagecommunity);
		
		CommunityItems item = communityItems.get(position);
		
		name.setText(item.getName());
		created.setText(item.getCreated());
		image.setImageResource(item.getImage());
		
		return convertView;
	}
	
	

}
