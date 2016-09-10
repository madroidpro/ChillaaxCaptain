package madroid.chillaaxcaptain.fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.MenuItemsActivity;
import madroid.chillaaxcaptain.helpers.DatabaseHelper;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.GetItem;
import madroid.chillaaxcaptain.model.RestaurantMenuItem;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by madroid on 28-07-2016.
 */
public class StartersFragment extends Fragment {

    interface displayButton{
        public void changeButtonState();
    }

    private  SectionedRecyclerViewAdapter sectionAdapter;
    public static Response<List<GetItem>> response_original=null;
    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    SharedData sd=SharedData.getSingletonObject();
    public Context ctx=null;
    View view=null;
    RecyclerView recyclerView=null;
    public String type="1";
    public DatabaseHelper dbHelper=null;
    public static final String TABLE_NAME="cart_items";
    public static final String CONDITION_COLUMN="item_status";
    public static final String CONDITION_COLUMN_COMMENT="item_comment";
    public static final String CONDITION_KEY="0";
    private ProgressDialog pDialog;
    public StartersFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx =getActivity();
        type=getArguments().getString("menuType");
        dbHelper=new DatabaseHelper(ctx);
        int cnt=dbHelper.getRecordCount(TABLE_NAME,CONDITION_COLUMN,CONDITION_KEY);
        Log.d("infocnt",cnt+"");
        MenuItemsActivity menuItemsActivity=(MenuItemsActivity)ctx;
        if(cnt>0){
            menuItemsActivity.submitSelectionButton.setVisibility(View.VISIBLE);
        }else{
            menuItemsActivity.submitSelectionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("info_stat","paused");
    }

