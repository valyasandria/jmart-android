package valyaJmartPK.jmart_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.request.RequestFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment} factory method to
 * create an instance of this fragment.
 */

public class ProductsFragment extends Fragment {

    private static final Gson gson = new Gson();
    public static ArrayList<Product> productsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final int pageSize = 10;
        int page = 0;
        View productView = inflater.inflate(R.layout.fragment_products,container,false);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if(object != null)
                    {
                        productsList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Product>>(){}.getType());
                        System.out.println(productsList);
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(getActivity(), android.R.layout.simple_list_item_1, productsList);
                        ListView listProd = (ListView) productView.findViewById(R.id.list_item);

                        listProd.setAdapter(listViewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(RequestFactory.getPage("product",page,pageSize,listener,null));
        return productView;
    }
}