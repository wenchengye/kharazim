package com.heqi.rpc;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wandoujia.gson.Gson;
import com.wandoujia.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * A canned request for retrieving the response body at a given URL as a Json Object.
 * <p>
 * Created by wenchengye on 16/8/28.
 */
public abstract class GsonVolleyRequest<T> extends AbstractVolleyRequest<T> {
  private final Gson gson;
  private final Class<T> clazz;
  private final Listener<T> listener;

  /**
   * Make request and return a parsed object from JSON.
   *
   * @param clazz         Relevant class object, for Gson's reflection
   * @param listener      Listener to receive the String response
   * @param errorListener Error listener, or null to ignore errors
   */
  public GsonVolleyRequest(Class<T> clazz, Listener<T> listener,
                           ErrorListener errorListener) {
    super(errorListener);
    this.clazz = clazz;
    this.listener = listener;
    this.gson = GsonFactory.getGson();
  }

  @Override
  protected void deliverResponse(T response) {
    listener.onResponse(response);
  }

  @Override
  protected Response<T> parseNetworkResponse(NetworkResponse response) {
    try {
      String json = new String(
          response.data,
          HttpHeaderParser.parseCharset(response.headers));
      return Response.success(gson.fromJson(json, clazz),
          HttpHeaderParser.parseCacheHeaders(response));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return Response.error(new ParseError(e));
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
      return Response.error(new ParseError(e));
    }
  }
}
