package madroid.chillaaxcaptain.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.adapter.GridMenuAdapter;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.FirstMenu;
import madroid.chillaaxcaptain.model.RestaurantInformation;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {

    public String code="";
    SharedData sd =SharedData.getSingletonObject();
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    public ProgressDialog pDialog;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    private boolean internetConnected=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        code=getIntent().getStringExtra("code");
        if(code !=""){
            loadRestaurant(code);
        }



    }



    private void loadRestaurant(String code) {
        pDialog = new ProgressDialog(RestaurantActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<RestaurantInformation>call=apiInterface.decodeQr(code);
        call.enqueue(new Callback<RestaurantInformation>() {
            @Override
            public void onResponse(Call<RestaurantInformation> call, Response<RestaurantInformation> response) {
                pDialog.cancel();
                if(response.body().getRestaurantTable().getStatus().toString().equals("o")){
                    //Setting Basic information

                    sd.setRestaurantId(response.body().getRestaurant().getId());
                    sd.setRestaurantName(response.body().getRestaurant().getName());
                    sd.setRestaurantAddress(response.body().getRestaurant().getAddress());
                    sd.setRestaurantLocation(response.body().getRestaurant().getLocation());
                    sd.setRestaurantTableId(response.body().getRestaurantTable().getId());
                    sd.setRestaurantTableDisplayName(response.body().getRestaurantTable().getDisplayName());
                    sd.setRestaurantBrandId(response.body().getBrand().getId());
                    sd.setBrandName(response.body().getBrand().getName());
                    sd.setBrandServiceTax(response.body().getBrand().getServiceTax());
                    sd.setBrandServiceCharge(response.body().getBrand().getServiceCharge());
                    sd.setBrandVat(response.body().getBrand().getVat());
                    sd.setBrandType(response.body().getBrand().getType());
                    sd.setKrishikalyan(response.body().getBrand().getKrishikalyan());
                    sd.setSwachbarath(response.body().getBrand().getSwachbarath());
                    sd.setVat2(response.body().getBrand().getVat2());

                    SharedPreferences.Editor editor=getSharedPreferences(sd.Flaglists,MODE_PRIVATE).edit();
                    editor.putString("RestaurantId",sd.getRestaurantId());
                    editor.putString("RestaurantName",sd.getRestaurantName());
                    editor.putString("RestaurantTableId",sd.getRestaurantTableId());
                    editor.putString("RestaurantTableDisplayName",sd.getRestaurantTableDisplayName());
                    editor.putString("RestaurantLocation",sd.getRestaurantLocation());
                    editor.putString("BrandServiceTax",sd.getBrandServiceTax());
                    editor.putString("BrandServiceCharge",sd.getBrandServiceCharge());
                    editor.putString("Krishikalyan",sd.getKrishikalyan());
                    editor.putString("Swachbarath",sd.getSwachbarath());
                    editor.putString("BrandVat",sd.getBrandVat());
                    editor.commit();
                    //  Log.d("Rinfo",sd.getRestaurantId());
                    TextView ourmenu =(TextView)findViewById(R.id.ourMenu);
                    ourmenu.setText("Our Menu");
                    ourmenu.setTextSize(1,20);

                    TextView restaurantNameSmall =(TextView)findViewById(R.id.restaurantNameSmall);
                    restaurantNameSmall.setText(response.body().getRestaurant().getName()+" "+response.body().getRestaurant().getLocation()+" , Table No: "+response.body().getRestaurantTable().getDisplayName());
                    ImageView restaurantBackground=(ImageView)findViewById(R.id.restaurantBackground);
                    ImageView restaurantLogo=(ImageView)findViewById(R.id.restaurantLogo);

                    Picasso.with(getApplicationContext())
                            .load(sd.BaseUrlImg +"brand-bg/"+response.body().getBrand().getBackground())
                            .into(restaurantBackground);

                    Picasso.with(getApplicationContext())
                            .load(sd.BaseUrlImg +"brand-logo/"+response.body().getBrand().getLogo())
                            .into(restaurantLogo);

                    TextView restaurantName =(TextView)findViewById(R.id.restaurantName);
                    restaurantName.setText(response.body().getRestaurant().getName()+" Restaurant");

                    TextView restaurantAddress=(TextView)findViewById(R.id.restaurantAddress);
                    restaurantAddress.setText(response.body().getRestaurant().getAddress());

                    loadMenuGrid(response.body().getRestaurant().getId());

                    SharedPreferences preferences = getSharedPreferences(sd.Flaglists,MODE_PRIVATE);
                    String userName=preferences.getString("userName",null);
                    if(userName == null)
                        openDialog();
                }else{
                    SharedPreferences settings = getSharedPreferences(sd.Flaglists,MODE_PRIVATE);
                    settings.edit().clear().commit();
                    openDialogBusy();
                    TextView ourMenu =(TextView)findViewById(R.id.ourMenu);
                    ourMenu.setText("Sorry, something went wrong :(");
                    ourMenu.setTextSize(1,15);
                }
                Log.d("Rinfo",response.body().getRestaurantTable().getDisplayName());

            }
            @Override
            public void onFailure(Call<RestaurantInformation> call, Throwable t) {
                pDialog.cancel();
                    Log.d("Rinfoe",t.toString());
                TextView ourMenu =(TextView)findViewById(R.id.ourMenu);
                //Clearing all data..  we don't want no data of yours
                SharedPreferences settings = getSharedPreferences(sd.Flaglists,MODE_PRIVATE);
                settings.edit().clear().commit();
                openDialogBusy();
                ourMenu.setText("Sorry, something went wrong :(");
                ourMenu.setTextSize(1,15);
            }
        });
    }

    private void loadMenuGrid(final String rest_id){
        Call<FirstMenu>firstMenuCall=apiInterface.getCategories(rest_id);
        firstMenuCall.enqueue(new Callback<FirstMenu>() {
            @Override
            public void onResponse(Call<FirstMenu> call, final Response<FirstMenu> response) {
                Log.d("Rinfo",response.body().getBeverages());
                List<MainmenuGrid> menulist =new ArrayList<>();
                menulist.add(new MainmenuGrid(){{
                    itemName="Starters";
                    itemCount=response.body().getStarters();
                    imagename="maincats/1.jpg";
                    itemId=1;
                }});
                menulist.add(new MainmenuGrid(){{
                    itemName="MainCourse";
                    itemCount=response.body().getMainCourse();
                    imagename="maincats/2.jpg";
                    itemId=2;
                }});
                menulist.add(new MainmenuGrid(){{
                    itemName="Desserts";
                    itemCount=response.body().getDesserts();
                    imagename="maincats/3.jpg";
                    itemId=3;
                }});
                menulist.add(new MainmenuGrid(){{
                    itemName="Beverages";
                    itemCount=response.body().getBeverages();
                    imagename="maincats/4.jpg";
                    itemId=4;
                }});
              /*  menulist.add(new MainmenuGrid(){{
                    itemName="Special Dish";
                    itemCount=response.body().getSpecialDish();
                    imagename="maincats/5.jpg";
                }});*/
                GridMenuAdapter gridMenuAdapter =new GridMenuAdapter(getApplicationContext(),RestaurantActivity.this,menulist);
                GridView menuGrid = (GridView) findViewById(R.id.menuGrid);
                menuGrid.setAdapter(gridMenuAdapter);

            }

            @Override
            public void onFailure(Call<FirstMenu> call, Throwable t) {

            }
        });
    }

    public class MainmenuGrid{
        public String itemName,itemCount,imagename;
        public int itemId;
    }


    public void openDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.login_layout,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        final TextView userName = (TextView) dialogView.findViewById(R.id.loginFullName);
        final TextView mobile = (TextView)dialogView.findViewById(R.id.loginmobile);
        final TextView email = (TextView)dialogView.findViewById(R.id.loginemail);
       // commonDialogMsg.setText(msg);*/
        Button commonDialogClose = (Button) dialogView.findViewById(R.id.loginSubmit);
        commonDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().trim().length() != 0 && userName.getText().toString().trim().length()!=0){
                    SharedPreferences.Editor editPref = getSharedPreferences(sd.Flaglists,MODE_PRIVATE).edit();
                    editPref.putString("userName",userName.getText().toString().trim());
                    editPref.putString("userMobile",mobile.getText().toString().trim());
                    editPref.putString("userEmail",email.getText().toString().trim());
                    editPref.commit();

                    alertdialog.cancel();
                }
            }
        });

    }

    public void openDialogBusy(){
        String msg = getResources().getString(R.string.tableBusy);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_smiley_layout,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        TextView popHead=(TextView)dialogView.findViewById(R.id.commonPopHeader);
        popHead.setText("TABLE RESERVED!");
        ImageView smilieImg = (ImageView)dialogView.findViewById(R.id.commonSmilie);
        smilieImg.setImageDrawable(getResources().getDrawable(R.drawable.sadsmile));
        TextView commonDialogMsg = (TextView) dialogView.findViewById(R.id.commonMsg);
        commonDialogMsg.setText(msg);
        Button loginSubmit = (Button) dialogView.findViewById(R.id.loginSubmit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.cancel();
                //GO to OrderStatus page
                finish();
            }
        });

    }
}
