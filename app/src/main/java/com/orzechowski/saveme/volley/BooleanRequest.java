package com.orzechowski.saveme.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

public class BooleanRequest extends Request<Boolean>
{
    private final Response.Listener<Boolean> mListener;
    private final Response.ErrorListener mErrorListener;
    private final String mRequestBody;
    private final String mProtocol = "utf-8";

    public BooleanRequest(int method, String url, String requestBody,
                          Response.Listener<Boolean> listener, Response.ErrorListener errorListener)
    {
        super(method, url, errorListener);
        mListener = listener;
        mErrorListener = errorListener;
        mRequestBody = requestBody;
    }

    @Override
    protected Response<Boolean> parseNetworkResponse(NetworkResponse response)
    {
        boolean parsed;
        try {
            parsed = Boolean.parseBoolean(new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers)));
        } catch (UnsupportedEncodingException e) {
            parsed = Boolean.parseBoolean(new String(response.data));
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError)
    {
        return super.parseNetworkError(volleyError);
    }

    @Override
    public void deliverError(VolleyError error)
    {
        mErrorListener.onErrorResponse(error);
    }

    @Override
    protected void deliverResponse(Boolean response)
    {
        mListener.onResponse(response);
    }

    @Override
    public String getBodyContentType()
    {
        return String.format("application/json; charset=%s", mProtocol);
    }

    @Override
    public byte[] getBody()
    {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(mProtocol);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
