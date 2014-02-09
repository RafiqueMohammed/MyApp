package arr.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListAdapter;
import arr.myapp.ListViewCollection.AddNews;

public class MyMethods {
	static MyDatabase mydb;
	
	public static Activity activity;
	static Intent intent=new Intent();
	public static void ShowAlert(Activity act,String title,String msg,Boolean finishOnOK){
		MyMethods.activity=act;
		AlertDialog.Builder d = new AlertDialog.Builder(act);
		d.setCancelable(false);
		d.setTitle(title);
		d.setIcon(R.drawable.stop_32);
		d.setMessage(msg);
		if(finishOnOK){
			d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					MyMethods.activity.finish();
				}
			});
		}else{
			d.setPositiveButton("OK",null);
		}

		d.show();
	}
	
	public static void SendMail(Context con,CharSequence title,CharSequence body){
		intent.putExtra(Intent.EXTRA_SUBJECT,title);
		intent.putExtra(Intent.EXTRA_TEXT,body);
		intent.setType("message/rfc822");
		con.startActivity(Intent.createChooser(intent,"Send mail using"));
	}
	
	public static boolean DeleteItemFromListView(ListAdapter ListAdapt,AdapterContextMenuInfo itemview){
		NewsAdapter news_adapt=(NewsAdapter) ListAdapt;
		Log.d("ARR","DB Item ID :" +news_adapt.getItem(itemview.position)._id+" and View ID "+news_adapt.getItem(itemview.position));
		if(MyMethods.RemoveFromLatestNewsDB(news_adapt.getItem(itemview.position)._id)){
			news_adapt.remove(news_adapt.getItem(itemview.position));
			news_adapt.notifyDataSetChanged();
			return true;
		}else{
			return false;
		}
		
		
	}
	
	public static boolean RemoveFromLatestNewsDB(int id){
	
	SQLiteDatabase sql=LatestNews.mydb.getWritableDatabase();
	String[] args={""+id};
	int affected_rows=sql.delete(MyDatabase.TAB_NEWS,MyDatabase.FLD_ID+"=?",args);
	if(affected_rows>0){
		Log.d("ARR","Success Deleted rows :" +id+" and result "+affected_rows);
		return true;
	}else{
		Log.d("ARR","Failed Deleted rows :" +id+" and result "+affected_rows);
		return false;
	}
	
		
	}

}
