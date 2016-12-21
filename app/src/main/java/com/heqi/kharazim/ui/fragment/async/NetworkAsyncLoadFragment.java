package com.heqi.kharazim.ui.fragment.async;

import com.android.volley.Request;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;

import com.android.volley.VolleyError;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.rpc.RpcHelper;

import java.util.concurrent.ExecutionException;


public abstract class NetworkAsyncLoadFragment<T> extends AsyncLoadFragment {
  
  private T data;

  private Listener<T> listener = new Listener<T>() {

  	@Override
  	public void onResponse(T result) {
  		NetworkAsyncLoadFragment.this.onFetched(result);
  	}

  };
  private ErrorListener errorListener = new ErrorListener() {

  	@Override
  	public void onErrorResponse(VolleyError error) {
  		NetworkAsyncLoadFragment.this.onFailed(null);
  	}

  };

  @Override
  protected void onPrepareLoading() {
  	showLoadingTipsView();
  } 

  @Override
  protected void onStartLoading() {
  	Request<T> request = newRequest(listener, errorListener);

  	if (request != null) {
  	  RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequestAsync(request);
  	} else {
  	  hideLoadingTipsView();
  	  hideFetchFailedTipsView();
  	}
  }

  private void onFetched(T result) {
  	hideLoadingTipsView();
  	hideFetchFailedTipsView();

  	if (result == null) {
  	  onNoFetchResult();
  	} else {
  	  this.data = result;
  	  applyData(this.data);
  	}
  }

  private void onFailed(ExecutionException e) {
  	hideLoadingTipsView();
  	hideFetchFailedTipsView();
  	showFetchFailedTipsView(e);
  }

  protected abstract void applyData(T data);

  protected abstract Request<T> newRequest(Listener<T> listener, ErrorListener errorListener);

  protected abstract void showLoadingTipsView();

  protected abstract void hideLoadingTipsView();

  protected abstract void showFetchFailedTipsView(ExecutionException e);

  protected abstract void hideFetchFailedTipsView();

  protected abstract void onNoFetchResult();

}