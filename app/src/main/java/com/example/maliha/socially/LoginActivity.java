package com.example.maliha.socially;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    public static final String UPLOAD_URL = " https://nasa-maliha-93.c9users.io/socially_login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void signUp(View v)
    {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
    public void login(View v)
    {
        upload();
    }
    private void upload(){
        class Upload extends AsyncTask<String,Void,String> {


            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Signing in...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                s=android.text.Html.fromHtml(s).toString();
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                if(s.contains("Success")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("email",getUserEmail());
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(String... param) {

                HashMap<String,String> data = new HashMap<>();

                data.put("email",getUserEmail());
                data.put("password",getPassword());


                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        Upload ui = new Upload();
        ui.execute("");
    }

    public String getPassword(){
        EditText editText = (EditText)findViewById(R.id.password);
        return  editText.getText().toString();
    }
    public String getUserEmail(){
        EditText editText = (EditText)findViewById(R.id.email);
        return  editText.getText().toString();
    }

}
