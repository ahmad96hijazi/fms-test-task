package com.fms.myapplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpHelper {
    private static final OkHttpHelper instance = new OkHttpHelper();

    //private constructor to avoid client applications to use constructor
    private OkHttpHelper() {
    }

    public static OkHttpHelper getInstance() {
        return instance;
    }

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(json.getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    Call put(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(json.getBytes(StandardCharsets.UTF_8));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .put(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
