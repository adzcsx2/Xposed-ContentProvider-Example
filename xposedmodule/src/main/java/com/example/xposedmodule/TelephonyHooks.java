package com.example.xposedmodule;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.text.TextUtils;


import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static android.content.Context.MODE_PRIVATE;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by hoyn on 2020/7/1.
 */

public class TelephonyHooks implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.contains("com.example.xposedmodule")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Context context = (Context) param.args[0];
                    SharedPreferences aa = context.getSharedPreferences("aa", MODE_PRIVATE);
                    String data = aa.getString("data", "");
                    XposedBridge.log(data);
                }
            });

        }
        if (lpparam.packageName.contains("com.example.hoyn.example")) {
            final Context[] context = new Context[1];
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    context[0] = (Context) param.args[0];
                }
            });
            //Android N以下获取imei的方法
            findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult("123133212121");
                }
            });
            //Android N以上获取imei的方法
            findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getImei", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("hoyn已经hook了");
                    return "12345678";
                }
            });

            //hook自定义的getText方法
            findAndHookMethod("com.example.hoyn.example.MainActivity", lpparam.classLoader, "getText", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    String data ;
                    MultiprocessSharedPreferences.setAuthority("com.example.xposedmodule.provider");
                    SharedPreferences test = MultiprocessSharedPreferences.getSharedPreferences(context[0], "test", MODE_PRIVATE);
                     data = test.getString("hello", "");
                    XposedBridge.log("获取到了"+data);
                    if(TextUtils.isEmpty(data)){
                        data = "ccccc";
                    }
                    param.setResult(data);
                    XposedBridge.log(param.getResult().toString());
                }
            });
        }
    }

}