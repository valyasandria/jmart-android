package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.model.ProductCategory;
import valyaJmartPK.jmart_android.request.ProductRequest;

/**
 * Add new product and its details to list view on the Main Activity
 * @author Valya Sandria Akiela
 */
public class CreateProductActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Product loggedProduct = null;

    public static Product getLoggedProduct(){
        return loggedProduct;
    }

    //covert byte shipment plans to string
    protected byte shipmentPlans(String SP){

        if(SP == "INSTANT")
        {
            return 1;
        }
        else if(SP == "SAME DAY")
        {
            return 2;
        }
        else if(SP == "NEXT DAY")
        {
            return 4;
        }
        else if(SP == "REGULER")
        {
            return 8;
        }
        else{ //KARGO
            return 16;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        EditText name = findViewById(R.id.editTextTextPersonName4);
        EditText weight = findViewById(R.id.editTextWeight);
        EditText price = findViewById(R.id.editTextPrice);
        EditText disc = findViewById(R.id.editTextDisc);

        Button createButton = findViewById(R.id.buttonCreate);
        Button cancelButton = findViewById(R.id.buttonCancel);

        RadioButton newButton = findViewById(R.id.radioButtonNew);
        RadioButton usedButton = findViewById(R.id.radioButtonUSed);

        Spinner spinProduct = findViewById(R.id.spinnerProd);
        Spinner spinShipment = findViewById(R.id.spinnerShip);

        //radio button
        //memastikan bahwa product condition hanya dipilih satu
        newButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean button)
            {
                if(button){
                    usedButton.setChecked(false);
                }
            }
        });

        usedButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean button) {
                if(button){
                    newButton.setChecked(false);
                }
            }
        });

        /**
         * user create new product and specify its name, weight, price, discount, condition, category, and shipment plans
         */
        createButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(CreateProductActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View view) {
                String nameProd = name.getText().toString();
                String weightProd = weight.getText().toString();
                int weight = Integer.parseInt(weightProd);
                String priceProd = price.getText().toString();
                double price = Double.parseDouble(priceProd);
                String discProd = disc.getText().toString();
                double disc = Double.parseDouble(discProd);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            loggedProduct = gson.fromJson(object.toString(), Product.class);

                            Toast.makeText(CreateProductActivity.this, "Product Created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(CreateProductActivity.this, "Failed to create product.",Toast.LENGTH_SHORT).show();
                        }
                        System.out.println(loggedProduct);
                    }
                };

                ProductRequest prodRequest = new ProductRequest(0, nameProd, weight, newButton.isChecked(), price, disc, ProductCategory.valueOf(spinProduct.getSelectedItem().toString()), shipmentPlans(spinShipment.getSelectedItem().toString()), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                queue.add(prodRequest);
            }
        });

        //user meng-cancel penambahan produk
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                weight.setText("");
                price.setText("");
                disc.setText("");
                usedButton.setChecked(false);
                newButton.setChecked(false);
                Intent backIntent = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });
    }

}