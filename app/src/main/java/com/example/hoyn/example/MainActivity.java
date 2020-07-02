package com.example.hoyn.example;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiprocessSharedPreferences.setAuthority("com.example.xposedmodule.provider");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imei = getIMEI(MainActivity.this);
                showToast("点击了，Imei=" + imei+getText());
                SharedPreferences test = MultiprocessSharedPreferences.getSharedPreferences(MainActivity.this, "test", MODE_PRIVATE);
                String hello = test.getString("hello", "");
//                ContentResolver provider = getContentResolver();
//                Cursor cursorTid;
//                String hello = "";
//                try {
//                    Uri uriTid = Uri.parse("content://com.example.xposedmodule.provider/test/getString");
//                    String[] selectionArgs = {"0","hello",""};
//                    cursorTid = provider.query(uriTid, null, null,selectionArgs, null);
//                    Bundle extras = cursorTid.getExtras();
//                    hello  = (String) extras.get("value");
//                    Log.e("asdasd",hello);
//                } catch (Exception e) {
//                    Log.e("TAG",e.getMessage()+"\t"+e.toString());
//                    e.printStackTrace();
//                }
                showToast("点击" +hello);
            }
        });
    }

    public  void showToast(final String content){
        Toast.makeText(App.context, content, Toast.LENGTH_SHORT).show();
    }

    public String getText(){
        return "aaaaa";
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                imei = tm.getDeviceId();
            }else {
                Method method = tm.getClass().getMethod("getImei");
                imei = (String) method.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

}
