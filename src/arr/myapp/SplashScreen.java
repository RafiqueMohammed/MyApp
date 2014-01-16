package arr.myapp;

import java.io.File;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends Activity {
	 boolean ft=false;
	TextView splash_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		splash_txt=(TextView) findViewById(R.id.splash_txt);
		final CheckInternet ci=new CheckInternet(this);
	
		/*new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				*/
		
		
		final Handler handler= new Handler();
		
		Runnable InternetCheck=	new Runnable() {
					
					@Override
					public void run() {
						splash_txt.setText("Checking Out Internet Connection..");
								
				if(!ci.isConnectingToInternet()){
					Toast.makeText(SplashScreen.this, "No Internet Connection Available",Toast.LENGTH_SHORT).show();	
				}
					}};
					
						handler.postDelayed(InternetCheck,1000);
						final Runnable settingUp=	new Runnable() {
							
							
							@Override
							public void run() {
								
								splash_txt.setText("Setting up environment for the first time use..");
								MyDatabase mydb= new MyDatabase(SplashScreen.this);
								
								String qry="INSERT INTO "+MyDatabase.TAB_NEWS+"(cloud_id,title,description,body,posted_on,status,enabled)" +
										" VALUES(1,'Welcome','Thanks for downloading our first BETA App of All India thowheed Jamaath','Thanks for downloading our first BETA App of All India thowheed Jamaath','2013-12-12',1,1);";
							SQLiteDatabase db=mydb.getWritableDatabase();
							try {
								Cursor cu;
								cu=db.rawQuery(qry, null);
								int getT=cu.getCount();
								Toast.makeText(SplashScreen.this, "Row inserted!"+getT,Toast.LENGTH_SHORT).show();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								Toast.makeText(SplashScreen.this, "Exception :"+e.toString(),Toast.LENGTH_SHORT).show();	
									}
							
							
							
							}};
				Runnable FirstTime=	new Runnable() {
					
					
					@Override
					public void run() {
						
						splash_txt.setText("Checking if file is stable..");
						
File myFile = getApplicationContext().getDatabasePath("aitj");
if(myFile.exists()){

}else{

	handler.postDelayed(settingUp,2000);
}
					
					
					}
					};

					
					handler.postDelayed(FirstTime,1300);
					

	/*if(ft){

			handler.postDelayed(settingUp,1000);
	}*/
	
				
			Runnable finalTask=	new Runnable() {
					
					@Override
					public void run() {
						Intent i = new Intent(SplashScreen.this,MainActivity.class);
						startActivity(i);
						finish();
					}};
				//	handler.postDelayed(finalTask,1000);
			//}
	//	}, 3000);
		
		
		
		handler.postDelayed(finalTask,3000);
	}

}
