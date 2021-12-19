package valyaJmartPK.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * create request to make new payment for selected product
 * @author Valya Sandria Akiela
 */
public class PaymentRequest extends StringRequest {

    private static final String URL = "http://10.0.2.2:8080/payment/create";
    private static final String URL_ACCEPT = "http://10.0.2.2:8080/payment/%d/accept";
    private static final String URL_CANCEL = "http://10.0.2.2:8080/payment/%d/cancel";
    private static final String URL_SUBMIT = "http://10.0.2.2:8080/payment/%d/submit";

    private final Map<String, String> params;

    public PaymentRequest(int buyerId, int productId, int productCount, String shipmentAddress, byte shipmentPlan, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Request.Method.POST,  URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(buyerId));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", String.valueOf(productCount));
        params.put("shipmentAddress", shipmentAddress);
        params.put("shipmentPlan", String.valueOf(shipmentPlan));
    }

    @Nullable
    @Override
    public Map<String, String> getParams()
    {
        return params;
    }

    public static StringRequest acceptPayment(int id, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        return new StringRequest(Method.POST, String.format(URL_ACCEPT, id), listener, errorListener);
    }

    public static StringRequest cancelPayment(int id, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        return new StringRequest(Method.POST, String.format(URL_CANCEL, id), listener, errorListener);
    }

    public static StringRequest submitPayment(int id, String receipt, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        return new StringRequest(Method.POST, String.format(URL_SUBMIT, id, receipt), listener, errorListener);
    }
}
