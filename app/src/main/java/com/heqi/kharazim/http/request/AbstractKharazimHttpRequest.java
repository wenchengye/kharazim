package com.heqi.kharazim.http.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.heqi.rpc.GsonFactory;
import com.heqi.rpc.GsonVolleyRequest;

/**
 * Base Http request of Kharazim project.
 * All http in Kharazim use same base url root, and use POST method.
 *
 * Created by overspark on 2016/11/19.
 */

public abstract class AbstractKharazimHttpRequest<T> extends GsonVolleyRequest<T> {

  private static final int KHARAZIM_HTTP_METHOD = Method.POST;
  public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";
  private static final String KHARAZIM_SERVICE_ROOT = "heal/api/heal/";
  private static final String KHARAZIM_URL_ROOT = KHARAZIM_SERVER + KHARAZIM_SERVICE_ROOT;

  /**
   * @return kharazim http baseUrlRoot, compostied by server addr and service root.
   */
  protected static String getBaseUrlRoot() {
    return KHARAZIM_URL_ROOT;
  }

  public AbstractKharazimHttpRequest(Class<T> clazz,
                                     Response.Listener<T> listener,
                                     Response.ErrorListener errorListener) {
    super(clazz, listener, errorListener);
  }

  /**
   * Since all kharazim http request use same baseUrl root,
   * And subClass shouldn't override this method.
   * Make it final.
   *
   * @return Kharazim http request's base url
   */
  @Override
  protected final String getBaseUrl() {
    return getBaseUrlRoot() + getBaseUrlDirectory();
  }

//  /**
//   * Override getMethod of {@code Request}, using POST in kharazim project.
//   * @return kharazim's http request method.
//   */
//  @Override
//  public int getMethod() {
//    return KHARAZIM_HTTP_METHOD;
//  }

  /**
   * @return the directory in kharazim baseUrlRoot.
   */
  protected abstract String getBaseUrlDirectory();

}
