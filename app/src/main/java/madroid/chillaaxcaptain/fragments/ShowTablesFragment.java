package madroid.chillaaxcaptain.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.OpenTableActivity;
import madroid.chillaaxcaptain.adapter.TableGridAdaptor;
import madroid.chillaaxcaptain.helpers.DatabaseHelper;
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
public class ShowTablesFragment extends Fragment {
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SharedData sd = SharedData.getSingletonObject();
    Context ctx =null;
    private ProgressDialog pDialog;
    OpenTableActivity openactivity = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    public ShowTablesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx =getActivity();
        openactivity = (OpenTableActivity) ctx;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tables_fragment, container, false);
        getTables(view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeViewLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // onRefresh action here
                swipeRefreshLayout.setRefreshing(true);
                getTables(view);
            }
        });
        return view;
    }
    private void getTables(final View view) {
        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<OpenTables> call = apiInterface.login(sd.username,sd.password);
        call.enqueue(new Callback<OpenTables>() {
            @Override
            public void onResponse(Call<OpenTables> call, Response<OpenTables> response) {
                pDialog.cancel();
                int Tsize = response.body().getTables().size();
                sd.setRestaurantId(response.body().getRestaurant().getId());
                sd.setRestaurantName(response.body().getRestaurant().getName());
                sd.setRestaurantAddress(response.body().getRestaurant().getAddress());
                sd.setRestaurantLocation(response.body().getRestaurant().getLocation());
                // sd.setRestaurantTableId(response.body().getRestaurantTable().getId());
                // sd.setRestaurantTableDisplayName(response.body().getRestaurantTable().getDisplayName());
                sd.setRestaurantBrandId(response.body().getBrand().getId());
                sd.setBrandName(response.body().getBrand().getName());
                sd.setBrandServiceTax(response.body().getBrand().getServiceTax());
                sd.setBrandServiceCharge(response.body().getBrand().getServiceCharge());
                sd.setBrandVat(response.body().getBrand().getVat());
                sd.setBrandType(response.body().getBrand().getType());
                sd.setKrishikalyan(response.body().getBrand().getKrishikalyan());
                sd.setSwachbarath(response.body().getBrand().getSwachbarath());
                sd.setVat2(response.body().getBrand().getVat2());

                SharedPreferences.Editor editor=ctx.getSharedPreferences(sd.Flaglists,ctx.MODE_PRIVATE).edit();
                editor.putString("RestaurantId",sd.getRestaurantId());
                editor.putString("RestaurantName",sd.getRestaurantName());
                // editor.putString("RestaurantTableId",sd.getRestaurantTableId());
                //editor.putString("RestaurantTableDisplayName",sd.getRestaurantTableDisplayName());
                editor.putString("RestaurantLocation",sd.getRestaurantLocation());
                editor.putString("BrandServiceTax",sd.getBrandServiceTax());
                editor.putString("BrandServiceCharge",sd.getBrandServiceCharge());
                editor.putString("Krishikalyan",sd.getKrishikalyan());
                editor.putString("Swachbarath",sd.getSwachbarath());
                editor.putString("BrandVat",sd.getBrandVat());
                editor.commit();
                TextView restaurantNameFragment= (TextView) view.findViewById(R.id.restaurantNameFragment);
                restaurantNameFragment.setText(sd.getRestaurantName()+", "+sd.getRestaurantLocation());
                Log.d("info_res",response.body().getBrand().getServiceTax());
                List<TableList> tList= new ArrayList<>();
                for(int i=0;i<Tsize;i++){
                    final String tableNum= response.body().getTables().get(i).getRestaurantTable().getDisplayName();
                    final String tableStatus= response.body().getTables().get(i).getRestaurantTable().getStatus();
                    final String tableId_=response.body().getTables().get(i).getRestaurantTable().getId();
                    tList.add(new TableList(){{
                                  tableNumber = tableNum;
                                  tableImage = tableStatus;
                                  tableId=tableId_;
                              }}
                    );
                }
                TableGridAdaptor tableGridAdaptor = new TableGridAdaptor(ctx,tList);
                GridView tableGrid = (GridView)view.findViewById(R.id.tableGrid);
                tableGridAdaptor.notifyDataSetChanged();
                tableGrid.setAdapter(tableGridAdaptor);
                Log.d("info_tables",response.body().getTables().size()+"");
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<OpenTables> call, Throwable t) {
                Log.d("err_info",t.toString());
                pDialog.cancel();
            }
        });
    }


    public class TableList{
        public String tableNumber,tableImage,tableId;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
