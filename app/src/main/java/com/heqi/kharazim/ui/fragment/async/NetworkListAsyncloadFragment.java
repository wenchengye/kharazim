package com.heqi.kharazim.ui.fragment.async;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.heqi.base.utils.CollectionUtils;
import com.heqi.fetcher.BaseFetcher;
import com.heqi.fetcher.FetchHelper;
import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.ui.adapter.HeaderViewAdapter;
import com.heqi.kharazim.ui.view.AbstractFetchMoreFooterView;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A base fragment for common network content display, M is the model type in list.
 */
public abstract class NetworkListAsyncloadFragment<M>
    extends AsyncLoadFragment {

  protected static final int FIRST_FETCH_COUNT = 15;
  protected static final int PAGE_SIZE = 15;
  protected HeaderViewAdapter headerViewAdapter;
  protected DataAdapter<M> contentAdapter;
  private ListView contentListView;
  private AbstractFetchMoreFooterView footerView;
  private FetchHelper<M> fetchHelper;
  private List<M> newData;
  private int lastTryFetch;
  private Parcelable listState;
  private boolean isReload;

  protected BaseFetcher.Callback<M> fetchCallback =
      new BaseFetcher.Callback<M>() {
        @Override
        public void onFetched(int start, int size, BaseFetcher.ResultList<M> result) {
          NetworkListAsyncloadFragment.this.onFetched(start, size, result);
        }

        @Override
        public void onFailed(int start, ExecutionException e) {
          NetworkListAsyncloadFragment.this.onFailed(start, e);
        }
      };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      listState = savedInstanceState.getParcelable(Intents.EXTRA_LIST_STATE);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    if (outState != null && contentListView != null) {
      outState.putParcelable(Intents.EXTRA_LIST_STATE, contentListView.onSaveInstanceState());
    }
    super.onSaveInstanceState(outState);
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.common_listview_layout;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    contentListView = newListView(contentView);
    footerView = newFetchMoreFooterView(contentListView);
    contentListView.setAdapter(newListAdapter());
    contentListView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                           int totalItemCount) {
        if (visibleItemCount + firstVisibleItem == totalItemCount
            && contentAdapter.getCount() > 0) {
          if (!shouldLoadMore(firstVisibleItem, visibleItemCount, totalItemCount)) {
            return;
          }
          onLoadingMore();
        }
      }

      @Override
      public void onScrollStateChanged(AbsListView absListView, int i) {
      }
    });
  }

  protected boolean shouldLoadMore(int firstVisibleItem, int visibleItemCount,
                                   int totalItemCount) {
    final boolean should = lastTryFetch != totalItemCount;
    lastTryFetch = totalItemCount;
    return should;
  }

  protected ListAdapter newListAdapter() {
    contentAdapter = newContentAdapter();
    headerViewAdapter = new HeaderViewAdapter(null, null, contentAdapter);
    return headerViewAdapter;
  }

  @Override
  protected void onPrepareLoading() {
    lastTryFetch = 0;
    if (!isReload) {
      showLoadingTipsView();
    }
  }

  @Override
  protected void onStartLoading() {
    if (!isInflated) {
      return;
    }
    getFetchHelper().fetch();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  private void onLoadingMore() {
    if (needToLoadMore()) {
      int pageSize = getPageSize() == 0 ? FIRST_FETCH_COUNT : getPageSize();
      getFetchHelper().fetchMore(pageSize);
    }
  }

  /**
   * Usually NetworkListAsyncloadFragment will fetch more data when the listView scrolled to end
   * until no more data can be fetched from the fetcher.
   * You can override this method to return false if you clearly know that you don't need the
   * fetcher to load more even if more data can be fetched.
   *
   * @return whether need to load more
   */
  protected boolean needToLoadMore() {
    // Call super here is to avoid subclass to override needToLoadData and cause some error.
    return super.needToLoadData();
  }

  protected void onFetched(int start, int size, BaseFetcher.ResultList<M> result) {
    isReload = true;
    hideLoadingTipsView();
    hideFetchFailedTipsView();

    if (result.data.isEmpty()) { // No data
      if (start == 0) { // First page
        onNoFetchResult();
      } else { // Non-first page
        if (hasFooterView()) {
          headerViewAdapter.addFooter(footerView);
          footerView.showNoMore();
        }
      }
    } else {
      if (hasFooterView()) {
        headerViewAdapter.addFooter(footerView);
        footerView.showLoading();
      }
      newData = CollectionUtils.replaceFromPosition(contentAdapter.getData(), result.data, start);
      contentAdapter.setData(newData);
      newData = null;
      headerViewAdapter.notifyDataSetChanged();
    }
    if (listState != null) {
      contentListView.onRestoreInstanceState(listState);
      listState = null;
    }
  }

  protected void onFailed(final int start, ExecutionException e) {
    isReload = true;
    hideLoadingTipsView();
    hideFetchFailedTipsView();
    showFetchFailedTipsView(start, e);
  }

  protected FetchHelper<M> getFetchHelper() {
    if (fetchHelper == null) {
      fetchHelper = newFetchHelper();
    }
    return fetchHelper;
  }

  protected void resetFetchHelper() {
    fetchHelper = null;
    isReload = false;
  }

  protected DataAdapter<M> getContentAdapter() {
    return contentAdapter;
  }

  protected FetchHelper<M> newFetchHelper() {
    if (getFirstFetchSize() != 0 && getPageSize() != 0) {
      return new FetchHelper<M>(newFetcher(), fetchCallback, getFirstFetchSize(), getPageSize());
    }
    return new FetchHelper<M>(newFetcher(), fetchCallback);
  }

  protected HeaderViewAdapter getAdapter() {
    return headerViewAdapter;
  }

  protected ListView getListView() {
    return contentListView;
  }

  protected ListView newListView(View contentView) {
    return (ListView) contentView.findViewById(R.id.listview);
  }

  protected boolean isInflated() {
    return isInflated;
  }

  protected int getFirstFetchSize() {
    return FIRST_FETCH_COUNT;
  }

  protected int getPageSize() {
    return PAGE_SIZE;
  }

  public AbstractFetchMoreFooterView getFooterView() {
    return footerView;
  }

  private boolean hasFooterView() {
    return footerView != null;
  }

  protected void reload() {
    lastTryFetch = 0;
    if (fetchHelper != null && fetchHelper.getFetcher() != null) {
      fetchHelper.getFetcher().clearCache();
    }

    if (contentListView != null) {
      contentListView.post(new Runnable() {
        @Override
        public void run() {
          isReload = true;
          // Because we post this, so we need to check if this fragment is not attached
          requestLoad();
        }
      });
    }
  }

  protected abstract BaseFetcher<M> newFetcher();

  protected abstract DataAdapter<M> newContentAdapter();

  protected abstract AbstractFetchMoreFooterView newFetchMoreFooterView(ViewGroup parent);

  protected abstract void showLoadingTipsView();

  protected abstract void hideLoadingTipsView();

  protected abstract void showFetchFailedTipsView(final int start, ExecutionException e);

  protected abstract void hideFetchFailedTipsView();

  protected abstract void onNoFetchResult();
}
