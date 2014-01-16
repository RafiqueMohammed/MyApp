package arr.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.MediaController;
import android.widget.VideoView;

public class LiveVideo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live_video);
		VideoView vid= new VideoView(this);
		vid.setVideoPath("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");
		vid.setMediaController(new MediaController(this));
		
	}



}
