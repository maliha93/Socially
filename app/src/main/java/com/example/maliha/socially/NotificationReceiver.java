package com.example.maliha.socially;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


/**
 * Created by Maliha on 11/1/2017.
 */

public class NotificationReceiver {

    Context context;
    String email="";
    public static final String UPLOAD_URL = " https://nasa-maliha-93.c9users.io/socially_getRequest.php";
    NotificationReceiver(Context c,String em){
        context=c;
        email=em;
        upload();
    }

    private void upload(){
        class Upload extends AsyncTask<String,Void,String> {


            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(FriendsActivity.this, "Adding friend...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                s=android.text.Html.fromHtml(s).toString();
                final String ss=s;
               // loading.dismiss();
                //Toast.makeText(context,s, Toast.LENGTH_LONG).show();
                if(!s.contains("nothing") && s.length()>1){
                    //Toast.makeText(context,s, Toast.LENGTH_LONG).show();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context.getApplicationContext());
                    alertDialogBuilder.setMessage(s+" wants to connect with you!");
                    alertDialogBuilder.setPositiveButton("Accept",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent intent = new Intent(context, clickActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                                    intent.putExtra("connected",ss);
                                    context.startActivity(intent);
                                }
                            });

                    alertDialogBuilder.setNegativeButton("Ignore",new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    Window window = alertDialog.getWindow();
                    if (window != null)
                    {
                        // the important stuff..
                        window.setType(WindowManager.LayoutParams.TYPE_TOAST);
                        alertDialog.show();
                    }
                    //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    //alertDialog.show();
                }

            }

            @Override
            protected String doInBackground(String... param) {

                HashMap<String,String> data = new HashMap<>();
                data.put("user1",getEmail());
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        Upload ui = new Upload();
        ui.execute("");
    }

    String getEmail(){
        return email;
    }

}
