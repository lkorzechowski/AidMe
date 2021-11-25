package com.orzechowski.saveme.volley;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.nio.charset.StandardCharsets;

public class StringPost extends StringRequest
{
    private String mRequestBody;

    public void setRequestBody(String requestBody)
    {
        mRequestBody = requestBody;
    }

    public StringPost(int method, String url, Response.Listener<String> listener,
                      @Nullable Response.ErrorListener errorListener)
    {
        super(method, url, listener, errorListener);
    }

    @Override
    public byte[] getBody()
    {
        return mRequestBody == null ? null : mRequestBody.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getBodyContentType()
    {
        return "application/json; charset=utf-8";
    }
}
