package madroid.chillaaxcaptain.activity;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import madroid.chillaaxcaptain.R;
import madroid.chillaaxcaptain.adapter.TableGridAdaptor;
import madroid.chillaaxcaptain.fragments.OngoingOrdersFragment;
import madroid.chillaaxcaptain.fragments.ShowTablesFragment;
import madroid.chillaaxcaptain.fragments.StartersFragment;
import madroid.chillaaxcaptain.helpers.SharedData;
import madroid.chillaaxcaptain.model.OpenTables;
import madroid.chillaaxcaptain.rest.ApiClient;
import madroid.chillaaxcaptain.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenTableActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    SharedData sd = SharedData.getSingletonObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opentable);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //getTables();
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShowTablesFragment(), "Place Order");
        adapter.addFragment(new OngoingOrdersFragment(), "Ongoing Orders");
        viewPager.setAdapter(adapter);
        //Add this to show default viewable fragment
        //viewPager.setCurrentItem(gridMenuType-1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
