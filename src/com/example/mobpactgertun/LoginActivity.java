package com.example.mobpactgertun;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginActivity extends Activity {
	LinearLayout LoginBox ;
	 ImageView imgLogo;
	
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
       LoginBox = (LinearLayout) findViewById(R.id.LoginBox); 
        imgLogo = (ImageView) findViewById(R.id.imageView1);
        Button login = (Button)findViewById(R.id.buttonLogin);
        LoginBox.setVisibility(View.GONE);
        
        imgLogo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 animate();
				
			}
		});
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentVideoAlltag = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intentVideoAlltag);
				
			}
		});
      
       


    }
	
	private void animate()
	{
	    Animation animTranslate  = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate);
        animTranslate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) { 
            	
            }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                LoginBox.setVisibility(View.VISIBLE);
                Animation animFade  = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade);
                LoginBox.startAnimation(animFade);
            }
        });
       
       
        imgLogo.startAnimation(animTranslate);
	}

}
