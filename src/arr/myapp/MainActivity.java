package arr.myapp;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
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
        list.add(new HomeMenuListColl("Latest News",R.drawable.news64));
        list.add(new HomeMenuListColl("Live Feeds",R.drawable.livefeeds64));
        list.add(new HomeMenuListColl("Live Video",R.drawable.video_64));
        list.add(new HomeMenuListColl("AITJ Contacts",R.drawable.contacts64));
        list.add(new HomeMenuListColl("Feedback",R.drawable.sendback64));
        list.add(new HomeMenuListColl("Settings",R.drawable.settings64));
        
   
        
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
    	 intent = new Intent(this,LiveVideo.class);
    	 break;
    	case 3:
   		 intent = new Intent(this,LatestNews.class);
   		 break;
    	case 4:
      		 intent = new Intent(this,Feedback.class);
      		 break;
    	case 5:
      		 intent = new Intent(this,Settings.class);
      		 startActivityForResult(intent, 1);
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
