package com.holystock.newmyoffer.data.api.services;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private VolleySingleton(Context context) {
        ctx = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized void initialize(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
    }

    public static synchronized VolleySingleton getInstance() {
        if (instance == null) {
            throw new IllegalStateException("VolleySingleton is not initialized. Call initialize(context) first.");
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    // =======================
    // CONTEXT GETTER
    // =======================
    public Context getContext() {
        return ctx;
    }
}