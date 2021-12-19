package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Show product detail if clicked
 * @author Valya Sandria Akiela
 */
public class ProductDetailActivity extends AppCompatActivity {

    //convert spinner shipment plans
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

    //convert radio button condition
    private String conditionUsed(boolean checkedCondition){
        if (checkedCondition)
        {
            return "NEW";
        }
        else
        {
            return "USED";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView name = findViewById(R.id.textViewProductName);
        TextView weight = findViewById(R.id.textViewWeight);
        TextView condition = findViewById(R.id.textViewCond);
        TextView price = findViewById(R.id.textViewPrice);
        TextView discount = findViewById(R.id.textViewDiscount);
        TextView category = findViewById(R.id.textViewCategory);
        TextView shipment = findViewById(R.id.textViewShipments);

        Button buyButton = findViewById(R.id.buttonBuy);

        //show detail product in text
        name.setText(ProductsFragment.selectedProduct.name);
        weight.setText(String.valueOf(ProductsFragment.selectedProduct.weight));
        condition.setText(conditionUsed(ProductsFragment.selectedProduct.conditionUsed));
        price.setText(String.valueOf("Rp" + ProductsFragment.selectedProduct.price));
        discount.setText(String.valueOf(ProductsFragment.selectedProduct.discount + "%"));
        category.setText(String.valueOf(ProductsFragment.selectedProduct.category));
        shipment.setText(shipmentPlans(ProductsFragment.selectedProduct.shipmentPlans));

        /**
         * user proceed to check out
         */
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}