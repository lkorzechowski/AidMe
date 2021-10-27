package com.orzechowski.aidme.tutorial;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

public class FileRequest extends Request<byte[]>
{
    private final Response.Listener<byte[]> mListener;
    private final Map<String, String> mParams;
    public Map<String, String> responseHeaders;

    public FileRequest(int post, String mUrl, Response.Listener<byte[]> listener,
                       Response.ErrorListener errorListener, Map<String, String> params)
    {
        super(post, mUrl, errorListener);
        setShouldCache(false);
        mListener = listener;
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams()
    {
        return mParams;
    }

    @Override
    protected void deliverResponse(byte[] response)
    {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response)
    {
        responseHeaders = response.headers;
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}