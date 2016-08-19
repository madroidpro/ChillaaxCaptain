package madroid.chillaaxcaptain.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.OrderStatusActivity;
import madroid.chillaaxcaptain.helpers.SharedData;

/**
 * Created by madroid on 03-08-2016.
 */
public class OrderDetailsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public SharedData sd=SharedData.getSingletonObject();
    List<OrderStatusActivity.MyOrderItems>orderItemsList;
    Context ctx;

    public OrderDetailsItemAdapter(List<OrderStatusActivity.MyOrderItems> orderItemsList, Context ctx) {
        this.orderItemsList = orderItemsList;
        this.ctx = ctx;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Oview = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_item,parent,false);
        return new OrderDetailsViewHolder(Oview);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderStatusActivity.MyOrderItems orderlist = orderItemsList.get(position);
       final OrderDetailsViewHolder vh=(OrderDetailsViewHolder)holder;
        vh.orderStatusBill.setText("Bill: "+ctx.getString(R.string.rs)+orderlist.oItemSetPrice);
        vh.orderStatusItemCount.setText(orderlist.oItemSetQty+" Items Ordered");
        vh.orderStatusId.setText(orderlist.oItemSetOrderId);
        vh.orderStatusText.setText(orderlist.oItemStatusText);
        vh.orderStatusSteward.setText(orderlist.oItemSteward);
        List<String> ls=orderlist.oimageList;
        int i=0;
        while (i<ls.size() && i<5){

            Picasso.with(ctx)
                    .load(sd.BaseUrlImg +"item-img/small/"+ls.get(i))
                    .into(vh.oImags.get(i));
            i++;
        }
        List<OrderStatusActivity.SubMenuItems>subMenuItems=orderlist.subMenuItems;
        int j=0;
        LinearLayout  cView = (LinearLayout) vh.childViewLayout;
        while (j <subMenuItems.size()){
            View vi = LayoutInflater.from(ctx).inflate(R.layout.sub_item_order_details,null);
            TextView subItemName = (TextView) vi.findViewById(R.id.subItemName);
            subItemName.setText(subMenuItems.get(j).itemName);
            TextView subItemQty = (TextView) vi.findViewById(R.id.subItemQty);
            subItemQty.setText("Qty: "+subMenuItems.get(j).itemQty);
            cView.addView(vi);
            j++;
        }

        vh.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vh.childViewLayout.getVisibility()== View.VISIBLE){
                    vh.childViewLayout.setVisibility(View.GONE);
                }else{
                    vh.childViewLayout.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }

    class OrderDetailsViewHolder extends RecyclerView.ViewHolder{
        TextView orderStatusItemCount,orderStatusBill,orderStatusSteward,orderStatusId,orderStatusText;
        List<ImageView>oImags = new ArrayList<>();
        View childViewLayout,cardView;
        public OrderDetailsViewHolder(View itemView) {
            super(itemView);
            this.orderStatusItemCount = (TextView)itemView.findViewById(R.id.orderStatusItemCount);
            this.orderStatusText=(TextView)itemView.findViewById(R.id.orderStatusText);
            this.orderStatusBill = (TextView)itemView.findViewById(R.id.orderStatusBill);
            this.orderStatusSteward = (TextView)itemView.findViewById(R.id.orderStatusSteward);
            this.orderStatusId = (TextView)itemView.findViewById(R.id.orderStatusId);
            this.oImags.add((ImageView) itemView.findViewById(R.id.oimg1));
            this.oImags.add((ImageView) itemView.findViewById(R.id.oimg2));
            this.oImags.add((ImageView) itemView.findViewById(R.id.oimg3));
            this.oImags.add((ImageView) itemView.findViewById(R.id.oimg4));
            this.oImags.add((ImageView) itemView.findViewById(R.id.oimg5));
            this.childViewLayout=itemView.findViewById(R.id.childViewLayout);
            this.cardView=itemView.findViewById(R.id.cardView);
//
//            this.oimg1 =(ImageView) itemView.findViewById(R.id.oimg1);
//            this.oimg2 =(ImageView) itemView.findViewById(R.id.oimg2);
//            this.oimg3 =(ImageView) itemView.findViewById(R.id.oimg3);
//            this.oimg4 =(ImageView) itemView.findViewById(R.id.oimg4);
//            this.oimg5 =(ImageView) itemView.findViewById(R.id.oimg5);
        }
    }
}
