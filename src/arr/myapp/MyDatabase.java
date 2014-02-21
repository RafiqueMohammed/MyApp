package arr.myapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import arr.myapp.ListViewCollection.AddNews;

public class MyDatabase extends SQLiteOpenHelper {

	public static final String DB_NAME="aitj";
	public static final int DB_VERSION=5;
	public static final String TAB_NEWS="latest_news";
	public static final String TAB_CONTACTS="contacts";
	public static final String FLD_ID="_id";
	public static final String FLD_CLOUD_ID="cloud_id";
	
final Context c;
	public MyDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.c=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String query="CREATE TABLE "+TAB_NEWS+"("+FLD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+FLD_CLOUD_ID+" INTEGER UNIQUE,title VARCHAR(700),description VARCHAR(700),body TEXT,status INTEGER(1) DEFAULT 0,fav INTEGER(1) DEFAULT 0,posted_on DATETIME DEFAULT CURRENT_TIMESTAMP);";
		try {
			db.execSQL(query);
			Toast.makeText(c,"Table created",Toast.LENGTH_LONG).show();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(c,"SQLException :"+e.toString(),Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//List<AddNews> bc_data=new ArrayList<ListViewCollection.AddNews>();
		try {
			//bc_data= MyDatabase.takeBackup(db);
			db.execSQL("DROP TABLE IF EXISTS "+TAB_NEWS+";");
			
			//MyDatabase.writeBackup(bc_data, db);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(c,"SQLException :"+e.toString(),Toast.LENGTH_LONG).show();
				}
		
		onCreate(db);

	}
	
	public static List<AddNews> takeBackup(SQLiteDatabase db){
		String[] cols={"cloud_id","_id","title","description","posted_on","status"};
		
		Cursor cur=db.query(TAB_NEWS, cols, null, null, null, null, null);
		List<AddNews> addnews = new ArrayList<ListViewCollection.AddNews>();
		addnews.clear();
		while (cur.moveToNext()) {
			addnews.add(new AddNews(cur.getInt(0),cur.getInt(1), cur.getString(2), cur.getString(3), cur.getString(4),cur.getInt(5),0));
			
		}
		cur.close();
		return addnews;
		
	}
	
	public static boolean writeBackup(List<AddNews> addnews,SQLiteDatabase db){
		Log.d("BACKUP","Length:"+addnews.size());
		ContentValues datavalue=new ContentValues();
		for(int i=0;i<addnews.size();i++){
			Log.d("BACKUP","id: "+addnews.get(i)._id);
			datavalue.clear();
			datavalue.put("cloud_id",addnews.get(i).cloud_id);
			datavalue.put("_id",addnews.get(i)._id);
			datavalue.put("title",addnews.get(i).title);
			datavalue.put("description",addnews.get(i).description);
			datavalue.put("posted_on",addnews.get(i).posted_on);
			datavalue.put("status",addnews.get(i).status);
			datavalue.put("fav",addnews.get(i).fav);
			db.insert(TAB_NEWS, null, datavalue);
		}
		return true;
	}

}
