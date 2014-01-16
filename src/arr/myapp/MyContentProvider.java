package arr.myapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

public class MyContentProvider extends ContentProvider {

	static final String AUTH="content://arr.myapp.MyContentProvider/"+MyDatabase.TAB_NEWS;
	static final Uri AuthUri=Uri.parse(AUTH);
	static final int uriTabNews=1;
	SQLiteDatabase db;
	MyDatabase dbhelper;
	

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbhelper=new MyDatabase(getContext());
		if(dbhelper==null){
			return false;
		}else{
			return true;
		}
		
	}
static UriMatcher uriMatcher;
static{
	uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
	uriMatcher.addURI(AUTH,MyDatabase.TAB_NEWS,uriTabNews);
}

	@Override
	public int delete(Uri uri, String query, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues cv) {
		
		if(uriMatcher.match(uri)==uriTabNews){
			db=dbhelper.getWritableDatabase();
			long last_row=db.insert(MyDatabase.TAB_NEWS,"-",cv);
			db.close();
			getContext().getContentResolver().notifyChange(uri,null);
				return ContentUris.withAppendedId(uri,last_row);
		}else{
			return null;
		}
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cursor query(Uri uri, String[] column, String where,
			String[] whereArgs, String sortOrder) {
		
		
		// TODO Auto-generated method stub
		db=dbhelper.getReadableDatabase();
		Cursor cur=null;
        String orderBy=TextUtils.isEmpty(sortOrder)? "_id DESC" : sortOrder;
		try{
        if(uriMatcher.match(uri)==uriTabNews){
        	cur=db.query(MyDatabase.TAB_NEWS, column, null, null, null, null, orderBy);
        	cur.setNotificationUri(getContext().getContentResolver(), uri);
        	
        	 return cur;
        }
		}catch(IllegalArgumentException e){
			Toast.makeText(getContext(), "IllegalArgument in query : "+e.toString(),Toast.LENGTH_LONG).show();
		}
		 return cur;
       
	}

	@Override
	public int update(Uri uri, ContentValues cv, String where,
			String[] whereArgs) {
		// TODO Auto-generated method stub
		db=dbhelper.getWritableDatabase();
		int last_row=db.update(MyDatabase.TAB_NEWS,cv,where,whereArgs);
					
		return last_row;
	}

}
