package madroid.chillaaxcaptain.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.adapter.SummeryItemListAdapter;
import madroid.chillaaxcaptain.helpers.DatabaseHelper;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.PlaceOrder;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryActivity extends AppCompatActivity {

    public final SharedData sd=SharedData.getSingletonObject();
    public DatabaseHelper dbHelper;
    public String TABLE_NAME="cart_items";
    public String CONDITION_COLUMN="item_status";
    public ProgressDialog pDialog;
    public double baseCost=0.0;
    double serviceTax=0.0;
    double serviceCharge=0.0;
    double VAT=0.0;
    double swatchBharathTax=0.0;
    double krishiKalyanTax=0.0;
    double totalCost=0.0;
    public List<String> itemIdsList= new ArrayList<>();
    public List<String> quantitiesList=new ArrayList<>();
    public List<String> itemPriceList=new ArrayList<>();
    public List<String> itemCommentList=new ArrayList<>();
    final List<Summary>summaryList=new ArrayList<>();
    public static String RestaurantName,RestaurantId,RestaurantTableId,RestaurantLocation,BrandServiceTax,BrandServiceCharge,BrandVat,Swachbarath,Krishikalyan,RestaurantTableDisplayName;
   final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        TextView summeryRestaurantName=(TextView)findViewById(R.id.summeryRestaurantName);

        SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
        RestaurantName = prefs.getString("RestaurantName", null);
        RestaurantId = prefs.getString("RestaurantId", null);
        RestaurantTableId = prefs.getString("RestaurantTableId", null);
        RestaurantTableDisplayName=prefs.getString("RestaurantTableDisplayName", null);
        RestaurantLocation = prefs.getString("RestaurantLocation", null);
        BrandServiceTax = prefs.getString("BrandServiceTax", null);
        BrandServiceCharge = prefs.getString("BrandServiceCharge", null);
        BrandVat = prefs.getString("BrandVat", null);
        Swachbarath = prefs.getString("Swachbarath", null);
        Krishikalyan = prefs.getString("Krishikalyan", null);
        summeryRestaurantName.setText(RestaurantName+" "+RestaurantLocation+", Table No: "+RestaurantTableDisplayName);
        dbHelper=new DatabaseHelper(getApplicationContext());
       // try{
            Cursor resultSet=dbHelper.getTableData(TABLE_NAME,"item_status","0");
            resultSet.moveToFirst();
            do{
                final String iName=resultSet.getString(2);
                final String iQty=resultSet.getString(3);
                baseCost+=Double.parseDouble(resultSet.getString(4));
                Log.d("info_summery",baseCost+"");
                final String iPrice=resultSet.getString(4);
                final String iImg=resultSet.getString(5);
                final String iId=resultSet.getString(1);
                //Setting data tobe sent to server
                itemIdsList.add(resultSet.getString(1));
                quantitiesList.add(resultSet.getString(3));
                itemPriceList.add(resultSet.getString(4));
                itemCommentList.add(resultSet.getString(7));
                //end

                summaryList.add(new Summary(){{
                    sItemId=iId;
                    sItemName=iName;
                    sItemImg=iImg;
                    sItemQty=iQty;
                    sItemPrice=iPrice;
                }
                });
            }while (resultSet.moveToNext());

            final SummeryItemListAdapter SIA =new SummeryItemListAdapter(summaryList,getApplicationContext());
            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.summeryRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setNestedScrollingEnabled(false);
            setSummeryPrices(baseCost);
            recyclerView.setAdapter(SIA);
            ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                    openDialogDecision(viewHolder,SIA);
                       /* String removeItemId=summaryList.get(viewHolder.getAdapterPosition()).sItemId;
                        String removeprice=summaryList.get(viewHolder.getAdapterPosition()).sItemPrice;
                        String quantity=summaryList.get(viewHolder.getAdapterPosition()).sItemQty;
                        dbHelper.removeTableData(TABLE_NAME,"item_id",removeItemId);
                        //Log.d("swiped",summaryList.get(viewHolder.getAdapterPosition()).sItemName+"");
                        itemIdsList.remove(removeItemId);
                        itemPriceList.remove(removeprice);
                        quantitiesList.remove(quantity);

                        summaryList.remove(viewHolder.getAdapterPosition());
                        baseCost-=Double.parseDouble(removeprice);
                        setSummeryPrices(baseCost);
                        SIA.notifyItemRemoved(viewHolder.getAdapterPosition());*/

                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
            Button placeOrderButton = (Button)findViewById(R.id.summeryPlaceOrderButton);
            placeOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pDialog = new ProgressDialog(SummaryActivity.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                   Log.d("info_post",android.text.TextUtils.join("|",itemCommentList));
                    Call<PlaceOrder>call = apiInterface.placeOrder(RestaurantTableId,baseCost+"",totalCost+"",Math.floor(serviceTax)+"",Math.floor(serviceCharge)+"",Math.floor(VAT)+"",Math.floor(krishiKalyanTax)+"",Math.floor(swatchBharathTax)+"",android.text.TextUtils.join(",", itemIdsList),android.text.TextUtils.join(",", quantitiesList),android.text.TextUtils.join(",",itemPriceList),android.text.TextUtils.join("|",itemCommentList));
                    call.enqueue(new Callback<PlaceOrder>() {
                        @Override
                        public void onResponse(Call<PlaceOrder> call, Response<PlaceOrder> response) {
                            pDialog.cancel();
                            SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
                            prefs.getString("code",null);
                            int showOrderStatus=prefs.getInt("showOrderStatus",0);
                            if(showOrderStatus == 0){
                                prefs.edit().putString("mainOrderId",response.body().getOrderid()).commit();
                            }
                           // Log.d("responseOrder",response.body().getOrderid()+"");
                           Boolean stats= dbHelper.updateAllTableData(TABLE_NAME,CONDITION_COLUMN,"1");
                            //Log.d("responseOrder_db",stats+"");
                            openDialog();
//                            Intent i=new Intent(getApplicationContext(),OrderStatusActivity.class);
//                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<PlaceOrder> call, Throwable t) {
                            pDialog.cancel();
                            Log.d("infoErr",t+"");
                            openDialogError();
                        }
                    });

                }
            });
       // }catch (Exception e){
       //     Log.d("err",e.toString());
       // }

    }

    private void setSummeryPrices(Double baseCost){
        //Log.d("baseCost",baseCost+"");
        double brandServiceTax=Double.parseDouble(BrandServiceTax);
        double brandServiceCharge=Double.parseDouble(BrandServiceCharge);
        double brandVAT=Double.parseDouble(BrandVat);
        double brandSB=Double.parseDouble(Swachbarath);
        double brandKK=Double.parseDouble(Krishikalyan);

        //calculated Values
        serviceTax=(baseCost/100)*brandServiceTax;
        serviceCharge=(baseCost/100)*brandServiceCharge;
        VAT=(baseCost/100)*brandVAT;
        swatchBharathTax=(baseCost/100)*brandSB;
        krishiKalyanTax=(baseCost/100)*brandKK;
        totalCost=baseCost+serviceTax+serviceCharge+VAT+swatchBharathTax+krishiKalyanTax;

        TextView sTotalBill=(TextView)findViewById(R.id.summeryTotalBill);
        sTotalBill.setText("Your Total Bill: "+getString(R.string.rs)+Math.floor(totalCost));

        TextView sBaseBill=(TextView)findViewById(R.id.summeryBaseBill);
        sBaseBill.setText("Base Bill: "+getString(R.string.rs)+Math.floor(baseCost));

        TextView sServiceTax=(TextView)findViewById(R.id.summeryServiceTax);
        sServiceTax.setText("Service Tax("+brandServiceTax+"%): "+getString(R.string.rs)+Math.floor(serviceTax));

        TextView sServiceCharge=(TextView)findViewById(R.id.summeryServiceCharge);
        sServiceCharge.setText("Service Charge("+brandServiceCharge+"%): "+getString(R.string.rs)+Math.floor(serviceCharge));

        TextView sVAT=(TextView)findViewById(R.id.summeryVAT);
        sVAT.setText("VAT("+brandVAT+"%): "+getString(R.string.rs)+Math.floor(VAT));

        TextView sSwatchBharath=(TextView)findViewById(R.id.summerySwatchBharath);
        sSwatchBharath.setText("Swatch Bharath("+brandSB+"%): "+getString(R.string.rs)+Math.floor(swatchBharathTax));

        TextView sKrishiKalyan=(TextView)findViewById(R.id.summeryKrishiKalyan);
        sKrishiKalyan.setText("Krishi Kalyan("+brandKK+"%): "+getString(R.string.rs)+Math.floor(krishiKalyanTax));

        Log.d("tax",baseCost+"-"+brandServiceTax+"-"+serviceTax+"");
    }

    public void openDialog(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_smiley_layout,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        /*TextView commonDialogMsg = (TextView) dialogView.findViewById(R.id.commonDialogMsg);
        commonDialogMsg.setText(msg);*/
        Button loginSubmit = (Button) dialogView.findViewById(R.id.loginSubmit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.cancel();
                //GO to OrderStatus page
                Intent intent = new Intent(getApplicationContext(),OrderStatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


    public void openDialogDecision(final RecyclerView.ViewHolder viewHolder, final SummeryItemListAdapter SIA){
        String msg = getResources().getString(R.string.decisionMsg);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_decision_alerts,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        TextView commonDialogMsg = (TextView) dialogView.findViewById(R.id.commonDecisionMsg);
        commonDialogMsg.setText(msg);

        Button selectYes = (Button) dialogView.findViewById(R.id.commonDecisionYes);
        selectYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.cancel();
                //GO to OrderStatus page
//                Intent intent = new Intent(getApplicationContext(),OrderStatusActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                String removeItemId=summaryList.get(viewHolder.getAdapterPosition()).sItemId;
                String removeprice=summaryList.get(viewHolder.getAdapterPosition()).sItemPrice;
                String quantity=summaryList.get(viewHolder.getAdapterPosition()).sItemQty;
                dbHelper.removeTableData(TABLE_NAME,"item_id",removeItemId);
                //Log.d("swiped",summaryList.get(viewHolder.getAdapterPosition()).sItemName+"");
                itemIdsList.remove(removeItemId);
                itemPriceList.remove(removeprice);
                quantitiesList.remove(quantity);
                SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
                prefs.getString("code",null);
                int showOrderStatus=prefs.getInt("showOrderStatus",0);
                summaryList.remove(viewHolder.getAdapterPosition());
                Log.d("infosumSize",summaryList.size()+"");
                if(summaryList.size() <= 0 && showOrderStatus == 1){
                    Intent intent = new Intent(getApplicationContext(),OrderStatusActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else if(summaryList.size() <= 0){
                    Intent intent = new Intent(getApplicationContext(),RestaurantActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("code",prefs.getString("code",null));
                    startActivity(intent);
                    finish();
                }
                baseCost-=Double.parseDouble(removeprice);
                setSummeryPrices(baseCost);
                SIA.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        Button selectNo = (Button) dialogView.findViewById(R.id.commonDecisionNo);
        selectNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SIA.notifyDataSetChanged();
                alertdialog.cancel();
            }
        });

    }

    public void openDialogError(){
        String msg = getResources().getString(R.string.errorMsg);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = this.getLayoutInflater().inflate(R.layout.common_smiley_layout,null);
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertdialog=alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
        TextView popHead=(TextView)dialogView.findViewById(R.id.commonPopHeader);
        popHead.setText("NO CONNECTION!");
        ImageView smilieImg = (ImageView)dialogView.findViewById(R.id.commonSmilie);
        smilieImg.setImageDrawable(getResources().getDrawable(R.drawable.sadsmile));
        TextView commonDialogMsg = (TextView) dialogView.findViewById(R.id.commonMsg);
        commonDialogMsg.setText(msg);
        Button loginSubmit = (Button) dialogView.findViewById(R.id.loginSubmit);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.cancel();
            }
        });

    }
    public class Summary{
        public String sItemId,sItemImg,sItemName,sItemQty,sItemPrice;
    }
}
