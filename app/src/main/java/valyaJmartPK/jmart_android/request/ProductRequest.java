package valyaJmartPK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProductRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String, String> params;

    public ProductRequest(String name, int weight, double price, double discount, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
    }

    @Nullable
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
