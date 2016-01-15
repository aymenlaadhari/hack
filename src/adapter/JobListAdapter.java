package adapter;

import java.util.List;

import com.example.mobpactgertun.R;

import model.Job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class JobListAdapter extends BaseAdapter {

	private List<Job> jobs;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jobs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return jobs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(
					R.layout.list_item_title_navigation, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
		
		Job item = jobs.get(position);
		txtTitle.setText(item.getType());
		

		return convertView;
	}

}
