package valyaJmartPK.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Map;

import valyaJmartPK.jmart_android.model.Account;
import valyaJmartPK.jmart_android.request.LoginRequest;

public class LoginActivity extends AppCompatActivity {

    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText editText = findViewById(R.id.editTextTextEmailAddress2);
        EditText editText1 = findViewById(R.id.editTextTextPassword2);
        Button button = findViewById(R.id.button2);
        TextView textView = findViewById(R.id.textView2);
        TextView textView1 = findViewById(R.id.textView3);

        setContentView(R.layout.activity_login);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}