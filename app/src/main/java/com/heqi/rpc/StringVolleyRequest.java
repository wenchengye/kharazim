package com.heqi.rpc;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 *
 * Created by wenchengye on 16/8/28.
 */
public abstract class StringVolleyRequest extends AbstractVolleyRequest<String> {

  private final Listener<String> mListener;

  /**
   * Creates a new request with the given method.
   *
   * @param listener Listener to receive the String response
   * @param errorListener Error listener, or null to ignore errors
   */
  public StringVolleyRequest(Response.Listener<String> listener,
                             ErrorListener errorListener) {
    super(errorListener);
    mListener = listener;
  }

  @Override
  protected void deliverResponse(String response) {
    mListener.onResponse(response);
  }

  @Override
  protected Response<String> parseNetworkResponse(NetworkResponse response) {
    String parsed;
    try {
      parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
    } catch (UnsupportedEncodingException e) {
      parsed = new String(response.data);
    }
    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
  }

}
