package com.example.xposedmodule;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//import com.frybits.harmony.Harmony;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static EditText ev;
    private  Context context;
    Button btn;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ev = findViewById(R.id.edit);
        btn = findViewById(R.id.btn);
        context = this;
        MultiprocessSharedPreferences.setAuthority("com.example.xposedmodule.provider");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                获取方式1
//                SharedPreferences sharedPreferences = MultiprocessSharedPreferences.getSharedPreferences(context, "test", Context.MODE_PRIVATE);
//                sharedPreferences.edit().putString("hello","world").commit();
//                String hello = sharedPreferences.getString("hello", "");
//                Log.e("hello",hello);
//                  获取方式2
//                ContentResolver provider = getContentResolver();
//                Cursor cursorTid;
//                try {
//                    Uri uri = Uri.parse("content://com.example.xposedmodule.provider/test/getString");
//                    String[] selectionArgs = {"0","hello",""};//第一个参数是否是安全模式，一般为0。第二个参数读取的key，第三个参数defaultKey
//                    cursorTid = provider.query(uri, null, null,selectionArgs, null);
//                    Bundle extras = cursorTid.getExtras();
//                    String hello1 = (String) extras.get("value");
//                    Log.e("asdasd",hello1);
//                } catch (Exception e) {
//                    Log.e("TAG",e.getMessage()+"\t"+e.toString());
//                    e.printStackTrace();
//                }
                String text = ev.getText().toString();
                SharedPreferences sharedPreferences = MultiprocessSharedPreferences.getSharedPreferences(context, "test", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("hello",text).commit();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
