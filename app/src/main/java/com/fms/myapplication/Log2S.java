package com.fms.myapplication;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Log2S {
    private static final Log2S instance = new Log2S();

    //private constructor to avoid client applications to use constructor
    private Log2S() {
    }

    public static Log2S getInstance() {
        return instance;
    }

    public void send(String TAG, Context context, String action) {
        HashMap<String, String> request = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm_ss", Locale.getDefault());
        String now = sdf.format(new Date());

        request.put("Serial", "20005");
        request.put("Type", "1");
        request.put("MethodName", "testReboot");
        request.put("Message", action);
        request.put("ModuleId", "17");

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            imei = telephonyManager.getImei();
        } else {
            imei = telephonyManager.getDeviceId();
        }

        request.put("CustomMessage", imei);
        request.put("Date", now);
        Log.d(TAG, request.toString());
        JSONObject json = new JSONObject(request);
        try {
            OkHttpHelper.getInstance().put("https://demo.fms-tech.com/FMSSmartMobility108_22/api/trackingapi/AddNVRLog", json.toString(), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.d(TAG, "response => " + response.isSuccessful());
                    String body = Objects.requireNonNull(response.body()).string();
                    if (response.isSuccessful()) {
                        Log.d(TAG, body);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
