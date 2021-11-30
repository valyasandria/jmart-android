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

import org.json.JSONException;
import org.json.JSONObject;

import valyaJmartPK.jmart_android.request.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editText = findViewById(R.id.editTextTextEmailAddress);
        EditText editText1 = findViewById(R.id.editTextTextPassword);
        Button button = findViewById(R.id.button);
        EditText editText2 = findViewById(R.id.editTextTextPersonName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText2.getText().toString();
                String email = editText.getText().toString();
                String pass = editText1.getText().toString();

                Response.ErrorListener errorListener = resp -> Toast.makeText(RegisterActivity.this, "System ERROR",Toast.LENGTH_SHORT).show();

                Response.Listener<String> listener = response -> {
                    try
                    {
                        JSONObject object = new JSONObject(response);

                        if(object != null)
                        {
                            Toast.makeText(RegisterActivity.this, "Registration Successful.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    catch (JSONException e)
                    {
                        Toast.makeText(RegisterActivity.this, "Registration Failed.",Toast.LENGTH_SHORT).show();
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, email, pass, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
}