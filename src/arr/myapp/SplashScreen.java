package arr.myapp;

import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends Activity {
	boolean ft = false;
	TextView splash_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		splash_txt = (TextView) findViewById(R.id.splash_txt);
		final CheckInternet ci = new CheckInternet(this);
		final SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
		final SharedPreferences lang_pref=PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
	Log.d("ARR","Auth :"+sp.getString("AuthID","-"));

		final Handler handler = new Handler();

		Runnable InternetCheck = new Runnable() {

			@Override
			public void run() {
				splash_txt.setText("Checking Out Internet Connection..");

				if (!ci.isConnectingToInternet()) {
					Toast.makeText(SplashScreen.this,
							"No Internet Connection Available",
							Toast.LENGTH_SHORT).show();
				}
				
				
			}
		};

		handler.postDelayed(InternetCheck, 2000);
		final Runnable settingUp = new Runnable() {

			@Override
			public void run() {
				MyDatabase mydb = new MyDatabase(SplashScreen.this);
				splash_txt.setText("Setting up environment for the first time use..");
				
				Toast.makeText(SplashScreen.this,
						"Setting up environment for the first time use..",Toast.LENGTH_SHORT).show();
				SharedPreferences.Editor edit=sp.edit();
				
				
				TelephonyManager tm=(TelephonyManager)SplashScreen.this.getSystemService(Context.TELEPHONY_SERVICE);
				UUID newAuthID = UUID.randomUUID();
				if (tm.getLine1Number()!= null)
					{
					String device_id=((tm.getDeviceId()==null)? newAuthID.toString() :tm.getDeviceId()) ;
					edit.putInt("DeviceType",tm.getPhoneType());
					edit.putString("AuthID",device_id);
					edit.commit();
					Log.d("ARR","Fresh Auth : Cellular"+sp.getString("AuthID","Not again 1while "+tm.getPhoneType()));
					}else{
						edit.putInt("DeviceType",0);
						
						edit.putString("AuthID",newAuthID.toString());
						edit.commit();
						Log.d("ARR","Fresh Auth :"+sp.getString("AuthID","Not again 2while "+newAuthID));
					}
				
				
		
		SharedPreferences.Editor l_edit=lang_pref.edit();
				l_edit.putString("Language","en");
				l_edit.commit();
				
				SQLiteDatabase db = mydb.getWritableDatabase();
				try {
					ContentValues cv = new ContentValues();
					cv.put("cloud_id", 0);
					cv.put("title", "Welcome");
					cv.put("description", "Thanks for downloading..");
					cv.put("body", "Thanks for downloading our first BETA App ");
					cv.put("posted_on", MyMethods.getCurrentDateTime());
					
					db.insert(MyDatabase.TAB_NEWS, "-", cv);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(SplashScreen.this,
							"Exception :" + e.toString(), Toast.LENGTH_SHORT)
							.show();
				}

				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		};

		
MyMethods.setLanguage(getBaseContext(),lang_pref.getString("Language","en"));

Runnable skipToHomepage = new Runnable() {

	@Override
	public void run() {
		Toast.makeText(SplashScreen.this, "Skipped.", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(SplashScreen.this, MainActivity.class);
		startActivity(i);
		finish();
	}
};


		if (sp.contains("AuthID")) {
			handler.postDelayed(skipToHomepage, 2000);
		} else {
			handler.postDelayed(settingUp, 3000);
			
		}

	}
}
