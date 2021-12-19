package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import valyaJmartPK.jmart_android.model.Store;
import valyaJmartPK.jmart_android.request.LoginRequest;
import valyaJmartPK.jmart_android.request.StoreRequest;
import valyaJmartPK.jmart_android.request.TopUpRequest;

/**
 * Show user account detail, top up, and register store
 * @author Valya Sandria Akiela
 */
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

        Button invoiceAccount = findViewById(R.id.buttonAccInv);
        Button invoiceStore = findViewById(R.id.buttonStoreInv);

        Button regisStoreButton = findViewById(R.id.button6);
        CardView regisStoreCard = findViewById(R.id.card_view);
        CardView storeCard = findViewById(R.id.card_view2);

        //field account details
        TextView textName = findViewById(R.id.textView12);
        TextView textEmail = findViewById(R.id.textView13);
        TextView textBalance = findViewById(R.id.textView14);

        Button cancelButton = findViewById(R.id.button8);
        Button regisButton = findViewById(R.id.button7);
        Button topUpButton = findViewById(R.id.button5);
        Button logOutButton = findViewById(R.id.button9);
        //input register store
        EditText nameText = findViewById(R.id.editTextTextPersonName5);
        EditText addText = findViewById(R.id.editTextTextPostalAddress);
        EditText phoneText = findViewById(R.id.editTextPhone);
        EditText topUpText = findViewById(R.id.editTextTopUp);

        //show registered store data
        TextView myStore = findViewById(R.id.textViewMyName);
        TextView myStoreAdd = findViewById(R.id.textViewMyAdd);
        TextView myStorePhone = findViewById(R.id.textViewMyPhone);

        //hide card view before button register store
        regisStoreCard.setVisibility(View.GONE);
        storeCard.setVisibility(View.GONE);
        regisStoreButton.setVisibility(View.GONE);

        //show account details
        Account myAcc = LoginActivity.getLoggedAccount();
        textName.setText(myAcc.name);
        textEmail.setText(myAcc.email);
        textBalance.setText("Rp"+String.valueOf(myAcc.balance));


        /**
         * top up balance and parse string top up amount to double
         */
        topUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //input balance from user
                String topUpAmount = topUpText.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Boolean.parseBoolean(response)) {
                            Toast.makeText(AboutMeActivity.this, "Top Up success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AboutMeActivity.this, "Top Up failed", Toast.LENGTH_SHORT).show();
                        }
                        //new balance after top up
                        myAcc.balance = myAcc.balance + Double.parseDouble(topUpAmount);
                        finish();
                        startActivity(getIntent());
                    }
                };
                //create request to top up balance
                TopUpRequest topUpRequest = new TopUpRequest(LoginActivity.getLoggedAccount().id, Double.parseDouble(topUpAmount), listener, null);
                RequestQueue requestQueue = Volley.newRequestQueue(AboutMeActivity.this);
                requestQueue.add(topUpRequest);

            }
        });

        /**
         * hide register store button kalau account tersebut sudah punya store, kemudian menampilkan data store yang terdaftar
         */
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


            /**
             * user mendaftarkan store baru
             */
            regisButton.setOnClickListener(new View.OnClickListener() {

                Response.ErrorListener errorListener = resp -> Toast.makeText(AboutMeActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

                @Override
                public void onClick(View v) {
                    String storeName = nameText.getText().toString();
                    String storeAdd = addText.getText().toString();
                    String storePhone = phoneText.getText().toString();
                    int myId = myAcc.id;

                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject object = new JSONObject(response);
                                if (object != null)
                                {
                                    Toast.makeText(AboutMeActivity.this, "Register Store Success", Toast.LENGTH_SHORT).show();
                                    myAcc.store = gson.fromJson(object.toString(), Store.class);

                                    //go to next card view (show store details)
                                    storeCard.setVisibility(View.VISIBLE);
                                    myStore.setText(myAcc.store.name);
                                    myStoreAdd.setText(myAcc.store.address);
                                    myStorePhone.setText(myAcc.store.phoneNumber);
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(AboutMeActivity.this, "Registration Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    //create request to register store
                    StoreRequest storeRequest = new StoreRequest(myId, storeName, storeAdd, storePhone, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                    queue.add(storeRequest);

                }
            });
        }
        //jika sudah punya store, card view menampilkan detail store
        else {
            storeCard.setVisibility(View.VISIBLE);

            //go to next card view
            myStore.setText(myAcc.store.name);
            myStoreAdd.setText(myAcc.store.address);
            myStorePhone.setText(myAcc.store.phoneNumber);
        }

        //button account invoice
        invoiceAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutMeActivity.this, "Account Invoice History",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AboutMeActivity.this, InvoiceHistoryActivity.class);
                startActivity(intent);
            }
        });

        //button store invoice
        invoiceStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutMeActivity.this, "Store Invoice History",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AboutMeActivity.this, InvoiceHistoryStoreActivity.class);
                startActivity(intent);
            }
        });

        //logout button
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutMeActivity.this, "LOG OUT",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AboutMeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}