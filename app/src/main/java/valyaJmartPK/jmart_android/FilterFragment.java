package valyaJmartPK.jmart_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.model.ProductCategory;
import valyaJmartPK.jmart_android.request.RequestFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#} factory method to
 * create an instance of this fragment.
 *
 * Used to Filtered Search for available products
 * @author Valya Sandria Akiela
 */

public class FilterFragment extends Fragment {

    private static final Gson gson = new Gson();
    public static int status = 0;
    public static ArrayList<Product> listFiltered = new ArrayList<Product>();

    Account account = LoginActivity.getLoggedAccount();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View inflaterView = inflater.inflate(R.layout.fragment_filter,container,false);

        EditText filterName = inflaterView.findViewById(R.id.input_name);
        EditText filterLowPrice = inflaterView.findViewById(R.id.input_low);
        EditText filterHighPrice = inflaterView.findViewById(R.id.input_high);
        CheckBox checkUsed = inflaterView.findViewById(R.id.checkBox);
        CheckBox checkNew = inflaterView.findViewById(R.id.checkBox2);
        Button applyButton = inflaterView.findViewById(R.id.button3);
        Button clearButton = inflaterView.findViewById(R.id.button4);
        Spinner spinCat = inflaterView.findViewById(R.id.spinner);

        //choose one radio button for products condition
        checkUsed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean button)
            {
                if(button) checkNew.setChecked(false);
            }
        });

        checkNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean button) {
                if(button) checkUsed.setChecked(false);
            }
        });

        //filter search products
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Filter search products",Toast.LENGTH_SHORT).show();

                String productName = filterName.getText().toString();
                String lowestPrice = filterLowPrice.getText().toString();
                int bottomPrice = Integer.parseInt(lowestPrice);
                String highestPrice = filterHighPrice.getText().toString();
                int topPrice = Integer.parseInt(highestPrice);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONArray object = new JSONArray(response);
                            if(object != null)
                            {
                                listFiltered = gson.fromJson(object.toString(),new TypeToken<ArrayList<Product>>(){}.getType());
                                System.out.println(listFiltered);
                                Toast.makeText(getActivity(),"Filtered products",Toast.LENGTH_SHORT).show();
                                status = 1;
                            }
                            else{
                                Toast.makeText(getActivity(),"Sorry, products not found",Toast.LENGTH_SHORT).show();
                            }

                            getActivity().finish();
                            getActivity().overridePendingTransition(0,0);
                            getActivity().startActivity(getActivity().getIntent());

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(RequestFactory.getProduct(ProductsFragment.page, ProductsFragment.pageSize, account.id, productName, bottomPrice, topPrice, ProductCategory.valueOf(spinCat.getSelectedItem().toString()), checkUsed.isChecked(), listener,null));
            }
        });

        //menghapus data yang telah diisi
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                filterName.setText("");
                filterHighPrice.setText("");
                filterLowPrice.setText("");
                checkUsed.setChecked(false);
                checkNew.setChecked(false);
            }
        });

        return inflaterView;
    }
}