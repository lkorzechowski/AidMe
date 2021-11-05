package com.orzechowski.aidme.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

public class MultipartRequest extends Request<NetworkResponse> {
    private final Response.Listener<NetworkResponse> mListener;
    private final Response.ErrorListener mErrorListener;
    private final Map<String, String> mHeaders;
    private final String mMimeType;
    private final byte[] mMultipartBody;

    public MultipartRequest(String url, Map<String, String> headers, String mimeType,
                            byte[] multipartBody, Response.Listener<NetworkResponse> listener,
                            Response.ErrorListener errorListener)
    {
        super(Method.GET, url, errorListener);
        mListener = listener;
        mErrorListener = errorListener;
        mHeaders = headers;
        mMimeType = mimeType;
        mMultipartBody = multipartBody;
    }

    @Override
    public String getBodyContentType()
    {
        return mMimeType;
    }

    @Override
    public Map<String, String> getHeaders()
    {
        return mHeaders;
    }

    @Override
    public byte[] getBody()
    {
        return mMultipartBody;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response)
    {
        try {
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response)
    {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error)
    {
        mErrorListener.onErrorResponse(error);
    }
}
