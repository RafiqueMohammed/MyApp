package arr.myapp;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import arr.myapp.ListViewCollection.AddNews;

public class NewsAdapter extends ArrayAdapter<ListViewCollection.AddNews> {
	Context c;
	int res;
	List<AddNews> mycoll;
	public NewsAdapter(Context context, int resource, int textViewResourceId,List<AddNews> coll) {
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
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		
		if(convertView!=null){
			holder=(ViewHolder) convertView.getTag();
		}else{
		LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView=inflater.inflate(res,parent,false);
		
		holder=new ViewHolder();
		holder.VH_title=(TextView) convertView.findViewById(R.id.LatestNews_title);
		holder.VH_desc=(TextView) convertView.findViewById(R.id.LatestNews_description);
		holder.VH_posted_on=(TextView) convertView.findViewById(R.id.LatestNews_posted_on);
		holder.VH_fav=(ImageView) convertView.findViewById(R.id.latest_news_addfav);
		convertView.setTag(holder);
		
		}
		
		
		holder.VH_title.setText(mycoll.get(position).getTitle());
		if(mycoll.get(position).getStatus()!=1){
			holder.VH_title.setTypeface(null,Typeface.NORMAL);
		}else{
			holder.VH_title.setTypeface(null,Typeface.BOLD);
		}
		if(mycoll.get(position).getFav()==1){
			holder.VH_fav.setImageResource(R.drawable.fav_28);
		}
		holder.VH_desc.setText(mycoll.get(position).getDescription());
		holder.VH_posted_on.setText(mycoll.get(position).getPosted_on());
		mycoll.get(position).get_id();
		
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView VH_title,VH_desc,VH_posted_on;
		ImageView VH_fav;
		
	}

}
