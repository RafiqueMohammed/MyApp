package arr.myapp;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;


public class MainActivity extends Activity {
 
	List<HomeMenuListColl> list;
	ListView homeMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        list=new ArrayList<HomeMenuListColl>();
      
        list.add(new HomeMenuListColl(getResources().getString(R.string.title_activity_latest_news),R.drawable.news64));
        list.add(new HomeMenuListColl(getResources().getString(R.string.title_activity_live_feeds),R.drawable.livefeeds64));
        list.add(new HomeMenuListColl(getResources().getString(R.string.title_activity_live_video),R.drawable.video_64));
        list.add(new HomeMenuListColl(getResources().getString(R.string.title_activity_contacts),R.drawable.contacts64));
        list.add(new HomeMenuListColl(getResources().getString(R.string.title_activity_feedback),R.drawable.sendback64));
        list.add(new HomeMenuListColl(getResources().getString(R.string.title_activity_settings),R.drawable.settings64));
        
   
        
        homeMenu=(ListView) findViewById(R.id.HomeMenu);
       // homeMenu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.home_menu_array)));
       homeMenu.setAdapter(new MyAdapter(this,R.layout.all_list_layout,list)) ;
       
       
       homeMenu.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View viewClicked, int pos,
				long id) {
			// TODO Auto-generated method stub
			
		openWindow(pos);
		
		}
	});
       
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
  if(PreferenceManager.getDefaultSharedPreferences(this).getString("pref_changed","-").equals("yes")){
    	Log.d("ARR","Sensed Changes! key:"+PreferenceManager.getDefaultSharedPreferences(this).getString("pref_changed","-"));
    	PreferenceManager.getDefaultSharedPreferences(this).edit().remove("pref_changed").commit();
    	startActivity(getIntent());
    	finish();
    }  	 
    }
  	
    
    private void openWindow(int pos){
    	
    	Intent intent = new Intent();
    	
    	switch(pos){
    	case 0:
    		 intent = new Intent(getApplicationContext(),LatestNews.class);
    		 break;
    	case 1:
    		 intent = new Intent(MainActivity.this,UploadImage.class);
    		 break;
    	case 2:
    	 intent = getIntent();
    	 break;
    	case 3:
   		 intent = new Intent(this,LatestNews.class);
   		 break;
    	case 4:
      		 intent = new Intent(this,Feedback.class);
      		 break;
    	case 5:
      		 intent = new Intent(this,Settings.class);
      		// startActivity(intent, 1);
      		 break;
 		
    	}
    	
    	startActivity(intent);
    	
    }


    class HomeMenuListColl{
    	public String title;
    	public String getTitle() {
			return title;
		}

		public int getIcons() {
			return icons;
		}

		public int icons;
    	
    	public HomeMenuListColl(String title,int img) {
			// TODO Auto-generated constructor stub
    		this.title=title;
    		this.icons=img;
		}
    	
    }
    
}
