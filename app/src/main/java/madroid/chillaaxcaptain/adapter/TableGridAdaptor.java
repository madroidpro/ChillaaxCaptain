package madroid.chillaaxcaptain.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.activity.MenuItemsActivity;
import madroid.chillaaxcaptain.fragments.ShowTablesFragment;
import madroid.chillaaxcaptain.helpers.DatabaseHelper;
import madroid.chillaaxcaptain.helpers.SharedData;

/**
 * Created by madroid on 18-08-2016.
 */
public class TableGridAdaptor extends BaseAdapter {

    Context ctx;
    List<ShowTablesFragment.TableList> tableLists;
    LayoutInflater inflater;
    SharedData sd=SharedData.getSingletonObject();
    DatabaseHelper dbHelper;
    public static final String TABLE_NAME="cart_items";

    public TableGridAdaptor(Context ctx, List<ShowTablesFragment.TableList> tableLists) {
        this.ctx = ctx;
        this.tableLists = tableLists;
        this.inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dbHelper=new DatabaseHelper(ctx);
    }

    @Override
    public int getCount() {
        return tableLists.size();
    }

    @Override
    public Object getItem(int i) {
        return tableLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        final ShowTablesFragment.TableList tableListItem = (ShowTablesFragment.TableList) this.getItem(i);
        if(vi == null)
            vi = inflater.inflate(R.layout.grid_item,null);
            ImageView imageView = (ImageView) vi.findViewById(R.id.gridImg);
        if(tableListItem.tableImage.equals("c"))
            imageView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.table_close));
        else{
            imageView.setImageDrawable(ctx.getResources().getDrawable(R.drawable.table_open));
        }
            TextView tableNum= (TextView)vi.findViewById(R.id.tableNumber);
            tableNum.setText("Table "+tableListItem.tableNumber);
        View gridItemLayout=vi.findViewById(R.id.gridItemLayout);
        gridItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ItemClicked",tableListItem.tableId);
                dbHelper.clearTableData(TABLE_NAME);
                Intent intent = new Intent(ctx, MenuItemsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type",1);
                SharedPreferences.Editor editor=ctx.getSharedPreferences(sd.Flaglists,ctx.MODE_PRIVATE).edit();
                editor.putString("RestaurantTableId",tableListItem.tableId);
                editor.putString("RestaurantTableDisplayName",tableListItem.tableNumber);
                editor.commit();
                ctx.startActivity(intent);
            }
        });
        return vi;
    }
}
