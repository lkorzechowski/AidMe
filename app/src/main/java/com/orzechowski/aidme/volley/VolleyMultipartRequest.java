package com.orzechowski.aidme.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VolleyMultipartRequest extends Request<NetworkResponse>
{
    private final String twoHyphens = "--";
    private final String lineEnd = "\r\n";
    private final String boundary = "apiclient-" + System.currentTimeMillis();
    private final Response.Listener<NetworkResponse> mListener;
    private final Response.ErrorListener mErrorListener;
    private final Map<String, String> mHeaders = new HashMap<>();
    private DataPart dataSet;

    public void setData(DataPart dataSet)
    {
        this.dataSet = dataSet;
    }

    public VolleyMultipartRequest(int method, String url,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener)
    {
        super(method, url, errorListener);
        mListener = listener;
        mErrorListener = errorListener;
        mHeaders.put("Cache-Control", "no-cache, no-store, must-revalidate");
        mHeaders.put("Pragma", "no-cache");
        mHeaders.put("Expires", "0");
    }

    @Override
    public Map<String, String> getHeaders()
    {
        try {
            return (mHeaders != null) ? mHeaders : super.getHeaders();
        } catch (AuthFailureError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getBodyContentType()
    {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(bos);
        try {
            Map<String, String> params = getParams();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                            entry.getKey() + "\"" + lineEnd);
                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(entry.getKey() + lineEnd);
                }
            }
            Map<String, DataPart> data = new HashMap<>();
            data.put("image", dataSet);
            if (!data.isEmpty()) {
                for (Map.Entry<String, DataPart> entry : data.entrySet()) {
                    buildData(dataOutputStream, entry.getValue(), entry.getKey());
                }
            }
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            return bos.toByteArray();
        } catch (IOException | AuthFailureError e) {
            e.printStackTrace();
            return null;
        }
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

    private void buildData(DataOutputStream dataOutputStream, DataPart dataFile, String inputName)
            throws IOException
    {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" +
                inputName + "\"; filename=\"" + dataFile.getFileName() + "\"" + lineEnd);
        if (dataFile.getType() != null && !dataFile.getType().trim().isEmpty()) {
            dataOutputStream.writeBytes("Content-Type: " + dataFile.getType() + lineEnd);
        }
        dataOutputStream.writeBytes(lineEnd);
        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dataFile.getContent());
        int bytesAvailable = fileInputStream.available();
        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        dataOutputStream.writeBytes(lineEnd);
    }
}
