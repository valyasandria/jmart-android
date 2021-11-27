package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    EditText editText = findViewById(R.id.editTextTextEmailAddress);
    EditText editText1 = findViewById(R.id.editTextTextPassword);
    Button button = findViewById(R.id.button);
    EditText editText2 = findViewById(R.id.editTextTextPersonName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button.setOnClickListener (o -> {
            String name = editText2.getText().toString();
            String email = editText.getText().toString();
            String pass = editText1.getText().toString();
            Response.ErrorListener respErrorList = resp->{
                Toast.makeText(RegisterActivity.this, "ERROR",Toast.LENGTH_SHORT).show();
            };

            Response.Listener<String> respList = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject!=null){
                        Toast.makeText(RegisterActivity.this, "Registration Successful.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Toast.makeText(RegisterActivity.this, "Registration Failed.",Toast.LENGTH_SHORT).show();
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(name, email, pass, respList, respErrorList);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        });
    }
}