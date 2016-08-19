package madroid.chillaaxcaptain.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.adapter.OrderDetailsItemAdapter;
import madroid.chillaaxcaptain.helpers.DatabaseHelper;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.OrderDetails;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusActivity extends AppCompatActivity {

    final SharedData sd=SharedData.getSingletonObject();
    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    public static String RestaurantName,RestaurantId,RestaurantTableId,RestaurantLocation,RestaurantTableDisplayName;
    public String alertMsg="";
    public String TABLE_NAME="cart_items";
    public ProgressDialog pDialog;
    public static DatabaseHelper dbHelper;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        TextView orderStatusRestaurantName=(TextView)findViewById(R.id.orderStatusRestaurantName);
        SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
        RestaurantName = prefs.getString("RestaurantName", null);
        RestaurantId = prefs.getString("RestaurantId", null);
        RestaurantTableId = prefs.getString("RestaurantTableId", null);
        RestaurantTableDisplayName = prefs.getString("RestaurantTableDisplayName", null);
        RestaurantLocation = prefs.getString("RestaurantLocation", null);
        orderStatusRestaurantName.setText(RestaurantName+" "+RestaurantLocation+", Table No: "+RestaurantTableDisplayName);
        dbHelper=new DatabaseHelper(getApplicationContext());
        loadOrderedItems();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.orderSwipeViewLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // onRefresh action here
                swipeRefreshLayout.setRefreshing(true);
                loadOrderedItems();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button orderStatusAddItems= (Button) findViewById(R.id.orderStatusAddItems);
        orderStatusAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MenuItemsActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        Button orderStatusRequestBill= (Button) findViewById(R.id.orderStatusRequestBill);
        orderStatusRequestBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("request clicked");
                View orderStatusButtonLayout = findViewById(R.id.orderStatusButtonsLayout);
                orderStatusButtonLayout.setVisibility(View.GONE);
                View orderStatusCashCardLayout = findViewById(R.id.orderStatusCardOrCashLayout);
                orderStatusCashCardLayout.setVisibility(View.VISIBLE);
            }
        });

        Button orderStatusCancel= (Button) findViewById(R.id.orderStatusCancel);
        orderStatusCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertMsg=getResources().getString(R.string.OrderStatusAlertCancel);
                raiseRequest(getResources().getString(R.string.cancelBill),getResources().getString(R.string.cancelPay));
            }
        });

        // CASH OR CARD PAYMENT OPERATION DONE HERE
        TextView cashcardCloseButton = (TextView) findViewById(R.id.cashcardCloseButton);
        cashcardCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View orderStatusCashCardLayout = findViewById(R.id.orderStatusCardOrCashLayout);
                orderStatusCashCardLayout.setVisibility(View.GONE);
                View orderStatusButtonLayout = findViewById(R.id.orderStatusButtonsLayout);
                orderStatusButtonLayout.setVisibility(View.VISIBLE);
            }
        });

        Button cashButton = (Button) findViewById(R.id.cashPay);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CASH SELECTED
                raiseRequest(getResources().getString(R.string.requestBill),getResources().getString(R.string.cashPay));
                alertMsg=getResources().getString(R.string.OrderStatusAlertPayment);
            }
        });

        Button cardButton = (Button) findViewById(R.id.cardPay);
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CARD SELECTED
                alertMsg=getResources().getString(R.string.OrderStatusAlertPayment);
                raiseRequest(getResources().getString(R.string.requestBill),getResources().getString(R.string.cardPay));
            }
        });
    }

    public void loadOrderedItems(){
        pDialog = new ProgressDialog(OrderStatusActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<OrderDetails> call = apiInterface.stw_getTableById(RestaurantTableId);
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
               // Log.d("info",response.body().getTable().getRestaurantOrder().get(0).getItems().getRestaurantOrderItem().get(0).getRestaurantMenuItemId());
                pDialog.cancel();
                swipeRefreshLayout.setRefreshing(false);
                try{
                   int i=0;
                   final List<MyOrderItems>myOrderItemsList =new ArrayList<>();
                    if(response.body().getRestaurantOrder().size() > 0){
                        while(i<response.body().getRestaurantOrder().size()){

                            final int orderItemSize=response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().size();
                            final String orderItemSteward="Steward: N/A";
                            String ostatus="";
                            switch(response.body().getRestaurantOrder().get(i).getStatus()){
                                case "n":{
                                    ostatus="STATUS: ORDER PLACED";
                                    break;
                                }
                                case "q":{
                                    ostatus = "STATUS: ORDER PROCESSING";
                                    break;
                                }
                                case "p":{
                                    ostatus="STATUS: ORDER SUCCESSFUL";
                                    break;
                                }

                            }
                            final String orderItemStatus=ostatus;
                            final String orderItemsetBill=response.body().getRestaurantOrder().get(i).getOriginalBill();
                            final String orderItemID="ID: "+RestaurantName+"RES"+response.body().getRestaurantOrder().get(i).getId();
                            // System.out.println(response.body().getRestaurantOrder().size());
                            final List<String>orderItemImages=new ArrayList<String>();
                            //Iterator<RestaurantOrderItem> iterator =response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().listIterator();
                            int j=0;
                            int sizeJ=response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().size();
                            while(j<sizeJ){
                                orderItemImages.add(response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().get(j).getRestaurantMenuItem().getItemImgSmall());
                                System.out.println(response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().get(j).getRestaurantMenuItem().getItemImgSmall());
                                j++;
                            }
                       /* while (response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().iterator().hasNext()){
                            orderItemImages.add(response.body().getRestaurantOrder().get(i).getItems().getRestaurantOrderItem().iterator().next().getRestaurantMenuItem().getItemImgSmall());
                            System.out.println("looping");
                        }*/
                            myOrderItemsList.add(new MyOrderItems(){{
                                oItemSetQty = orderItemSize;
                                oItemStatusText=orderItemStatus;
                                oItemSetPrice=orderItemsetBill;
                                oItemSteward=orderItemSteward;
                                oItemSetOrderId=orderItemID;
                                oimageList=orderItemImages;
                            }
                            });
                            // System.out.println( orderItemImages.size());
                            i++;
                        }
                        // System.out.println( myOrderItemsList.size());
                        OrderDetailsItemAdapter orderDetailsItemAdapter = new OrderDetailsItemAdapter(myOrderItemsList,getApplicationContext());
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.orderStatusRecycler);
                        recyclerView.setNestedScrollingEnabled(false);
                        orderDetailsItemAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(orderDetailsItemAdapter);

                        //Storing Status
                        SharedPreferences.Editor editor=getSharedPreferences(sd.Flaglists,MODE_PRIVATE).edit();
                        editor.putInt("showOrderStatus",1);
                        editor.commit();
                    }else{
                        TextView orderStatusHead= (TextView) findViewById(R.id.orderStatusHead);
                        orderStatusHead.setText("No Orders Found");
                        //Storing Status
                        SharedPreferences.Editor editor=getSharedPreferences(sd.Flaglists,MODE_PRIVATE).edit();
                        editor.putInt("showOrderStatus",0);
                        editor.commit();
                        //openDialogNoOrder();
                    }


                }catch (Exception e){
                    System.out.println(e);
                    TextView orderStatusHead= (TextView) findViewById(R.id.orderStatusHead);
                    orderStatusHead.setText("Sorry, Something went wrong ;(");
                }

            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                pDialog.cancel();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

      public class MyOrderItems{
           public int oItemSetQty;
           public String oItemSetPrice,oItemSteward,oItemSetOrderId,oItemStatusText;
          public List<String> oimageList;
        }


    public void raiseRequest(String requestType,String paymentType){
        pDialog=new ProgressDialog(OrderStatusActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<ResponseBody> raiseCall=apiInterface.raiseRequest(RestaurantId,RestaurantTableId,requestType,paymentType);
        raiseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.cancel();
                Log.d("responseRaise",response.raw()+"");
                //Deleting router preferences
                dbHelper.clearTableData(TABLE_NAME);
                SharedPreferences settings = getSharedPreferences(sd.Flaglists,MODE_PRIVATE);
                settings.edit().remove("code").remove("showOrderStatus").commit();
                //end
                openDialog(new View(getApplicationContext()),alertMsg);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.cancel();
            }
        });
    }


    public void openDialog(View view,String msg){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_alerts,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        TextView commonDialogMsg = (TextView) dialogView.findViewById(R.id.commonDialogMsg);
        commonDialogMsg.setText(msg);
        Button commonDialogClose = (Button) dialogView.findViewById(R.id.commonDialogClose);
        commonDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              alertdialog.cancel();
                Intent intent = new Intent(getApplicationContext(),OpenTableActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void openDialogNoOrder(){
        String msg = getResources().getString(R.string.noOrderFound);
        final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_smiley_layout,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        TextView commonPopHeader = (TextView) dialogView.findViewById(R.id.commonPopHeader);
        commonPopHeader.setText("NO ORDERS FOUND!");
        ImageView smile = (ImageView)dialogView.findViewById(R.id.commonSmilie);
        smile.setImageDrawable(getResources().getDrawable(R.drawable.sadsmile));
        TextView commonMsg = (TextView)dialogView.findViewById(R.id.commonMsg);
        commonMsg.setText(msg);
        Button loginSubmit=(Button)dialogView.findViewById(R.id.loginSubmit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        Intent intent = new Intent(getApplicationContext(),OpenTableActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
