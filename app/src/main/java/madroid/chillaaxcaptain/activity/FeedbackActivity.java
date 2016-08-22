package madroid.chillaaxcaptain.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {
    final SharedData sd=SharedData.getSingletonObject();
    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    public static int review=3;
    public static String AlertMsg="";
    public static String RestaurantName,RestaurantId,RestaurantTableId,RestaurantLocation,RestaurantTableDisplayName;
    public static String mainOrderId="1";
    public ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        TextView orderStatusRestaurantName=(TextView)findViewById(R.id.feedbackRestaurantName);
        SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
        RestaurantName = prefs.getString("RestaurantName", null);
        RestaurantId = prefs.getString("RestaurantId", null);
        RestaurantTableId = prefs.getString("RestaurantTableId", null);
        RestaurantTableDisplayName=prefs.getString("RestaurantTableDisplayName", null);
        RestaurantLocation = prefs.getString("RestaurantLocation", null);
        mainOrderId=prefs.getString("mainOrderId",null);
        orderStatusRestaurantName.setText(RestaurantName+" "+RestaurantLocation+", Table No: "+RestaurantTableDisplayName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageButton sad = (ImageButton) findViewById(R.id.feedbackSad);
        ImageButton average = (ImageButton) findViewById(R.id.feedbackAverage);
        ImageButton happy = (ImageButton) findViewById(R.id.feedbackHappy);
        ImageButton ehappy = (ImageButton) findViewById(R.id.feedbackEHappy);


        // Custom Rating buttons
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllRating(1);
            }
        });

        average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllRating(2);
            }
        });

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllRating(3);
            }
        });

        ehappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllRating(4);
            }
        });

        //END

        final EditText name=(EditText)findViewById(R.id.feedbackFullName);
       final EditText mobile=(EditText)findViewById(R.id.feedbackPhoneNumber);
       final EditText reviewText = (EditText)findViewById(R.id.feedbackText);
        Button feedbackSubmit = (Button)findViewById(R.id.feedbackSubmit);

        feedbackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean failFlag = false;
                if( name.getText().toString().trim().length() == 0 )
                {
                    failFlag = true;
                    name.setError( "This field is required" );
                }
                if( mobile.getText().toString().trim().length() == 0 )
                {
                    failFlag = true;
                    mobile.setError( "This field is required" );
                }
                if( reviewText.getText().toString().trim().length() == 0 )
                {
                    failFlag = true;
                    reviewText.setError( "This field is required" );
                }

                // if all are fine
                if (failFlag == false) {
                    feedbackReview(name.getText().toString().trim(),mobile.getText().toString().trim(),reviewText.getText().toString().trim());
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
         EditText name=(EditText)findViewById(R.id.feedbackFullName);
         EditText mobile=(EditText)findViewById(R.id.feedbackPhoneNumber);
         name.setText(prefs.getString("userName",""));
         mobile.setText(prefs.getString("userMobile",""));
    }

    private void feedbackReview(String name,String mobile,String review_text) {
        pDialog=new ProgressDialog(FeedbackActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setCancelable(false);
        pDialog.show();
        Log.d("info",mainOrderId);
        Call<ResponseBody> call = apiInterface.userFeedbacks(mainOrderId,name,mobile,review+"",review_text,"empty","0");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.cancel();
                AlertMsg=getResources().getString(R.string.alertMsgFeedback);
                //Clearing all data..  we don't want no data of yours
                SharedPreferences settings = getSharedPreferences(sd.Flaglists,MODE_PRIVATE);
                settings.edit().clear().commit();
                openDialog(new View(getApplicationContext()),AlertMsg);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("infoErr",t+"");
                pDialog.cancel();
            }
        });
    }


    public void openDialog(View view,String msg){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_smiley_layout,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        TextView commonPopHeader =(TextView)dialogView.findViewById(R.id.commonPopHeader);
        commonPopHeader.setText("Success");
        TextView commonDialogMsg = (TextView) dialogView.findViewById(R.id.commonMsg);
        commonDialogMsg.setText(msg);
        Button commonDialogClose = (Button) dialogView.findViewById(R.id.loginSubmit);
        commonDialogClose.setText("Thanks! Visit Again.");
        commonDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.cancel();
                Intent intent = new Intent(getApplicationContext(),OpenTableActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void clearAllRating(int i){
        review=i;
        ImageButton sad = (ImageButton) findViewById(R.id.feedbackSad);
        ImageButton average = (ImageButton) findViewById(R.id.feedbackAverage);
        ImageButton happy = (ImageButton) findViewById(R.id.feedbackHappy);
        ImageButton ehappy = (ImageButton) findViewById(R.id.feedbackEHappy);

        sad.setImageDrawable(getResources().getDrawable(R.drawable.sad));
        average.setImageDrawable(getResources().getDrawable(R.drawable.average));
        happy.setImageDrawable(getResources().getDrawable(R.drawable.happy));
        ehappy.setImageDrawable(getResources().getDrawable(R.drawable.ehappy));

        switch (i){
            case 1:{
                sad.setImageDrawable(getResources().getDrawable(R.drawable.sad_bold));
                break;
            }
            case 2:{
                average.setImageDrawable(getResources().getDrawable(R.drawable.average_bold));
                break;
            }
            case 3:{
                happy.setImageDrawable(getResources().getDrawable(R.drawable.happy_bold));
                break;
            }
            case 4:{
                ehappy.setImageDrawable(getResources().getDrawable(R.drawable.ehappy_bold));
                break;
            }
        }
    }
}
