package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
 * Show invoice history from user transaction
 * @author Valya Sandria Akiela
 */
public class InvoiceHistoryActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static ArrayList<Payment> paymentArrayList = new ArrayList<>();
    public static ArrayList<Product> listProducts = new ArrayList<>();
    static int pageSize = 20;
    static Integer page = 0;

    Account account = LoginActivity.getLoggedAccount();

    //menambahkan produk yang dibeli ke invoice
    private void convertPayment() {
        for (Payment data : paymentArrayList) {
            if(data.buyerId == LoginActivity.getLoggedAccount().id){
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject object = new JSONObject(response);
                            Product productsTemp = gson.fromJson(object.toString(),Product.class);
                            listProducts.add(productsTemp);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(InvoiceHistoryActivity.this);
                requestQueue.add(RequestFactory.getById("product", data.productId, listener, null));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);

        EditText paymentID = findViewById(R.id.editTextPymID);
        Button cancelButton = findViewById(R.id.buttonCancelPym);
        ListView listInvoice = findViewById(R.id.list_invoice);

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
                        ArrayAdapter<Product> listViewAdapter = new ArrayAdapter<Product>(InvoiceHistoryActivity.this, android.R.layout.simple_list_item_1, listProducts);

                        listInvoice.setAdapter(listViewAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(InvoiceHistoryActivity.this);
        requestQueue.add(RequestFactory.getPage("payment", page, pageSize, listener, null));

        //cancel payment
        cancelButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(InvoiceHistoryActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View v) {

                int cancelId = -1;
                int id;
                String payId = paymentID.getText().toString();
                id = Integer.parseInt(payId);

                for (int i = 0; i < paymentArrayList.size(); i++) {
                    if (paymentArrayList.get(id).id == id) {
                        cancelId = id;
                    }
                    else{
                        cancelId = -1;
                    }
                }

                Response.Listener<String> listener = response -> {
                    boolean resp = Boolean.parseBoolean(response);
                    if (resp)
                    {
                        Toast.makeText(InvoiceHistoryActivity.this, "Payment cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InvoiceHistoryActivity.this, "Payment failed to cancel", Toast.LENGTH_SHORT).show();
                    }
                };

                RequestQueue cancelRequest = Volley.newRequestQueue(InvoiceHistoryActivity.this);
                cancelRequest.add(PaymentRequest.cancelPayment(cancelId, listener, errorListener));

            }
        });
    }
}