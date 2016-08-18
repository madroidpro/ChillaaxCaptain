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
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.SubHeader;
import madroid.chillaaxcaptain.model.SubMenuItem;

/**
 * Created by madroid on 29-07-2016.
 */
public class SubMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    SharedData sd = SharedData.getSingletonObject();
    SubHeader header;
    List<SubMenuItem>listItems;
    Context ctx;

    public SubMenuAdapter(SubHeader header, List<SubMenuItem> listItems,Context ctx) {
        this.header = header;
        this.listItems = listItems;
        this.ctx=ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_listview, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    private SubMenuItem getItem(int position)
    {
        return listItems.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader)holder;
           // VHheader.subMenuImg.setBackgroundResource(header.getSubHeaderImg());
            Picasso.with(ctx)
                    .load(sd.BaseUrlImg +"item-img/big/"+header.getSubHeaderImg())
                    .into(VHheader.subMenuImg);
            VHheader.subMenuHeaderText.setText(header.getSubHeaderText());
        }
        else if(holder instanceof VHItem)
        {
            SubMenuItem currentItem = getItem(position-1);
            VHItem VHitem = (VHItem)holder;
            VHitem.menuItemName.setText(currentItem.getMenuItemName());
            VHitem.menuItemDesc.setText(currentItem.getMenuItemDesc());
            VHitem.menuItemPrice.setText(currentItem.getMenuItemPrice());
            Picasso.with(ctx)
                    .load(sd.BaseUrlImg +"item-img/big/"+currentItem.getMenuItemImg())
                    .into(VHitem.menuItemImg);
            //VHitem.menuItemImg.setBackgroundResource(currentItem.getMenuItemImg());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return listItems.size()+1;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        ImageView subMenuImg;
        TextView subMenuHeaderText;
        public VHHeader(View itemView) {
            super(itemView);
            this.subMenuImg = (ImageView)itemView.findViewById(R.id.subMenuHeaderImg);
            this.subMenuHeaderText = (TextView)itemView.findViewById(R.id.subMenuHeaderText);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView menuItemName,menuItemDesc,menuItemPrice;
        ImageView menuItemImg;
        public VHItem(View itemView) {
            super(itemView);
            this.menuItemName = (TextView)itemView.findViewById(R.id.menuItemName);
            this.menuItemDesc = (TextView)itemView.findViewById(R.id.menuItemDesc);
            this.menuItemPrice = (TextView)itemView.findViewById(R.id.menuItemPrice);
            this.menuItemImg = (ImageView)itemView.findViewById(R.id.menuItemImg);
        }
    }
}
