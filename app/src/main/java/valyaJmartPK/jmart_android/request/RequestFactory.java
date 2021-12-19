package valyaJmartPK.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import valyaJmartPK.jmart_android.model.ProductCategory;


/**
 * create request for pagination page on list view products & invoice history
 * @author Valya Sandria Akiela
 */
public class RequestFactory {

    private static final String URL_FORMAT_ID = "http://10.0.2.2:8080/%s/%d";
    private static final String URL_FORMAT_PAGE = "http://10.0.2.2:8080/%s/page?page=%s&pageSize=%s";
    private static final String URL_FORMAT_PRODUCT = "http://10.0.2.2:8080/product/getProductFiltered?page=%d&pageSize=%d&accountId=%d&search=%s&minPrice=%s&maxPrice=%s&category=%s&conditionUsed=%s";

    public static StringRequest getById
            (
                    String parentURI,
                    int id,
                    Response.Listener<String> listener,
                    Response.ErrorListener errorListener
            )
    {
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
    public static StringRequest getPage
            (
                    String parentURI,
                    int page,
                    int pageSize,
                    Response.Listener<String> listener,
                    Response.ErrorListener errorListener
            )
    {
        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    public static StringRequest getProduct(
            int page,
            int pageSize,
            int accountId,
            String search,
            int minPrice,
            int maxPrice,
            ProductCategory category,
            boolean conditionUsed,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    )
    {
        String url = String.format(URL_FORMAT_PRODUCT,page,pageSize, accountId, search, minPrice, maxPrice, category, conditionUsed);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);

    }

}
