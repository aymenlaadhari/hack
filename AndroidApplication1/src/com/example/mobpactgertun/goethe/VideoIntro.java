package com.example.mobpactgertun.goethe;

import util.Config;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobpactgertun.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;


public class VideoIntro  extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {
	private static final int RECOVERY_DIALOG_REQUEST = 1;
	 
    // YouTube player view
    private YouTubePlayerView youTubeView;
    private TextView text;
    String res;
    private Button buttonIntro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		res = i.getStringExtra("intent");
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	       
		setContentView(R.layout.activity_video);
		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		text = (TextView)findViewById(R.id.textType);
		text.setText(res);
		 
        // Initializing video player with developer key
        youTubeView.initialize(Config.DEVELOPER_KEY, this);
        buttonIntro = (Button)findViewById(R.id.button_intro);
        buttonIntro.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_LONG).show();
				
			}
		});
        
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 @Override
	    public void onInitializationFailure(YouTubePlayer.Provider provider,
	            YouTubeInitializationResult errorReason) {
	        if (errorReason.isUserRecoverableError()) {
	            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
	        } else {
	            String errorMessage = String.format(
	                    getString(R.string.error_player), errorReason.toString());
	            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	        }
	    }

	 @Override
	    public void onInitializationSuccess(YouTubePlayer.Provider provider,
	            YouTubePlayer player, boolean wasRestored) {
	        if (!wasRestored) {
	 
	            // loadVideo() will auto play video
	            // Use cueVideo() method, if you don't want to play it automatically
	            player.loadVideo(Config.YOUTUBE_VIDEO_CODE);
	 
	            // Hiding player controls
	            player.setPlayerStyle(PlayerStyle.CHROMELESS);
	        }
	    }
	 
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (requestCode == RECOVERY_DIALOG_REQUEST) {
	            // Retry initialization if user performed a recovery action
	            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
	        }
	    }
	 
	    private YouTubePlayer.Provider getYouTubePlayerProvider() {
	        return (YouTubePlayerView) findViewById(R.id.youtube_view);
	    }

	

}
