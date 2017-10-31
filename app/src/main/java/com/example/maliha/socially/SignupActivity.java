package com.example.maliha.socially;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    public static final String UPLOAD_URL = " https://nasa-maliha-93.c9users.io/socially_signup.php";
    public static final String UPLOAD_KEY = "image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void signUp(View v)
    {
        upload();
    }
    private void upload(){
        class Upload extends AsyncTask<String,Void,String>{


            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignupActivity.this, "Signing Up...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                s=android.text.Html.fromHtml(s).toString();
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                if(s.contains("Success")){
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.putExtra("email",getUserEmail());
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(String... param) {

                HashMap<String,String> data = new HashMap<>();


                data.put("usrname",getUserName());
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
    public String getUserName(){
        EditText editText = (EditText)findViewById(R.id.name);
        return  editText.getText().toString();
    }
}