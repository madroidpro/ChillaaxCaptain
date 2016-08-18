package madroid.chillaaxcaptain.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.adapter.MarshMallowPermission;
import madroid.chillaaxcaptain.helpers.SharedData;


public class ScanActivity extends AppCompatActivity {

   final SharedData sd = SharedData.getSingletonObject();
    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
