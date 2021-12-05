package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.model.Store;



public class AboutMeActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Store loggedStore = null;

    public static Store getLoggedStore(){
        return loggedStore;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        Button regisStoreButton = findViewById(R.id.button6);
        CardView regisStoreCard = findViewById(R.id.card_view);
        CardView storeCard = findViewById(R.id.card_view2);

        //logged account
        TextView textName = findViewById(R.id.textView12);
        TextView textEmail = findViewById(R.id.textView13);
        TextView textBalance = findViewById(R.id.textView14);

        Button cancelButton = findViewById(R.id.button8);
        Button regisButton = findViewById(R.id.button7);

        //input register store
        EditText nameText = findViewById(R.id.editTextTextPersonName5);
        EditText addText = findViewById(R.id.editTextTextPostalAddress);
        EditText phoneText = findViewById(R.id.editTextPhone);

        //show registered store
        TextView myStore = findViewById(R.id.textViewMyName);
        TextView myStoreAdd = findViewById(R.id.textViewMyAdd);
        TextView myStorePhone = findViewById(R.id.textViewMyPhone);

        //hide card view before button register store
        regisStoreCard.setVisibility(View.GONE);
        storeCard.setVisibility(View.GONE);
        regisStoreButton.setVisibility(View.GONE);

        //account details
        Account myAcc = LoginActivity.getLoggedAccount();

        textName.setText(myAcc.name);
        textEmail.setText(myAcc.email);
        textBalance.setText("Rp" + myAcc.balance);

        //hide register store button if sudah punya store
        if (myAcc.store == null)
        {
            regisStoreButton.setVisibility(View.VISIBLE);
            regisStoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regisStoreCard.setVisibility(View.VISIBLE);
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regisStoreCard.setVisibility(View.GONE);
                }
            });

            //input register store
            regisButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String storeName = nameText.getText().toString();
                    String storeAdd = addText.getText().toString();
                    String storePhone = phoneText.getText().toString();

                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject object = new JSONObject(response);
                                if (object != null)
                                {
                                    Toast.makeText(AboutMeActivity.this, "Register Store Success.", Toast.LENGTH_SHORT).show();
                                    loggedStore = gson.fromJson(object.toString(), Store.class);
                                    storeCard.setVisibility(View.VISIBLE);

                                    Store store = AboutMeActivity.getLoggedStore();
                                    myStore.setText(store.name);
                                    myStoreAdd.setText(store.address);
                                    myStorePhone.setText(store.phoneNumber);
                                }
                            }
                            catch (JSONException e)
                            {
                                Toast.makeText(AboutMeActivity.this, "Registration Failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    
                }
            });
        }

    }
}