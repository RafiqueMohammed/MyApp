package arr.myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;

public class Feedback extends Activity {

	EditText fname,email,msg;
	String[] data=new String[3];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		fname=(EditText) findViewById(R.id.feedback_fname);
		email=(EditText) findViewById(R.id.feedback_email);
		msg=(EditText) findViewById(R.id.feedback_msg);
		AccountManager manager = AccountManager.get(this); 
	    Account[] accounts = manager.getAccounts();
	    UUID uniqueKey = UUID.randomUUID();   
	    Log.d("ARR","Unique ID :"+uniqueKey);
	    if(accounts.length<1){
	    	MyMethods.ShowAlert((Activity)this,"Email Account","You have no default account! Please create an email account!",true);
	    }else{
	    	  MyMethods.ShowAlert((Activity)this,"Your Account",""+accounts.length,false);
	    }
	  

		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.feedback, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.feedback_send:
			//TelephonyManager tp=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			
			String dev_id=Secure.getString(this.getContentResolver(), Secure.ANDROID_ID); 
			Log.d("ARR","DEVICE :"+dev_id);
			String sFname=fname.getText().toString();
			String sEmail=email.getText().toString();
			String sMsg=msg.getText().toString();
			if(sFname==null || sFname==""||sMsg==null || sMsg.equals("")){
				MyMethods.ShowAlert((Activity) this,"Required","Requires field is empty!",false);
			}else{
				FeedBackFormData fData=new FeedBackFormData(sFname,sEmail,sMsg, dev_id);
				new FeedbackSync().execute(fData);
			}
	
			return true;
		default:return super.onOptionsItemSelected(item);
		
		}
		
	}

	public class FeedbackSync extends AsyncTask<FeedBackFormData,Void,String[]>{
		ProgressDialog wait=new ProgressDialog(Feedback.this);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			wait.setTitle("Sending Data");
			wait.setCancelable(false);
			wait.setMessage("Please wait..");
			wait.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			wait.show();
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(FeedBackFormData... data) {
			// TODO Auto-generated method stub
			
			HttpClient http=new DefaultHttpClient();
			HttpPost post=new HttpPost("http://api.aitj.org/data/feedback.php?type=feedback");
			List<NameValuePair> postdata=new ArrayList<NameValuePair>();
			postdata.add(new BasicNameValuePair("fname",data[0].getFname()));
			postdata.add(new BasicNameValuePair("email",data[0].getEmail() ));
			postdata.add(new BasicNameValuePair("msg",data[0].getMsg()));
			postdata.add(new BasicNameValuePair("device_id",data[0].getDevice_id()));
			Log.d("ARR","Device ID "+data[0].getDevice_id());
			try{
				post.setEntity(new UrlEncodedFormEntity(postdata));
				HttpResponse rcvd= http.execute(post);
				int status=rcvd.getStatusLine().getStatusCode();
				String[] result = new String[3];
				if(status==200){
					InputStream in = rcvd.getEntity().getContent();
					BufferedReader bf = new BufferedReader(new InputStreamReader(in));
					String tmp = "";
					StringBuilder content = new StringBuilder();
					while ((tmp = bf.readLine()) != null) {
						content.append(tmp + "\n");
					}
					
					result[0] = "ok";
					result[1] = "FeedBack Submitted Successfully";
					result[2] = content.toString();

					in.close();
					bf.close();
					return result;
				}else{
					result[0] = "no";
					result[1] = "Error occured on the server";
					result[2] ="Status :"+status;
					return result;
				}
				
			}catch(ClientProtocolException e){
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			wait.dismiss();
			if(result[0]=="ok"){
				try {
					JSONObject jResult=new JSONObject(result[2]);
					String sStatus=jResult.getString("status");
					String sResult=jResult.getString("result");
					Log.d("ARR","RCVD JSON : "+sStatus+ " | "+sResult);
					if(sStatus.equals("ok")){
						
						Toast.makeText(Feedback.this,""+sResult,Toast.LENGTH_LONG).show();
						Feedback.this.finish();
						
					}else if(sStatus.equals("no")){
						Toast.makeText(Feedback.this,""+sResult,Toast.LENGTH_LONG).show();
						
					}else{
						Toast.makeText(Feedback.this,"Unknown Error Occured. #ERROR:635",Toast.LENGTH_LONG).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
			}else{
				Toast.makeText(Feedback.this,""+result[1]+"\n"+"Server Status : "+result[2],Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}
		
	}
	
	public class FeedBackFormData{
		private String fname,email,msg,device_id;

		public FeedBackFormData(String fname,String email,String msg,String device_id) {
			this.fname=fname;
			this.email=email;
			this.msg=msg;
			this.device_id=device_id;
			// TODO Auto-generated constructor stub
		}

		public String getFname() {
			return fname;
		}

		public String getEmail() {
			return email;
		}

		public String getMsg() {
			return msg;
		}

		public String getDevice_id() {
			return device_id;
		}
		
	}

}
