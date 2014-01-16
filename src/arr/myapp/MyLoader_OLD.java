package arr.myapp;

import java.util.ArrayList;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class MyLoader_OLD extends AsyncTaskLoader<ArrayList<String>> {

	MyObserver ob;
	public static final String ObsAction="MyLoader.LOAD_ACTION";
	public MyLoader_OLD(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> loadInBackground() {
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> ret= new ArrayList<String>();
		char[] randChar="ayuaweisdhesi ufyawo8r79e8 ryf8  yfiehuilifhsi2452dlfh ldsfjds8ewr78993".toCharArray();
		
		Random r=new Random();
		for(int i=0;i<=20;i++){
			String rand="";
			for(int j=0;j<=20;j++){
				rand=rand+randChar[r.nextInt(randChar.length)];
			}
			rand+="\n";
			ret.add(rand);
		}
		return ret;
	}

	@Override
	public void deliverResult(ArrayList<String> data) {
		// TODO Auto-generated method stub
		if(isStarted()){
			super.deliverResult(data);
		}else{
			
		}
		
	}
	
	@Override
	protected void onStartLoading() {
		// TODO Auto-generated method stub
		forceLoad();
		ob=new MyObserver(this);
		IntentFilter iF=new IntentFilter();
		iF.addAction(ObsAction);
		getContext().registerReceiver(ob,iF);
		Log.d("LOADER","onStartLoading");
		super.onStartLoading();
	}
	@Override
	protected void onReset() {
		// TODO Auto-generated method stub
		getContext().unregisterReceiver(ob);
		Log.d("LOADER","onReset");
		super.onReset();
	}

	@Override
	public void onCanceled(ArrayList<String> data) {
		// TODO Auto-generated method stub
		super.onCanceled(data);
	}
	
	public class MyObserver extends BroadcastReceiver{
		MyLoader_OLD loader;
		
		public MyObserver(MyLoader_OLD myLoader_OLD){
			this.loader=myLoader_OLD;
		}
		@Override
		public void onReceive(Context c, Intent arg1) {
			// TODO Auto-generated method stub
			loader.onContentChanged();
			Log.d("BROADCAST","I am called");
		}
		
		
		
	}

}
