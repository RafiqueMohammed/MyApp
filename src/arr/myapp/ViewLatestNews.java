package arr.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewLatestNews extends Activity {

	TextView title, posted, body;
	SQLiteDatabase db;
	MyDatabase mydb;
	int item_id = 0;
	Cursor cur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_latest_news);
		Intent intent = getIntent();
		this.item_id = intent.getIntExtra("item_id", 0);

		if (item_id == 0) {
			MyMethods.ShowAlert(this,"Sorry!","Your requested content is either invalid or removed", true);
			
		} else {
			this.mydb = new MyDatabase(this);
			this.db = mydb.getReadableDatabase();
			String[] args = { "" + item_id };
			this.cur = db.rawQuery("SELECT title,body,posted_on FROM "+ MyDatabase.TAB_NEWS + " WHERE _id=?", args);
			int num_rows=cur.getCount();
			
			if(num_rows==0){
				MyMethods.ShowAlert(this,"Not Found!","Content cannot be found!", true);		
			}else{
				title=(TextView) findViewById(R.id.view_latest_news_title);
				posted=(TextView) findViewById(R.id.view_latest_news_posted_on);
				body=(TextView) findViewById(R.id.view_latest_news_body);
				while(cur.moveToNext()){
					title.setText(cur.getString(0));
					posted.setText(cur.getString(2));
					body.setText(cur.getString(1));
				}
				cur.close();
				db.close();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_latest_news, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_SEND);
		
		switch(item.getItemId()){
		case R.id.ViewNews_share_with:
			intent.putExtra(Intent.EXTRA_TEXT,body.getText());
			intent.setType("text/plain");
			startActivity(Intent.createChooser(intent,getResources().getString(R.string.salaam_ta)));
			return true;
		case R.id.ViewNews_send_email: 
			MyMethods.SendMail(this,title.getText(),body.getText());
			return true;
		default:return super.onOptionsItemSelected(item); 
		}
		
	}

}
