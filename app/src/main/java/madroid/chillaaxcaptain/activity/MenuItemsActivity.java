package madroid.chillaaxcaptain.activity;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.fragments.StartersFragment;
import madroid.chillaaxcaptain.helpers.SharedData;

public class MenuItemsActivity extends AppCompatActivity{

   SharedData sd =SharedData.getSingletonObject();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int gridMenuType=1;
    public Button submitSelectionButton=null;
    public static String RestaurantName,RestaurantId,RestaurantTableId,RestaurantLocation,RestaurantTableDisplayName;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    private boolean internetConnected=true;
    ViewPagerAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_items);
        gridMenuType=getIntent().getExtras().getInt("type");
        //Action Bar Settings
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        Log.d("Rinfos",sd.getRestaurantId());
        TextView restaurantName=(TextView)findViewById(R.id.menuRestaurantName);
        SharedPreferences prefs = getSharedPreferences(sd.Flaglists, MODE_PRIVATE);
        RestaurantName = prefs.getString("RestaurantName", null);
        RestaurantId = prefs.getString("RestaurantId", null);
        RestaurantTableId = prefs.getString("RestaurantTableId", null);
        RestaurantTableDisplayName = prefs.getString("RestaurantTableDisplayName", null);
        RestaurantLocation = prefs.getString("RestaurantLocation", null);
        restaurantName.setText(RestaurantName+" "+RestaurantLocation+", Table No: "+RestaurantTableDisplayName);
        submitSelectionButton=(Button)findViewById(R.id.submitSelectionButton);
        submitSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SummaryActivity.class);
                startActivity(intent);
            }
        });


        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorlayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        //MenuItem searchItem = ;
       final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_item));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d("info_q",query);
//                StartersFragment fragment = (StartersFragment) adapter.getCurrentFragment();
//               // StartersFragment startersFragment = (StartersFragment) getSupportFragmentManager().findFragmentById(R.id.viewpager);
//                fragment.searchItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              //  Log.d("info_q",newText);
                StartersFragment fragment = (StartersFragment) adapter.getCurrentFragment();
                // StartersFragment startersFragment = (StartersFragment) getSupportFragmentManager().findFragmentById(R.id.viewpager);

                if (TextUtils.isEmpty(newText)){
                    //Text is cleared, do your thing
                    fragment.closeSearch();
                }else{
                    fragment.searchItem(newText);
                }

                return false;
            }
        });
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        registerInternetCheckReceiver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartersFragment(), "Starter","1");
        adapter.addFragment(new StartersFragment(), "Main Course","2");
        adapter.addFragment(new StartersFragment(), "Desserts","3");
        adapter.addFragment(new StartersFragment(), "Beverages","4");
        viewPager.setAdapter(adapter);
        //Add this to show default viewable fragment
        viewPager.setCurrentItem(gridMenuType-1);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Fragment mCurrentFragment;
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, String menutype) {
            mFragmentList.add(fragment);
            Bundle bundle = new Bundle();
            bundle.putString("menuType",menutype);
            fragment.setArguments(bundle);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //  Checking for internet connection
    /**
     *  Method to register runtime broadcast receiver to show snackbar alert for internet connection..
     */
    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }

    /**
     *  Runtime Broadcast receiver inner class to capture internet connectivity events
     */
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = getConnectivityStatusString(context);
            setSnackbarMessage(status,false);
        }
    };

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
    private void setSnackbarMessage(String status,boolean showBar) {
        String internetStatus="";
        if(status.equalsIgnoreCase("Wifi enabled")||status.equalsIgnoreCase("Mobile data enabled")) {
        }else{

            internetStatus="Lost Internet Connection";

            snackbar = Snackbar
                    .make(coordinatorLayout, internetStatus, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            onStart();
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.WHITE);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            if(internetStatus.equalsIgnoreCase("Lost Internet Connection")){
                if(internetConnected){
                    snackbar.show();
                    internetConnected=false;
                }
            }else{
                if(!internetConnected){
                    internetConnected=true;
                    snackbar.show();
                }
            }
        }
    }

}
