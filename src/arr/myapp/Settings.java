package arr.myapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		Log.d("ARR","OnResume Called");
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		Log.d("ARR","OnPause Called");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		MyMethods.setLanguage(getBaseContext(),PreferenceManager.getDefaultSharedPreferences(this).getString("Language","en"));
		
		
		
		if(key.equals("Language")){
			
			PreferenceManager.getDefaultSharedPreferences(this).edit().putString("pref_changed","yes").commit();
			    startActivity(getIntent());
			    finish();
			
		}
		Log.d("ARR","onSharedPreferenceChanged Called Key:"+key);
		
	}
}
