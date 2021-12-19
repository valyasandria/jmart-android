package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Payment;
import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.request.PaymentRequest;
import valyaJmartPK.jmart_android.request.RequestFactory;

/**
 * Show store invoice history
 * @author Valya Sandria Akiela
 */
public class InvoiceHistoryStoreActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static ArrayList<Payment> paymentArrayList = new ArrayList<>();
    public static ArrayList<Product> listProducts = new ArrayList<>();
    static int pageSize = 20;
    static Integer page = 0;

    Account account = LoginActivity.getLoggedAccount();

    private void convertPayment() {
        int i = 0;

        for (Payment data : paymentArrayList) {
            if(data.buyerId == LoginActivity.getLoggedAccount().id){
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Product productsTemp = gson.fromJson(object.toString(),Product.class);
                            listProducts.add(productsTemp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(InvoiceHistoryStoreActivity.this);
                requestQueue.add(RequestFactory.getById("product", data.productId, listener, null));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history_store);

        ListView listInvoice = findViewById(R.id.list_invoice);
        EditText paymentID = findViewById(R.id.editTextPymID);
        EditText receipt = findViewById(R.id.editTextReceipt);
        Button cancelButton = findViewById(R.id.buttonCancelPym);
        Button accButton = findViewById(R.id.buttonAccPym);
        Button submitButton = findViewById(R.id.buttonSubmitPym);

        //show invoice history
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    if (object != null) {
                        paymentArrayList = gson.fromJson(object.toString(), new TypeToken<ArrayList<Payment>>() {
                        }.getType());
                        System.out.println(paymentArrayList);
                        convertPayment();
                        System.out.println(listProducts);
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(InvoiceHistoryStoreActivity.this, android.R.layout.simple_list_item_1, listProducts);

                        listInvoice.setAdapter(listViewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(InvoiceHistoryStoreActivity.this);
        requestQueue.add(RequestFactory.getPage("payment", page, pageSize, listener, null));

        /*
        //cancel payment
        cancelButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(InvoiceHistoryStoreActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {
                    boolean resp = Boolean.parseBoolean(response);
                    if (resp)
                    {
                        Toast.makeText(InvoiceHistoryStoreActivity.this, "Payment cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InvoiceHistoryStoreActivity.this, "Payment cancelled failed", Toast.LENGTH_SHORT).show();
                    }
                };

                RequestQueue cancelRequest = Volley.newRequestQueue(InvoiceHistoryStoreActivity.this);
                cancelRequest.add(PaymentRequest.cancelPayment(id, listener, errorListener));

            }
        });

        //submit payment
        submitButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(InvoiceHistoryStoreActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {
                    boolean resp = Boolean.parseBoolean(response);
                    if (resp)
                    {
                        Toast.makeText(InvoiceHistoryStoreActivity.this, "Payment submitted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InvoiceHistoryStoreActivity.this, "Payment can't submit", Toast.LENGTH_SHORT).show();
                    }
                };

                RequestQueue cancelRequest = Volley.newRequestQueue(InvoiceHistoryStoreActivity.this);
                cancelRequest.add(PaymentRequest.submitPayment(id, submitReceipt, listener, errorListener));
            }
        });

        //accept payment
        accButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(InvoiceHistoryStoreActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = response -> {
                    boolean resp = Boolean.parseBoolean(response);
                    if (resp)
                    {
                        Toast.makeText(InvoiceHistoryStoreActivity.this, "Payment accepted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InvoiceHistoryStoreActivity.this, "Payment failed to accept", Toast.LENGTH_SHORT).show();
                    }
                };

                RequestQueue cancelRequest = Volley.newRequestQueue(InvoiceHistoryStoreActivity.this);
                cancelRequest.add(PaymentRequest.acceptPayment(id, listener, errorListener));

            }
        });*/
    }
}