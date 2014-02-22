package arr.myapp;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;

public class UploadImage extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_image);
		Button upload=(Button) findViewById(R.id.upload);
		upload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AsyncHttpClient client= new AsyncHttpClient();
				File file=new File(Environment.getExternalStorageDirectory().getPath()+"/Download/priya.jpg");
				RequestParams param=new RequestParams();
				final TextView progress_txt=(TextView) findViewById(R.id.upload_progress);
				try {
					
					param.put("file", file);
					progress_txt.setText("Please wait..");
					client.post("http://api.aitj.org/upload/index.php?upload=true", param, new AsyncHttpResponseHandler(){
						
						
						public void onSuccess(int statusCode, org.apache.http.Header[] headers, String content) {
							Toast.makeText(UploadImage.this,"status :"+statusCode+"content : "+content,Toast.LENGTH_LONG).show();
							progress_txt.setText("status :"+statusCode+"content : "+content);
						};
						public void onFailure(int statusCode, Throwable error, String content) {
							Toast.makeText(UploadImage.this,"Failed : status :"+statusCode+"Throw:"+error.toString()+" content : "+content,Toast.LENGTH_LONG).show();
						};
						@Override
						public void onProgress(int bytesWritten, int totalSize) {
							// TODO Auto-generated method stub
							int c=(bytesWritten*100)/totalSize;
							progress_txt.setText("Uploading "+c+"% ..");
											
							super.onProgress(bytesWritten, totalSize);
							
						}
					});
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
