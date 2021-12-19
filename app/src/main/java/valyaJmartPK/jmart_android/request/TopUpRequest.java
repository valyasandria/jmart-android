package valyaJmartPK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * create request to add balance
 * @author Valya Sandria Akiela
 */
public class TopUpRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/%d/topUp";
    private final Map<String, String> params;

    public TopUpRequest(int id, double balance, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Request.Method.POST,  String.format(URL, id, balance), listener, errorListener);
        params = new HashMap<>();
        params.put("balance", String.valueOf(balance));
    }

    @Nullable
    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}
