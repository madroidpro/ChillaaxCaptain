package madroid.chillaaxcaptain.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.SummaryActivity;
import madroid.chillaaxcaptain.helpers.SharedData;

/**
 * Created by madroid on 01-08-2016.
 */
public class SummeryItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public SharedData sd=SharedData.getSingletonObject();
    List<SummaryActivity.Summary>summaryList;
    Context ctx;

    public SummeryItemListAdapter(List<SummaryActivity.Summary> summaryList, Context ctx) {
        this.summaryList = summaryList;
        this.ctx = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View summeryView=LayoutInflater.from(parent.getContext()).inflate(R.layout.summery_item,parent,false);
        return new SummeryViewHolder(summeryView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SummaryActivity.Summary summary=summaryList.get(position);
        SummeryViewHolder sv =(SummeryViewHolder)holder;
        sv.summeryName.setText(summary.sItemName);
        sv.summeryPrice.setText("Price: "+ctx.getString(R.string.rs)+summary.sItemPrice);
        sv.summeryQty.setText("Qty: "+summary.sItemQty);
        Picasso.with(ctx)
                .load(sd.BaseUrlImg +"item-img/small/"+summary.sItemImg)
                .into(sv.summeryImg);
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }


    public class SummeryViewHolder extends RecyclerView.ViewHolder{
        ImageView summeryImg;
        TextView summeryName,summeryQty,summeryPrice;
        public SummeryViewHolder(View itemView) {
            super(itemView);
            this.summeryImg = (ImageView)itemView.findViewById(R.id.summeryItemImage);
            this.summeryName = (TextView)itemView.findViewById(R.id.summeryItemName);
            this.summeryQty = (TextView)itemView.findViewById(R.id.summeryItemQuantity);
            this.summeryPrice = (TextView)itemView.findViewById(R.id.summeryItemPrice);
        }
    }
}
