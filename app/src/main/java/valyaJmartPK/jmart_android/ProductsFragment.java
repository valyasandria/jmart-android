package valyaJmartPK.jmart_android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.request.RequestFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment} factory method to
 * create an instance of this fragment.
 *
 * Show list products for sale and paginate page
 * @author Valya Sandria Akiela
 */

public class ProductsFragment extends Fragment {

    private static final Gson gson = new Gson();
    public static ArrayList<Product> listProduct = new ArrayList<>();
    static int pageSize = 20;
    static Integer page = 0;
    static Product selectedProduct = null;
    public static ArrayAdapter<Product> listViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View inflaterView = inflater.inflate(R.layout.fragment_products,container,false);

        Button nextButton = inflaterView.findViewById(R.id.buttonNext);
        Button prevButton = inflaterView.findViewById(R.id.buttonPrev);
        Button goButton = inflaterView.findViewById(R.id.buttonGo);
        EditText pageEdit = inflaterView.findViewById(R.id.editTextPage);

        ListView listView = inflaterView.findViewById(R.id.list_item);

        pageEdit.setText(String.valueOf(page+1), TextView.BufferType.EDITABLE);

        //menuju halaman yang diinginkan
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Go", Toast.LENGTH_SHORT).show();
                page = Integer.parseInt(pageEdit.getText().toString()) - 1;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        //kembali ke halaman sebelumnya
        prevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Previous page", Toast.LENGTH_SHORT).show();
                page = page - 1;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        //menuju ke halaman berikutnya
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Next page", Toast.LENGTH_SHORT).show();
                page = page + 1;
                getActivity().finish();
                getActivity().overridePendingTransition(0,0);
                getActivity().startActivity(getActivity().getIntent());
            }
        });

        //show list product & choose product
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONArray object = new JSONArray(response);
                    if(object != null)
                    {
                        listProduct = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>(){}.getType());
                        System.out.println(listProduct);
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, listProduct);
                        listView.setAdapter(listViewAdapter);

                        //one product clicked, go to product detail activity
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                selectedProduct = (Product) listView.getItemAtPosition(position);

                                Toast.makeText(getActivity(),"Product selected", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        //create request for page
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(RequestFactory.getPage("product", page, pageSize, listener,null));
        return inflaterView;

    }
}