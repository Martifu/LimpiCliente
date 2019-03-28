package com.example.firebaseedu;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyS {
    private static VolleyS mVolleyS = null;
    private RequestQueue requestQueue;

    private VolleyS(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyS getInstance(registro_usuarios context)
    {
        if (mVolleyS==null)
        {
            mVolleyS = new VolleyS(context);
        }
        return mVolleyS;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
