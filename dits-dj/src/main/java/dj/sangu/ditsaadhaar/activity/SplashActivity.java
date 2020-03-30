package dj.sangu.ditsaadhaar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import dj.sangu.ditsaadhaar.R;
import dj.sangu.ditsaadhaar.utils.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.launch_screen);
        final int isLogin = SharedPrefManager.getInstance(SplashActivity.this).isLoggedIn();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,isLogin==1? OperatorHomeActivity.class:isLogin==2?AdminHomeActivity.class:LoginActivity.class));
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
                finish();
            }
        },2000);

    }
}
