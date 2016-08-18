package madroid.chillaaxcaptain.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.MenuItemsActivity;
import madroid.chillaaxcaptain.activity.RestaurantActivity;
import madroid.chillaaxcaptain.helpers.SharedData;

/**
 * Created by madroid on 27-07-2016.
 */
public class GridMenuAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<RestaurantActivity.MainmenuGrid>menulist;
    Context ctx;
    final SharedData sd = SharedData.getSingletonObject();
    public GridMenuAdapter(Context ctx,Activity act,List<RestaurantActivity.MainmenuGrid> menulist) {
        super();
        this.inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.menulist = menulist;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return menulist.size();
    }

    @Override
    public Object getItem(int i) {
        return menulist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        final RestaurantActivity.MainmenuGrid mainmenuGrid = (RestaurantActivity.MainmenuGrid)this.getItem(i);
        if(vi ==null)
            vi=inflater.inflate(R.layout.grid_item,null);
            ImageView img = (ImageView)vi.findViewById(R.id.gridImg);
            Picasso.with(ctx)
                    .load(sd.BaseUrlImg +mainmenuGrid.imagename)
                    .into(img);
            /*TextView itemname=(TextView)vi.findViewById(R.id.gridItemName);
            itemname.setText(mainmenuGrid.itemName);

            TextView itemCount=(TextView)vi.findViewById(R.id.gridItemCount);
            itemCount.setText(mainmenuGrid.itemCount+" Items available");*/
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked",mainmenuGrid.itemId+"");
                Intent i = new Intent(ctx,MenuItemsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("type",mainmenuGrid.itemId);
                ctx.startActivity(i);
            }
        });
        return vi;
    }
}
