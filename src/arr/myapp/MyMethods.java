package arr.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MyMethods {
	public static Activity activity;
	
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

}
