package madroid.chillaaxcaptain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.helpers.SharedData;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final SharedData sd = SharedData.getSingletonObject();
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // close this activity
                finish();
                Intent intent = new Intent(getApplicationContext(), OpenTableActivity.class);
                startActivity(intent);
               /* SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
                String code = prefs.getString("code", null);
                int showOrderStatus=prefs.getInt("showOrderStatus",0);
                if(showOrderStatus == 1){
                    Intent intent = new Intent(getApplicationContext(), OrderStatusActivity.class);
                    startActivity(intent);
                }
                else if(code != null){
                    Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                    intent.putExtra("code",code);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                    startActivity(intent);
                }*/

            }
        }, 2000);

    }
}
