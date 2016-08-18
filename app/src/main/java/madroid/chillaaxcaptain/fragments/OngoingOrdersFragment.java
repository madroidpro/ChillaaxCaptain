package madroid.chillaaxcaptain.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.system.Os;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.adapter.OngoingOrderItemListAdapter;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.OpenTables;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by madroid on 18-08-2016.
 */
public class OngoingOrdersFragment extends Fragment {
    Context ctx=null;
    SharedData sd = SharedData.getSingletonObject();
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    String oStatus="";
    public static String RestaurantId,RestaurantName;
    public OngoingOrdersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getActivity();
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(sd.Flaglists,ctx.MODE_PRIVATE);
        RestaurantId=sharedPreferences.getString("RestaurantId",null);
        RestaurantName=sharedPreferences.getString("RestaurantName",null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ongoing_order_fragment,null);
        getOngoingOrders(view);
        return view;
    }

    private void getOngoingOrders(final View view){
        Call<OpenTables>call = apiInterface.getOngoingOrder("1",RestaurantId);
        call.enqueue(new Callback<OpenTables>() {
            @Override
            public void onResponse(Call<OpenTables> call, Response<OpenTables> response) {
                Log.d("info_ongoing",response.body().getTables().get(0).getRestaurantOrder().get(0).getId());

                int Tsize=response.body().getTables().size();
                List<OngoingOrderItem>ongoingOrderItemsList=new ArrayList<OngoingOrderItem>();
                for(int i=0;i<Tsize;i++){
                    int Osize=response.body().getTables().get(i).getRestaurantOrder().size();
                    if(Osize !=0){
                        final String oStatus_code=response.body().getTables().get(i).getRestaurantOrder().get(0).getStatus();
                        Log.d("info_ongoing",oStatus_code);
                        switch (oStatus_code){
                            case "n":
                                 oStatus="ORDER PLACED";
                                break;
                            case "q":
                                 oStatus="ORDER PROCESSING";
                                break;
                            default:
                                oStatus ="ORDER PLACED";
                                break;
                        }
                        final String orderId_=response.body().getTables().get(i).getRestaurantOrder().get(0).getId();
                        final String oTableNo=response.body().getTables().get(i).getRestaurantTable().getDisplayName();
                        final String oTableId=response.body().getTables().get(i).getRestaurantTable().getId();
                        ongoingOrderItemsList.add(new OngoingOrderItem(){{
                            tableNo=oTableNo;
                            tableId=oTableId;
                            orderStatus=oStatus;
                            orderId=RestaurantName+"RES"+orderId_;
                        }});
                    }

                }

                OngoingOrderItemListAdapter listAdapter=new OngoingOrderItemListAdapter(ctx,ongoingOrderItemsList);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ongoingFragmentRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<OpenTables> call, Throwable t) {

            }
        });
    }


    public class OngoingOrderItem{
        public String tableNo,tableId,orderStatus,orderId;
    }
}
