package arr.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MySharedPrefs extends Activity  {

	SharedPreferences sp=this.getSharedPreferences("latest_news", Context.MODE_PRIVATE);
	
	final int DEFAULT=0;
	
	void update_last_row(int id){
		SharedPreferences.Editor edit=sp.edit();
		edit.putInt("last_cloud_id", id);
		edit.commit();
		Toast.makeText(this,"Update Last Row :"+id,Toast.LENGTH_LONG).show();
		
	}
	
	int get_last_row(){
		int row=sp.getInt("last_cloud_id", DEFAULT);
		Toast.makeText(this,"Get Last Row :"+row,Toast.LENGTH_LONG).show();
			return row;
				
	}
}