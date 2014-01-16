package arr.myapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class FetchLiveFeeds extends AsyncTask<String, String, String>{
@Override
protected void onPostExecute(String result) {
// TODO Auto-generated method stub
super.onPostExecute(result);
//Toast.makeText(MainActivity.this,result.toString(),Toast.LENGTH_LONG).show();
}
StringBuilder result=new StringBuilder();
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		
		HttpClient http = new DefaultHttpClient();
		HttpGet send_data;
		String url="http://echo.jsontest.com/fullname/RafiqueMohammed/country/India/Wife/Fouziya/";
		try {
			send_data = new HttpGet(url);
		
			HttpResponse rcvd_data=http.execute(send_data);
			StatusLine header=rcvd_data.getStatusLine();
			int success=header.getStatusCode();
			
			if(success==200){
				InputStream in=rcvd_data.getEntity().getContent();
				BufferedReader bf=new BufferedReader(new InputStreamReader(in));
				String line="";
		
				while((line=bf.readLine())!=null){
					result.append(line+"\n");
				}
				
bf.close();
in.close();
			}else{
				result.append("Server Code Recieved :"+success);
			}
		} catch (ClientProtocolException e) {
			result.append("ClientProtocolException :"+e.toString());// TODO Auto-generated catch block
		
		} catch (IOException e) {
			String error=e.toString();
			// TODO Auto-generated catch block
			int myvalue=error.indexOf("UnknownHost");
			if(myvalue!=-1){
				result.append("No Internet Connection!");
			}else{
				result.append("Unknown Exception :"+e.toString());
			}
			
					} 
		
		return result.toString();
		
	}
	
}
