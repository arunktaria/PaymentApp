package com.example.paymentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText amount, username, note, upiId;
    Button btnsubmit;
    String googlePackageName = "com.google.android.apps.nbu.paisa.user";
    BroadcastReceiver receiver;
    MyReceiver myReceiver;
    Pattern ptrn=Pattern.compile("^(.+)@(.+)$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        amount = findViewById(R.id.amount);
        username = findViewById(R.id.username);
        note = findViewById(R.id.paymentnote);
        upiId = findViewById(R.id.upiId);
        btnsubmit = findViewById(R.id.btnsubmit);
        myReceiver = new MyReceiver();
        receiver = new MyReceiver();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().equals("") && username.getText().toString().equals("") && note.getText().toString().equals("") && upiId.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (upiId.getText().toString().equals(""))
                    upiId.setError("Required!");
                else if (!ptrn.matcher(upiId.getText().toString()).matches())
                    {
                        upiId.setError("wrong format");
                    }

                else if (amount.getText().toString().equals(""))
                    amount.setError("Required!");
                else if (username.getText().toString().equals(""))
                    username.setError("Required!");
                else if (note.getText().toString().equals(""))
                    note.setError("Required!");

                else {
                    payment();
                }


            }
        });


    }


    public void payment() {
        Uri uri = new Uri.Builder()
                .authority("pay")
                .scheme("upi")
                .appendQueryParameter("am", amount.getText().toString())
                .appendQueryParameter("pa", upiId.getText().toString())
                .appendQueryParameter("tn", note.getText().toString())
                .appendQueryParameter("pn", username.getText().toString())
                .build();

        Intent intent = new Intent();
        intent.setPackage(googlePackageName);
        intent.setData(uri);
        startActivityForResult(intent, 100);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data!=null) {


            if (requestCode == 100) {


            }
        }
        else
        {
            Toast.makeText(MainActivity.this, "Google pay not installed", Toast.LENGTH_SHORT).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}