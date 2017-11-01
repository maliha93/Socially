package com.example.maliha.socially;

/**
 * Created by Maliha on 11/1/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PhanVanLinh on 14/09/2017.
 * phanvanlinh.94vn@gmail.com
 */

public class DialogMultipleChoice {
    Context mContext;
    String email="";
    public static final String UPLOAD_URL = " https://nasa-maliha-93.c9users.io/socially_pending.php";
    public DialogMultipleChoice(String s,Context context) {
        mContext = context;
        email=s;
    }

    List<Item> itemList = new ArrayList<>();

    public void show(String s) {

        String emails[]=s.split(" ");

        for(String e: emails){
            itemList.add(new Item(e, R.mipmap.ic_launcher));
        }
       /* if (itemList.isEmpty()) {
            itemList.add(new Item("A", R.mipmap.ic_launcher));
            itemList.add(new Item("B", R.mipmap.ic_launcher));
            itemList.add(new Item("C", R.mipmap.ic_launcher));
        }*/

        final DialogMultipleChoiceAdapter adapter =
                new DialogMultipleChoiceAdapter(mContext, itemList);

        new AlertDialog.Builder(mContext).setTitle("Select Friends")
                .setAdapter(adapter, null)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        upload(adapter.getCheckedItem());
                        /*Toast.makeText(mContext,
                                "getCheckedItem = " + adapter.getCheckedItem().size(),
                                Toast.LENGTH_SHORT).show();*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    private void upload(String s){
        class Upload extends AsyncTask<String,Void,String> {


            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(mContext, "Sending...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                s=android.text.Html.fromHtml(s).toString();
                loading.dismiss();
                Toast.makeText(mContext,s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... param) {

                HashMap<String,String> data = new HashMap<>();
                data.put("user1",getUserEmail());
                data.put("user2",param[0]);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        Upload ui = new Upload();
        ui.execute(s);
    }

    String getUserEmail(){
        return email;
    }
}