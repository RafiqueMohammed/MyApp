package arr.myapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabase extends SQLiteOpenHelper {

	public static final String DB_NAME="aitj";
	public static final int DB_VERSION=2;
	public static final String TAB_NEWS="latest_news";
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

		String query="CREATE TABLE "+TAB_NEWS+"("+FLD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+FLD_CLOUD_ID+" INTEGER UNIQUE,title VARCHAR(700),description VARCHAR(700),body TEXT,posted_on DATETIME,status TINYINT(1),enabled TINYINT(1));";
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
		try {
			db.execSQL("DROP TABLE IF EXISTS "+TAB_NEWS+";");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Toast.makeText(c,"SQLException :"+e.toString(),Toast.LENGTH_LONG).show();
				}
		onCreate(db);

	}

}
