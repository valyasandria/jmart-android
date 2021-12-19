package valyaJmartPK.jmart_android;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Product;

/**
 * There are 2 tab layout (products and filter) and 3 menu (search, create product, and account detail)
 * that can perform their respective functions
 * @author Valya Sandria Akiela
 */
public class MainActivity extends AppCompatActivity {
    //top menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        MenuItem menu_create = menu.findItem(R.id.add);
        MenuItem menu_account = menu.findItem(R.id.account);
        MenuItem menu_search = menu.findItem(R.id.search);

        /*SearchView searchMenu = (SearchView) menu_search.getActionView();
        searchMenu.setQueryHint("search product...");

        searchMenu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchProd) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchProd) {
                listViewAdapter.getFilter().filter(searchProd);
                return false;
            }
        });*/

        Account myAcc = LoginActivity.getLoggedAccount();

        //hide or show create product (action bar +)
        /*if(myAcc.store==null){
            menu_create.setVisible(false);
        }
        else {
            menu_create.setVisible(true);
        }*/

        return true;
    }

    //button top menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //create product menu clicked
        if (id == R.id.add)
        {
            Toast.makeText(MainActivity.this,"Create product",Toast.LENGTH_SHORT).show();
            Intent intentCreate = new Intent(this, CreateProductActivity.class);
            this.startActivity(intentCreate);
            return true;
        }

        //account menu clicked
        else if(id == R.id.account)
        {
            Toast.makeText(MainActivity.this,"My Account",Toast.LENGTH_SHORT).show();
            Intent intentAccount = new Intent(this, AboutMeActivity.class);
            this.startActivity(intentAccount);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

}

