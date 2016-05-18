package com.example.karmolrut.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class menu extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        final Button btactivity = (Button) findViewById(R.id.btactivity);
        // Perform action on click
        btactivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), groupActivity.class);
                startActivity(it);
            }
        });

        final Button btlogout = (Button) findViewById(R.id.logout);
        btlogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(menu.this);

                alertDialog.setTitle("ออกจากระบบ...");
                alertDialog.setMessage("คุณต้องการออกจากระบบหรือไม่ ?");
                alertDialog.setIcon(R.drawable.bgblood);

                alertDialog.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //คลิกใช่ ออกจากโปรแกรม
                                Intent it = new Intent(getApplicationContext(), login.class);
                                startActivity(it);
                                finish();

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
        });
    }
    public void onBackPressed() {


    }

}
