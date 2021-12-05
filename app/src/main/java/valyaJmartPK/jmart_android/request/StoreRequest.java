package valyaJmartPK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StoreRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String, String> params;

    public StoreRequest(String name, String address, String phoneNumber, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phone number", phoneNumber);
    }

    @Nullable
    @Override
    public Map<String, String> getParams(){
        return params;
    }

}
