package arr.myapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import arr.myapp.MainActivity.HomeMenuListColl;

public class MyAdapter extends ArrayAdapter<HomeMenuListColl> {

	Context c;
	TextView ttitle;
	ImageView iicons;
	View myview;
	int res;
	List<HomeMenuListColl> data;

	public MyAdapter(Context context, int resource, List<HomeMenuListColl> data) {
		super(context, resource, data);
		// TODO Auto-generated constructor stub

		this.c = context;
		this.res = resource;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public void setData(List<HomeMenuListColl> data) {
		this.data = data;
	}


@Override
public HomeMenuListColl getItem(int position) {
	// TODO Auto-generated method stub
	return data.get(position);
}
@Override
public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView != null) {
			myview = convertView;
		} else {
			LayoutInflater inflater = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myview = inflater.inflate(R.layout.all_list_layout, parent, false);
		}
		ttitle = (TextView) myview.findViewById(R.id.homeMenu_title_layout);
		iicons = (ImageView) myview.findViewById(R.id.homeMenu_icon_layout);

		ttitle.setText(data.get(position).getTitle());
		iicons.setImageResource(this.data.get(position).getIcons());

		return myview;
	}

}
