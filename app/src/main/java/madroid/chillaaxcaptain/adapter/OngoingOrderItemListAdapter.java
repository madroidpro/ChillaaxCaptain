package madroid.chillaaxcaptain.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.OrderStatusActivity;
import madroid.chillaaxcaptain.fragments.OngoingOrdersFragment;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by madroid on 18-08-2016.
 */
public class OngoingOrderItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    SharedData sd=SharedData.getSingletonObject();
    Context ctx;
    List<OngoingOrdersFragment.OngoingOrderItem>ongoingOrderItemList;
    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    public String RestaurantId;
    public OngoingOrderItemListAdapter(Context ctx, List<OngoingOrdersFragment.OngoingOrderItem> ongoingOrderItemList) {
        this.ctx = ctx;
        this.ongoingOrderItemList = ongoingOrderItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View ItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_order_item,parent,false);
        return new ItemViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OngoingOrdersFragment.OngoingOrderItem ongoingOrderItem=ongoingOrderItemList.get(position);
      final  ItemViewHolder vh = (ItemViewHolder) holder;
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(sd.Flaglists,ctx.MODE_PRIVATE);
        RestaurantId=sharedPreferences.getString("RestaurantId",null);
        vh.ongoingTableNumber.setText(ongoingOrderItem.tableNo);
        vh.ongoingOrderStatus.setText(ongoingOrderItem.orderStatus);
        vh.ongoingOrderId.setText("Order ID: "+ongoingOrderItem.orderId);
        vh.ongoingRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.addEditLayout.setVisibility(View.GONE);
                vh.cashcardlayout.setVisibility(View.VISIBLE);
            }
        });
        vh.ongoingCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.cashcardlayout.setVisibility(View.GONE);
                vh.addEditLayout.setVisibility(View.VISIBLE);
            }
        });
        vh.ongoingEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=ctx.getSharedPreferences(sd.Flaglists,ctx.MODE_PRIVATE).edit();
                editor.putString("RestaurantTableId",ongoingOrderItem.tableId);
                editor.putString("RestaurantTableDisplayName",ongoingOrderItem.tableNo);
                editor.commit();
                Intent intent=new Intent(ctx, OrderStatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        vh.ongoingViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=ctx.getSharedPreferences(sd.Flaglists,ctx.MODE_PRIVATE).edit();
                editor.putString("RestaurantTableId",ongoingOrderItem.tableId);
                editor.putString("RestaurantTableDisplayName",ongoingOrderItem.tableNo);
                editor.commit();
                Intent intent=new Intent(ctx, OrderStatusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        vh.ongoingCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raiseRequest(ongoingOrderItem.tableId,ctx.getResources().getString(R.string.requestBill),ctx.getResources().getString(R.string.cardPay));
                vh.cashcardlayout.setVisibility(View.GONE);
                vh.OrderBillRequested.setVisibility(View.VISIBLE);
            }
        });
        vh.ongoingCashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raiseRequest(ongoingOrderItem.tableId,ctx.getResources().getString(R.string.requestBill),ctx.getResources().getString(R.string.cashPay));
                vh.cashcardlayout.setVisibility(View.GONE);
                vh.OrderBillRequested.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ongoingOrderItemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView ongoingOrderStatus,ongoingTableNumber,ongoingOrderId,OrderBillRequested;
        Button ongoingRequestButton,ongoingEditButton,ongoingCashButton,ongoingCardButton,ongoingCancelButton;
        View cashcardlayout,addEditLayout,ongoingViewLayout;
        public ItemViewHolder(View itemView) {
            super(itemView);
            this.ongoingOrderStatus=(TextView)itemView.findViewById(R.id.ongoingOrderStatus);
            this.ongoingTableNumber=(TextView)itemView.findViewById(R.id.ongoingTableNumber);
            this.ongoingOrderId=(TextView)itemView.findViewById(R.id.ongoingOrderId);
            this.OrderBillRequested=(TextView)itemView.findViewById(R.id.OrderBillRequested);
            this.ongoingRequestButton=(Button) itemView.findViewById(R.id.ongoingRequestButton);
            this.ongoingEditButton=(Button) itemView.findViewById(R.id.ongoingEditButton);
            this.ongoingCashButton=(Button) itemView.findViewById(R.id.ongoingCashButton);
            this.ongoingCardButton=(Button) itemView.findViewById(R.id.ongoingCardButton);
            this.ongoingCancelButton=(Button) itemView.findViewById(R.id.ongoingCancelButton);
            this.cashcardlayout=itemView.findViewById(R.id.cashcardlayout);
            this.addEditLayout=itemView.findViewById(R.id.addEditLayout);
            this.ongoingViewLayout=itemView.findViewById(R.id.ongoingViewLayout);

        }
    }

    public void raiseRequest(String RestaurantTableId,String requestType,String paymentType){

        Call<ResponseBody> raiseCall=apiInterface.raiseRequest(RestaurantId,RestaurantTableId,requestType,paymentType);
        raiseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               // pDialog.cancel();
                Log.d("responseRaise",response.raw()+"");
                //Deleting router preferences
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}


