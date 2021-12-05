package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.request.ProductRequest;

public class CreateProductActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Product loggedProduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        EditText name = findViewById(R.id.editTextTextPersonName4);
        EditText weight = findViewById(R.id.editTextWeight);
        EditText price = findViewById(R.id.editTextPrice);
        EditText disc = findViewById(R.id.editTextDisc);

        Button createButton = findViewById(R.id.buttonCreate);
        Button cancelButton = findViewById(R.id.buttonCreate);

        createButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(CreateProductActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View view) {
                String nameProd = name.getText().toString();
                String weightProd = weight.getText().toString();
                int finalWeight = Integer.parseInt(weightProd);
                String priceProd = price.getText().toString();
                double finalPrice = Double.parseDouble(priceProd);
                String discProd = disc.getText().toString();
                Double finalDisc = Double.parseDouble(discProd);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            if (object != null)
                            {

                                Toast.makeText(CreateProductActivity.this, "Product Created.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                                loggedProduct = gson.fromJson(object.toString(), Product.class);
                                startActivity(intent);
                            }
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(CreateProductActivity.this, "Nothing to Created.",Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                ProductRequest prodRequest = new ProductRequest(nameProd, finalWeight, finalPrice, finalDisc, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                queue.add(prodRequest);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }
}