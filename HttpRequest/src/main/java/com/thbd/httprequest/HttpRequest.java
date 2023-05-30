package com.thbd.httprequest;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

    private OkHttpClient mClient = new OkHttpClient();
    private Response response;
    private RequestBody fromBody;
    private Context context;
    String URL = "", strJson = "getting data... Custom HTTP";
    String val = "Failed";

    private static final String TAG = "Custom Http Request";

    public HttpRequest( ) {

    }

    // TODO: pass parameter for call to specific data
    public String CallForData(RequestBody body, String url) {
        this.URL = url;
        this.fromBody = body;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (isConnect()) {
            Request request = new Request.Builder()
                    .header("User-Agent",
                            context.getString(
                                    R.string.agent
                            )
                    )
                    .url(URL)
                    .post(fromBody).build();
            try {
                response = mClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                strJson = "failed";
            }

            if (response != null && response.isSuccessful()) {
                try {
                    strJson = response.body().string();
                    Log.e(TAG, "requested body : " + strJson);
                } catch (IOException e) {
                    e.printStackTrace();
                    strJson = "failed";
                }
            } else {
                strJson = "failed";
            }
        } else {
            strJson = "network error";
        }

        return strJson;
    }
    // TODO: without pass parameter for call to specific data
    public String CallForData(String url) {
        this.URL = url;


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (isConnect()) {
            Request request = new Request.Builder()
                    .header("User-Agent",
                            context.getString(
                                    R.string.agent
                            )
                    )
                    .url(URL).build();
            try {
                response = mClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                strJson = "failed";
            }

            if (response != null && response.isSuccessful()) {
                try {
                    strJson = response.body().string();
                    Log.e(TAG, "requested body : " + strJson);
                } catch (IOException e) {
                    e.printStackTrace();
                    strJson = "failed";
                }
            } else {
                strJson = "failed";
            }
        } else {
            strJson = "network error";
        }

        return strJson;
    }

    private boolean isConnect() {
        try {
            String commend = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(commend).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }


    }


    public void snackBarError(Activity act, String msg) {
        View views = act.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(views, msg, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(act.getResources().getColor(android.R.color.holo_red_dark));
        try {
            snackbar.show();
        } catch (Exception e) {
        }
    }


    public void snackBarSuccess(Activity act, String msg) {
        View views = act.findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar.make(views, msg, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(act.getResources().getColor(android.R.color.holo_green_light));
        try {
            snackbar.show();
        } catch (Exception e) {
        }
    }
}
