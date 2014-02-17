package arr.myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import arr.myapp.ListViewCollection.AddNews;

public class LatestNews extends Activity {
	ListView newsList;
	CheckInternet ci;
	Context con;
	final String NEWS_URL = "http://api.aitj.org/api/v1/?android";
	List<AddNews> addnews = new ArrayList<ListViewCollection.AddNews>();
	static MyDatabase mydb;
	SQLiteDatabase db;
	FetchLatestNews fn;
	NewsAdapter adapt;
	Cursor cur;
	
	
	public static final int LOADER_ID=0x01;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_latest_news);
		newsList = (ListView) findViewById(R.id.LatestNewsView);
		mydb = new MyDatabase(this);
		con = LatestNews.this;
			populateLatestNewsList();

			registerForContextMenu(newsList);
		
		newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				int item_id=addnews.get(pos)._id;
				Intent view_news=new Intent(LatestNews.this,ViewLatestNews.class);
				view_news.putExtra("item_id",item_id);
				startActivity(view_news);
			}
	
			
		});
		
			
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.latest_news_context, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo cont=(AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()){
		case R.id.latest_news_context_delete:
			
			if(MyMethods.DeleteItemFromListView(newsList.getAdapter(), cont)){
				Toast.makeText(this,"Successfully deleted",Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this,"Cannot be deleted",Toast.LENGTH_LONG).show();
			}
			return true;
		case R.id.latest_news_sendmail:
			Log.d("ARR","Send Mail was clicked");
			return true;
		case R.id.latest_news_addfav:
			NewsAdapter item_news=(NewsAdapter)newsList.getAdapter();
			item.setTitle("Remove from Fav");
			item.setVisible(false);
			db= mydb.getWritableDatabase();
			ContentValues up_val=new ContentValues();
			up_val.put("fav",1);
			String[] wArg={""+item_news.getItem(cont.position)._id};
			int result=db.update(MyDatabase.TAB_NEWS,up_val,"_id=?", wArg);
			if(result>0){
				Log.d("ARR","-"+item_news.getItem(cont.position).fav+" id:"+item_news.getItem(cont.position)._id+" res:"+result);
				String[] col={"_id","title","fav","status"};
				Cursor c=db.query(MyDatabase.TAB_NEWS,col,"_id=?", wArg,null,null,null);
				if(c.getCount()>0){
					for(int cr=0;cr<c.getCount();cr++){
						Log.d("ARR","Cursor : id:"+c.getInt(0)+" fav:"+c.getInt(2)+" title:"+c.getString(1)+" status:"+c.getInt(3));
						
					}
				}else{
					Log.d("ARR","Not found");
				}
				c.close();
				ImageView favicon=(ImageView) findViewById(R.id.latest_news_addfav);
				favicon.setImageResource(R.drawable.fav_28);
				adapt.notifyDataSetChanged();
				
				MyMethods.ShowAlert(this,"Success","Successfully added to your favourite", false,true);
				}else{
					MyMethods.ShowAlert(this,"Failed","Unable to add to your favourite", false,false);
						}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.lastest_news_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.latest_news_top_menu:
			
			ci = new CheckInternet(LatestNews.this);
			if (!ci.isConnectingToInternet()) {
				ci.showAlertDialog(LatestNews.this, "No Internet Connection","Your internet connection is disabled. Please enable it.",
						false);

			} else {
				fn= new FetchLatestNews();
				fn.execute();
		
			}
			return true	;
		default:return super.onOptionsItemSelected(item) ;
		}

		
	}

	public void populateLatestNewsList()
		 {
	
		String qry = "SELECT _id,title,description,posted_on,status,cloud_id FROM "+MyDatabase.TAB_NEWS+" ORDER BY "+MyDatabase.FLD_ID+" DESC;";
		SQLiteDatabase db = mydb.getReadableDatabase();
		cur= db.rawQuery(qry, null);
		addnews.clear();
		
		while (cur.moveToNext()) {
			addnews.add(new AddNews(cur.getInt(5),cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3),cur.getInt(4),0));
			
		} 
	
		adapt = new NewsAdapter(this,R.layout.latest_news_singleview, R.id.LatestNews_title,addnews);
		TextView empty_view=new TextView(LatestNews.this);
		newsList.setAdapter(adapt);
		empty_view.setText("Your News Box Is Empty!");
		newsList.setEmptyView(empty_view);
		
		}


	public void InjectIntoDB(String result) throws JSONException {

		db= mydb.getWritableDatabase();
		JSONArray jArray = new JSONArray(result);
		int total = jArray.length();
		JSONObject jObj;
		String title, desc, posted_on,body;
		int cloud_id = 0;
		JSONObject _error= new JSONObject();
		ContentValues cv=new ContentValues();
			for (int i = 0; i < total; i++) {
	cv.clear();
		jObj = jArray.getJSONObject(i);

		title = jObj.getString("title").toString();
		desc = jObj.getString("description").toString();
		posted_on = jObj.getString("posted_on").toString();
		cloud_id = jObj.getInt("cloud_id");
		body= jObj.getString("body").toString();
	
		cv.put(MyDatabase.FLD_CLOUD_ID,cloud_id);
		cv.put("title",title);
		cv.put("description",desc);
		cv.put("body",body);
		cv.put("posted_on",posted_on);
		cv.put("status",1);
		cv.put("fav",0);
		
						try {
			
						 db.insert(MyDatabase.TAB_NEWS,"-", cv);
						} catch (Exception e) {
							_error.put("Exception",e.toString()+"##CLOUD_ID:"+cloud_id);
							// TODO Auto-generated catch block
						}
			
			} // End of for loop
			
			populateLatestNewsList();
		
			SharedPreferences sp=getSharedPreferences("latest_news", Context.MODE_PRIVATE);
			
			
			SharedPreferences.Editor edit=sp.edit();
			edit.putInt("last_cloud_id",cloud_id);
			edit.commit();
			
			int getSP=sp.getInt("last_cloud_id",-1);
			Toast.makeText(con,"SharedPrefs "+getSP, Toast.LENGTH_LONG).show();
			
			
	}

	public class FetchLatestNews extends AsyncTask<String, Integer, String[]> {
		

		StringBuilder content = new StringBuilder();
		String[] result = new String[3];
		BufferedReader bf;

		ProgressDialog pd = new ProgressDialog(con);

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			pd.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result[0] == "no") {
				Toast.makeText(con, result[1].toString(), Toast.LENGTH_LONG).show();
			} else if (result[0] == "ok") {
				try {
					InjectIntoDB(result[2].toString());
					
					// populateLatestNewsList();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(con,"JSONException : "+e.toString(), Toast.LENGTH_LONG).show();
				}
			}

			pd.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

			super.onPreExecute();
			pd.setTitle("Please wait..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Checking and Retrieving Latest updates..");
			pd.show();

		}

		@Override
		protected String[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpParams param= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(param,30000);
			HttpConnectionParams.setSoTimeout(param, 30000);
			HttpClient http = new DefaultHttpClient(param);
			HttpGet send = new HttpGet(NEWS_URL);
			
			
			

			try {
				HttpResponse rcvd = http.execute(send);

				if (rcvd.getStatusLine().getStatusCode() != 200) {
					result[0] = "no";
					result[1] = "Server Error : "
							+ rcvd.getStatusLine().getStatusCode();
					return result;
				}
				InputStream in = rcvd.getEntity().getContent();
				bf = new BufferedReader(new InputStreamReader(in));
				String tmp = "";

				while ((tmp = bf.readLine()) != null) {
					content.append(tmp + "\n");
				}

				result[0] = "ok";
				result[1] = "Content Recieved Successfully";
				result[2] = content.toString();

				in.close();
				bf.close();
				return result;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				result[0] = "no";
				result[1] = "ClientProtocolException : " + e.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				result[0] = "no";
				result[1] = "IOException : " + e.toString();
			} finally {
				try {
					bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					result[0] = "no";
					result[1] = "Buffered Cannot be closed : " + e.toString();
				}
			}

			return result;
		}

	}





}
