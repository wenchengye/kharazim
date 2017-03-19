package com.heqi.rpc;

import android.net.Uri;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenchengye on 16/8/28.
 */
public abstract class AbstractVolleyRequest<T> extends Request<T> {

  public AbstractVolleyRequest(ErrorListener listener) {
    super(Method.POST, null, listener);
    setRetryPolicy(this);
  }

  private String generateUrl() {
    String url = getBaseUrl();
    if (TextUtils.isEmpty(url)) {
      return null;
    }
    Map<String, String> urlParams = new HashMap<String, String>();
    setGetParams(urlParams);
    Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
    for (Map.Entry<String, String> paramEntry : urlParams.entrySet()) {
      if (!TextUtils.isEmpty(paramEntry.getValue())) {
        uriBuilder.appendQueryParameter(paramEntry.getKey(), paramEntry.getValue());
      }
    }
    url = uriBuilder.toString();
    return url;
  }

  private void setRetryPolicy(AbstractVolleyRequest<T> request) {
    request.setRetryPolicy(new DefaultRetryPolicy(getTimeout(),
        getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
  }

  /**
   * Override getUrl of {@code Request}.
   * cause of Request's Get params must be set in constructor.
   */
  @Override
  public final String getUrl() {
    return generateUrl();
  }

  /**
   * Get the base url of ultimate url, build with params by generateUrl().
   *
   * @return base url
   */
  protected abstract String getBaseUrl();

  /**
   * set HTTP Url params, implementation must call super.setParams() first.
   *
   * @param params
   */
  protected void setGetParams(Map<String, String> params) {
  }

  /**
   * Get request timeout.
   *
   * @return timeout in ms
   */
  protected int getTimeout() {
    return DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
  }

  /**
   * Get request retry times.
   *
   * @return retry times
   */
  protected int getRetries() {
    return DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
  }
}
