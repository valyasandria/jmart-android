package valyaJmartPK.jmart_android;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import valyaJmartPK.jmart_android.model.Account;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hello user!
        TextView textView = findViewById(R.id.textView);

        //hello [name]!
        Account acc = LoginActivity.getLoggedAccount();
        textView.setText("Hello " + acc.name + "!");




    }
}