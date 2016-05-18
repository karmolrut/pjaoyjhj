package com.example.karmolrut.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class login extends Activity implements View.OnClickListener{
    String ip = "http://activityen.azurewebsites.net";
    int a = 0;
    String StrUsername, StrPassword;
    EditText username, password;
    Button btlogin;
    private Context mContext;
    OkHttpClient okHttpClient = new OkHttpClient();
    String State;

    //int timeout = 0; // make the activity visible for 0 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btlogin = (Button) findViewById(R.id.Login);

        btlogin.setOnClickListener(this);

    }

    public void onClick(View v) {

        StrUsername = username.getText().toString().trim();
        StrPassword = password.getText().toString().trim();

       // Intent it = new Intent(getApplicationContext(),
        //        login.class);

        Bundle b = new Bundle();
        b.putString("username", StrUsername);
        b.putString("password", StrPassword);
        //it.putExtras(b);

       // startActivity(it);
        AsyncTaskGetData asyncTaskGetData = new AsyncTaskGetData();
        asyncTaskGetData.execute("1", StrUsername, StrPassword);
    }

    public class AsyncTaskGetData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {

            if(a == 2){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(login.this);

                alertDialog.setTitle("ERROR...");
                alertDialog.setMessage("ใส่ USERNAMEหรือPASSWORD ผิดกรุณากดใช่เพื่อกรอกข้อมูลใหม่");
                alertDialog.setIcon(R.drawable.bgblood);

                alertDialog.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //คลิกใช่ ออกจากโปรแกรม
                                Intent homepage = new Intent(login.this, login.class);
                                startActivity(homepage);

                            }
                        });

                alertDialog.show();

            }




        }

        @Override
        protected Void doInBackground(String... params) {

            // Initialize Builder (not RequestBody)
            FormBody.Builder builder = new FormBody.Builder();
            // Add Params to Builder
            builder.add("sID", params[0]);
            builder.add("sUsername", params[1]);
            builder.add("sPassword", params[2]);
            RequestBody body = builder.build();



            Request request = new Request.Builder().url(ip+"/android.php").post(body).build();
            Response response;

            try {
                response = okHttpClient.newCall(request).execute();
                String result = response.body().string();

                JSONObject jsonObject;

                try {

                    jsonObject = new JSONObject(result);

                    // Query JSON tag: State
                    State = jsonObject.getString("State");
                    System.out.println(State);


                    if (State.equals("OK")) {
                        System.out.println("รหัสผ่านและชื่อผู้ใช้งานถูกต้อง");

                        Intent homepage = new Intent(login.this, menu.class);
                        startActivity(homepage);
                        System.exit(0);
                          a=0;
                    } else if(State.equals(("Denied"))) {
                           a = 2;
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
            return null;
        }





    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(login.this);

        alertDialog.setTitle("ออกจากโปรแกรม...");
        alertDialog.setMessage("คุณต้องการออกจากโปรแกรมใช่หรือไม่ ?");
        alertDialog.setIcon(R.drawable.bgblood);

        alertDialog.setPositiveButton("ใช่",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //คลิกใช่ ออกจากโปรแกรม

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);

                    }
                });

        alertDialog.setNegativeButton("ไม่ใช่",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        //คลิกไม่ cancel dialog
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }
    }