    public void searchItem(String query){
        String headerImg="na.jpg";
        String headerText="Results";
        List<RestaurantMenuItem> list = new ArrayList<>();
        if(response_original != null){
                int len=response_original.body().size();
            Log.d("info_qu",len+"");
                for(int i=0;i<len;i++){
                    List<RestaurantMenuItem>itemList=response_original.body().get(i).getRestaurantMenuItem();
                    for(int j=0;j<itemList.size();j++){
                        if (itemList.get(j).getItemName().toLowerCase().contains(query) || itemList.get(j).getDisplayNumber().equals(query)){
                            RestaurantMenuItem item = new RestaurantMenuItem();
                            item.setItemName(itemList.get(j).getItemName());
                            item.setDescription(itemList.get(j).getDescription());
                            item.setItemPrice(itemList.get(j).getItemPrice());
                            item.setItemImgSmall(itemList.get(j).getItemImgSmall());
                            item.setId(itemList.get(j).getId());
                            list.add(item);
                            //Log.d("info_matc",itemList.get(j).getItemName().toString());
                        }
                    }
            }
            sectionAdapter.removeAllSections();
            sectionAdapter.addSection(new MenuItemSection(headerImg,headerText,list));
            final GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch(sectionAdapter.getSectionItemViewType(position)) {
                        case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                            return 3;
                        default:
                            return 1;
                    }
                }
            });

            recyclerView.setLayoutManager(glm);
            recyclerView.setAdapter(sectionAdapter);
            sectionAdapter.notifyDataSetChanged();

        }
    }

    public void closeSearch(){

        if(response_original!= null){
            renderRecycle(response_original,view);
            Log.d("info_close","closed");
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.starter_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            pDialog=new ProgressDialog(ctx);
            pDialog.setMessage("Please wait..");
            pDialog.setCancelable(false);
            pDialog.show();
            final Call<List<GetItem>>itemcall= apiInterface.getItems(sd.getRestaurantId(),type);
            itemcall.enqueue(new Callback<List<GetItem>>() {
                @Override
                public void onResponse(Call<List<GetItem>> call, Response<List<GetItem>> response) {
                    renderRecycle(response,view);
                     pDialog.cancel();

                }

                @Override
                public void onFailure(Call<List<GetItem>> call, Throwable t) {
                    pDialog.cancel();
                }
            });
        }
    }


    public void renderRecycle(Response<List<GetItem>> response,View view){
        response_original=response;

        int i=0;
        int hd=0;
        Map<String,Integer> headers=new LinkedHashMap<>();
        sectionAdapter.removeAllSections();
        try {
            int bodylength=response.body().size();
            Log.d("info_len",bodylength+"");
            while(response.body().get(i)!= null){
                // Log.d("response",response.body().get(i).getRestaurantMenuItemGroup().getName()+"");
                String hImg=response.body().get(i).getRestaurantMenuItemGroup().getGroupImg();
                String hTxt=response.body().get(i).getRestaurantMenuItemGroup().getName();
                List<RestaurantMenuItem>itemList=response.body().get(i).getRestaurantMenuItem();
                if(i == 0)
                    hd=1;
                else{
                    hd+=response.body().get(i-1).getRestaurantMenuItem().size();

                }
                headers.put(hTxt,hd+3);
                sectionAdapter.addSection(new MenuItemSection(hImg,hTxt,itemList));
                i++;
            }
        }catch (Exception e){
            Log.d("info_response","End Reached!");
        }

        try {
            final GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch(sectionAdapter.getSectionItemViewType(position)) {
                        case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                            return 3;
                        default:
                            return 1;
                    }
                }
            });
            recyclerView.setLayoutManager(glm);
            recyclerView.setAdapter(sectionAdapter);
            sectionAdapter.notifyDataSetChanged();
            pDialog.cancel();
            FloatingActionsMenu rightLabels = (FloatingActionsMenu) view.findViewById(R.id.fab);
            Iterator iterator = headers.entrySet().iterator();
            while (iterator.hasNext()){
                final Map.Entry pair= (Map.Entry) iterator.next();
                String name= (String) pair.getKey();
                FloatingActionButton headerButton = new FloatingActionButton(ctx);
                headerButton.setTitle(name);
                headerButton.setSize(FloatingActionButton.SIZE_MINI);
                headerButton.setColorNormal(getResources().getColor(R.color.colorPrimary));
                headerButton.setColorPressed(getResources().getColor(R.color.colorPrimaryDark));
                headerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        glm.scrollToPositionWithOffset((int)pair.getValue(),0);
                        // recyclerView.smoothScrollToPosition((int)pair.getValue());
                    }
                });
                rightLabels.addButton(headerButton);
            }


        }catch (Exception e){
            Log.d("err",e.toString());
            pDialog.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("info_status","resume");
//        if (getActivity() instanceof AppCompatActivity) {
//            AppCompatActivity activity = ((AppCompatActivity) getActivity());
//            if (activity.getSupportActionBar() != null)
//                activity.getSupportActionBar().setTitle(R.string.nav_example1);
//        }
    }

    class MenuItemSection extends StatelessSection implements displayButton {

        String headerImg,headerText;
        List<RestaurantMenuItem> list;

        public MenuItemSection(String headerImg, String headerText, List<RestaurantMenuItem> list) {
            super(R.layout.header_listview, R.layout.menu_list_item);

            this.headerImg = headerImg;
            this.headerText = headerText;
            this.list = list;
        }

        @Override
        public void changeButtonState() {
            int cnt=dbHelper.getRecordCount(TABLE_NAME,CONDITION_COLUMN,CONDITION_KEY);
            Log.d("info_button",cnt+"");
            MenuItemsActivity menuItemsActivity=(MenuItemsActivity)ctx;
            if(cnt>0){
                menuItemsActivity.submitSelectionButton.setVisibility(View.VISIBLE);
            }else{
                menuItemsActivity.submitSelectionButton.setVisibility(View.GONE);
            }

        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            String sMenuItemName = list.get(position).getItemName();
            String sMenuItemDesc=list.get(position).getDescription();
            final String sMenuItemPrice=list.get(position).getItemPrice();
            final String sMenuImg=list.get(position).getItemImgSmall();
            final String sMenuItemId=list.get(position).getId();

            // Building cart table column data
            final ContentValues cart_data=new ContentValues();
            cart_data.put("item_id",sMenuItemId);
            cart_data.put("item_name",sMenuItemName);
            cart_data.put("item_quantity",1);
            cart_data.put("item_price",sMenuItemPrice);
            cart_data.put("item_image",sMenuImg);
            cart_data.put("item_status",0);
            cart_data.put("item_comment","N/A");

            itemHolder.menuItemName.setText(sMenuItemName);
            itemHolder.menuItemDesc.setText(sMenuItemDesc);
            try {
                int qty;
                int price;
                Cursor resultSet = dbHelper.getTableData(TABLE_NAME, "item_id", sMenuItemId,"0");
                resultSet.moveToFirst();
                qty = Integer.parseInt(resultSet.getString(3));
                price = Integer.parseInt(resultSet.getString(4));
                itemHolder.menuItemPrice.setText(getString(R.string.rs)+price);
                itemHolder.menuItemAddButton.setText("Item Added");
                itemHolder.menuItemAddButton.setBackground(getResources().getDrawable(R.drawable.button_style_green));
                itemHolder.menuItemNamesLayout.setVisibility(View.GONE);
                itemHolder.subMenuSubLayout.setVisibility(View.VISIBLE);
                itemHolder.menuItemQty.setText("Qty "+qty);
                itemHolder.menuItemEditComment.setText(resultSet.getString(7));
                Log.d("price",price+"");
            }catch (Exception e){
                Log.d("err",e.toString());
                itemHolder.menuItemAddButton.setText("Add Item");
                itemHolder.menuItemAddButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                itemHolder.subMenuSubLayout.setVisibility(View.GONE);
                itemHolder.menuItemNamesLayout.setVisibility(View.VISIBLE);
                dbHelper.removeTableData(TABLE_NAME,"item_id",sMenuItemId);
                itemHolder.menuItemPrice.setText(getString(R.string.rs)+sMenuItemPrice);
            }


            Picasso.with(ctx)
                    .load(sd.BaseUrlImg +"item-img/small/"+sMenuImg)
                    .into(itemHolder.menuItemImg);

            // Main Item Add function
            itemHolder.menuItemAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemHolder.menuItemAddButton.setText("Item Added");
                    itemHolder.menuItemAddButton.setBackground(getResources().getDrawable(R.drawable.button_style_green));
                    itemHolder.menuItemNamesLayout.setVisibility(View.GONE);
                    itemHolder.subMenuSubLayout.setVisibility(View.VISIBLE);
                    itemHolder.menuItemQty.setText("Qty "+1);
                    dbHelper.insertTableData(TABLE_NAME,cart_data);
                    changeButtonState();

                }
            });

            // Main Item Remove All function
            itemHolder.menuItemRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemHolder.menuItemAddButton.setText("Add Item");
                    itemHolder.menuItemAddButton.setBackground(getResources().getDrawable(R.drawable.button_style));
                    itemHolder.subMenuSubLayout.setVisibility(View.GONE);
                    itemHolder.menuItemNamesLayout.setVisibility(View.VISIBLE);
                    dbHelper.removeTableData(TABLE_NAME,"item_id",sMenuItemId);
                    int price=Integer.parseInt(sMenuItemPrice);
                    itemHolder.menuItemPrice.setText(getString(R.string.rs)+price);
                    changeButtonState();
                }
            });


            itemHolder.menuItemEditComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        Log.d("info_comment",""+itemHolder.menuItemEditComment.getText());
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        if(itemHolder.menuItemEditComment.getText().length()>0)
                        dbHelper.updateTableRowData(TABLE_NAME,sMenuItemId,CONDITION_COLUMN_COMMENT,itemHolder.menuItemEditComment.getText().toString());
                        return true;
                    }
                    return false;
                }
            });

            //sub increase quantity
            itemHolder.menuItemAddQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //increase qty by 1 and price = qty*unitprice
                   int qty= dbHelper.updateTableData(TABLE_NAME,1,"item_id",sMenuItemId);
                   if(qty != -1){
                       itemHolder.menuItemQty.setText("Qty "+qty);
                       int price=Integer.parseInt(sMenuItemPrice)*qty;
                       itemHolder.menuItemPrice.setText(getString(R.string.rs)+price);
                   }
                   // itemHolder.subMenuSubLayout.setVisibility(View.GONE);
                }
            });
            //sub increase quantity
            itemHolder.menuItemRemoveQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //decrease qty by 1
                    int qty= dbHelper.updateTableData(TABLE_NAME,0,"item_id",sMenuItemId);
                    if(qty != -1){
                        itemHolder.menuItemQty.setText("Qty "+qty);
                        int price=Integer.parseInt(sMenuItemPrice)*qty;
                        itemHolder.menuItemPrice.setText(getString(R.string.rs)+price);
                    }

                    //itemHolder.subMenuSubLayout.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.subMenuHeaderText.setText(headerText);
            Picasso.with(ctx)
                    .load(sd.BaseUrlImg +"item-img/big/"+headerImg)
                    .into(headerHolder.subMenuHeaderImg);
        }

    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView subMenuHeaderText;
        private final ImageView subMenuHeaderImg;

        public HeaderViewHolder(View view) {
            super(view);

            subMenuHeaderText = (TextView) view.findViewById(R.id.subMenuHeaderText);
            subMenuHeaderImg=(ImageView)view.findViewById(R.id.subMenuHeaderImg);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private final View rootView;
        private final ImageView menuItemImg;
        private final TextView menuItemPrice,menuItemName,menuItemDesc,menuItemQty;
        private final Button menuItemAddButton,menuItemRemoveButton,menuItemRemoveQuantity,menuItemAddQuantity;
        private final View subMenuSubLayout,menuItemNamesLayout;
        private final EditText menuItemEditComment;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            menuItemImg = (ImageView) view.findViewById(R.id.menuItemImg);
            menuItemPrice = (TextView) view.findViewById(R.id.menuItemPrice);
            menuItemName = (TextView) view.findViewById(R.id.menuItemName);
            menuItemDesc = (TextView) view.findViewById(R.id.menuItemDesc);
            menuItemEditComment = (EditText) view.findViewById(R.id.menuItemEditComment);
            menuItemQty=(TextView) view.findViewById(R.id.menuItemQty);
            menuItemAddButton=(Button)view.findViewById(R.id.menuItemAddButton);
            subMenuSubLayout=view.findViewById(R.id.subMenuSubLayout);
            menuItemNamesLayout=view.findViewById(R.id.menuItemNamesLayout);
            menuItemRemoveButton=(Button)view.findViewById(R.id.menuItemRemoveButton);
            menuItemRemoveQuantity=(Button)view.findViewById(R.id.menuItemRemoveQuantity);
            menuItemAddQuantity=(Button)view.findViewById(R.id.menuItemAddQuantity);
        }
    }
}
