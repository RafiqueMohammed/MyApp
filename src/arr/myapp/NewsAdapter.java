package arr.myapp;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import arr.myapp.ListViewCollection.AddNews;

public class NewsAdapter extends ArrayAdapter<ListViewCollection.AddNews> {

	TextView title,desc,body,posted_on;
	int cloud_id,_id;
	View row;
	Context c;
	int res;
	List<AddNews> mycoll;
	public NewsAdapter(Context context, int resource, int textViewResourceId,
			List<AddNews> coll) {
		super(context, resource, textViewResourceId, coll);
		// TODO Auto-generated constructor stub
		this.c=context;
		this.res=resource;
		this.mycoll=coll;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mycoll.size();
	}
	@Override
	public AddNews getItem(int position) {
		// TODO Auto-generated method stub
		return mycoll.get(position);
	}
@Override
public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
}
public void setListData(List<AddNews> data){
	this.mycoll=data;
	notifyDataSetChanged();
	
}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView!=null){
			row=convertView;
		}else{
		
		LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row=inflater.inflate(res,parent,false);
		}
		title=(TextView) row.findViewById(R.id.LatestNews_title);
		desc=(TextView) row.findViewById(R.id.LatestNews_description);
		posted_on=(TextView) row.findViewById(R.id.LatestNews_posted_on);
		
		title.setText(mycoll.get(position).getTitle());
		desc.setText(mycoll.get(position).getDescription());
		posted_on.setText(mycoll.get(position).getPosted_on());
		_id=mycoll.get(position).get_id();
		
		
		return row;
	}

}
