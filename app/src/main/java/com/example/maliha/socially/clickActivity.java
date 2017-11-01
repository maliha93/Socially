package com.example.maliha.socially;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class clickActivity extends AppCompatActivity{

    DialogMultipleChoice mDialogMultipleChoice;
    public static final String UPLOAD_URL = " https://nasa-maliha-93.c9users.io/socially_getfriends.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotspot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("connected")){
            TextView tv= (TextView)findViewById(R.id.connected);
            tv.setText("Connected with: " +getIntent().getStringExtra("connected"));
            Button b=(Button) findViewById(R.id.button);
            b.setText("Activated");
        }

    }
    public void invite(View v){

        upload();

    }
    private void upload(){
        class Upload extends AsyncTask<String,Void,String> {


            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(clickActivity.this, "Loading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                s=android.text.Html.fromHtml(s).toString();
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                if(!s.contains("No friends")){

                    mDialogMultipleChoice = new DialogMultipleChoice(getUserEmail(),clickActivity.this);
                    mDialogMultipleChoice.show(s);
                }
            }

            @Override
            protected String doInBackground(String... param) {

                HashMap<String,String> data = new HashMap<>();

                data.put("user1",getUserEmail());

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        Upload ui = new Upload();
        ui.execute("");
    }
    String getUserEmail(){
        return getIntent().getStringExtra("email");
    }


}
