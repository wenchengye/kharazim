package com.heqi.rpc;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutionException;

/**
 * Http helper class using Volley.
 *
 * Created by wenchengye on 16/8/28.
 */
public class RpcHelper {

  private static RpcHelper instance;

  public static synchronized RpcHelper getInstance(Context context) {
    if (instance == null) {
      instance = new RpcHelper(context);
    }
    return instance;
  }

  /**
   * stop Volley threads.
   */
  public static synchronized void release() {
    if (instance != null) {
      instance.requestQueue.stop();
    }
    instance = null;
  }

  private RequestQueue requestQueue;
  private Context context;

  private RpcHelper(Context context) {
    this.context = context;
    // using application context, keep from leaking Activity.
    requestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
  }

  /**
   * execute request async, call back with request's listeners.
   * the call back will on UI-thread, guaranteed by Volley.
   *
   * @param request
   */
  public <T> void executeRequestAsync(Request<T> request) {
    requestQueue.add(request);
  }

  /**
   * execute request sync.
   * the request's listeners must be set with the param future.
   *
   * @param request
   * @param future
   * @return
   * @throws ExecutionException
   */
  public <T> T executeRequest(Request<T> request, RequestFuture<T> future)
      throws ExecutionException {
    requestQueue.add(request);

    try {
      T ret = future.get();
      return ret;
    } catch (InterruptedException e) {
      throw new ExecutionException(e);
    } catch (ExecutionException e) {
      throw e;
    }
  }
}
