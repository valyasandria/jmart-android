package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Payment;
import valyaJmartPK.jmart_android.model.Product;
import valyaJmartPK.jmart_android.request.PaymentRequest;

/**
 * Pay selected product
 * @author Valya Sandria Akiela
 */
public class PaymentActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Payment loggedPayment = null;
    private int count = 1;
    Account account = LoginActivity.getLoggedAccount();
    Product product = ProductsFragment.selectedProduct;

    private double discounted = (product.price)*((100.0-product.discount)/100.0);

    public static Payment getLoggedPayment(){
        return loggedPayment;
    }

    //convert shipment plans to string
    protected String shipmentPlans(byte SP){

        if(SP == 1)
        {
            return "INSTANT";
        }
        else if(SP == 2)
        {
            return "SAME DAY";
        }
        else if(SP == 4)
        {
            return "NEXT DAY";
        }
        else if(SP == 8)
        {
            return "REGULER";
        }
        else{ //BYTE 16
            return "KARGO";
        }
    }

    //convert shipment plans to byte
    protected byte shipmentPlansByte(String SP){

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

    //convert radio button condition
    private String conditionUsed(boolean checkedCondition){
        if (checkedCondition)
        {
            return "NEW"; //if new button clicked
        }
        else
        {
            return "USED"; //used button clicked
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView name = findViewById(R.id.textViewProductName);
        TextView weight = findViewById(R.id.textViewWeight);
        TextView condition = findViewById(R.id.textViewCond);
        TextView price = findViewById(R.id.textViewPrice);
        TextView discount = findViewById(R.id.textViewDiscount);
        TextView category = findViewById(R.id.textViewCategory);
        TextView shipment = findViewById(R.id.textViewShipments);
        TextView total = findViewById(R.id.textViewTotal);
        TextView shipCost = findViewById(R.id.textViewCost);

        EditText quantity = findViewById(R.id.editTextQuantity);
        EditText address = findViewById(R.id.editTextBuyerAddress);

        Button payButton = findViewById(R.id.buttonPay);

        //show detail product in text
        name.setText(ProductsFragment.selectedProduct.name);
        weight.setText(String.valueOf(ProductsFragment.selectedProduct.weight));
        condition.setText(conditionUsed(ProductsFragment.selectedProduct.conditionUsed));
        price.setText(String.valueOf("Rp" + ProductsFragment.selectedProduct.price));
        discount.setText(String.valueOf(ProductsFragment.selectedProduct.discount + "%"));
        category.setText(String.valueOf(ProductsFragment.selectedProduct.category));
        shipment.setText(shipmentPlans(ProductsFragment.selectedProduct.shipmentPlans));
        shipCost.setText("Rp9000.0");

        /**
         * user pay selected product with product quantity and discount
         * fill the shipment address
         */
        payButton.setOnClickListener(new View.OnClickListener() {

            Response.ErrorListener errorListener = resp -> Toast.makeText(PaymentActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

            @Override
            public void onClick(View v) {
                String buyerAdd = address.getText().toString();
                String countProduct = quantity.getText().toString();
                count = Integer.parseInt(countProduct);

                total.setText("Rp" + (9000.0 + ((product.price * count) - (discounted * count))));

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            Toast.makeText(PaymentActivity.this, "Payment success!",Toast.LENGTH_SHORT).show();
                            loggedPayment = gson.fromJson(object.toString(), Payment.class);
                            LoginActivity.getLoggedAccount().balance -= (9000.0 + ((product.price * count) - (discounted * count)));
                            Intent back = new Intent(PaymentActivity.this, MainActivity.class);
                            startActivity(back);
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(PaymentActivity.this, "Payment failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                //create payment request
                PaymentRequest paymentRequest = new PaymentRequest(account.id, product.id, count, buyerAdd, shipmentPlansByte(shipmentPlans(product.shipmentPlans)),listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
                queue.add(paymentRequest);
            }
        });
    }
}