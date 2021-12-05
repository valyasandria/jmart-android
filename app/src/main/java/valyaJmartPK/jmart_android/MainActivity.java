package valyaJmartPK.jmart_android;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Menu menuHide;

    //top menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menuHide = menu;
        MenuItem menu_create = menu.findItem(R.id.add);
        if (LoginActivity.getLoggedAccount().store != null)
        {
            menu_create.setVisible(true);
        }
        //menu_create.setVisible(false);
        return true;
    }

    //tab layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.pager2);
        Spinner spinner = findViewById(R.id.spinner);
        ListView listView = findViewById(R.id.list_item);
        MenuItem menu_create = findViewById(R.id.add);
        MenuItem menu_account = findViewById(R.id.account);

        FragmentManager fm = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);



        tabLayout.addTab(tabLayout.newTab().setText("PRODUCTS"));
        tabLayout.addTab(tabLayout.newTab().setText("FILTER"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    //button top menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add)
        {
            Intent intentCreate = new Intent(this, CreateProductActivity.class);
            this.startActivity(intentCreate);
            return true;
        }
        else if(id == R.id.account)
        {
            Intent intentCreate = new Intent(this, AboutMeActivity.class);
            this.startActivity(intentCreate);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

