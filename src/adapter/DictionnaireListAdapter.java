package adapter;

import java.util.List;

import com.example.mobpactgertun.R;

import model.Tuc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class DictionnaireListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Tuc> dictItems;
	String res = "";

	public DictionnaireListAdapter(Activity activity, List<Tuc> dictItems) {
		super();
		this.activity = activity;
		this.dictItems = dictItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dictItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dictItems.get(position);
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
			convertView = inflater.inflate(R.layout.items_dictionnaire, null);
		TextView searchword = (TextView) convertView
				.findViewById(R.id.searchword);
		TextView meaning1 = (TextView) convertView.findViewById(R.id.meaning1);
		TextView meaning2 = (TextView) convertView.findViewById(R.id.meaning2);
		Tuc item = dictItems.get(position);
		meaning1.setText(item.getPhrase());
		searchword.setText(item.getSearchword());	
		if(item.getMeanings() != null)
		{
		for (int i = 0; i < item.getMeanings().size(); i++) {
			res=item.getMeanings().get(i).toString();
			
		}
		}
		meaning2.setText(res);
		return convertView;
	}

}
