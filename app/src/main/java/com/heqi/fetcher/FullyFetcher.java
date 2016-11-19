package com.heqi.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Usage: a subclass of baseFetcher used to fetch non-paged data.
 */
public abstract class FullyFetcher<T> extends BaseFetcher<T> {

  private List<T> cacheList;
  private final byte[] lock = new byte[0];

  @Override
  protected List<T> fetchHttpData(int start, int size) throws ExecutionException {
    List<T> list = null;
    synchronized (lock) {
      if (cacheList != null) { // try get from cache
        list = cacheList;
      }
    }
    if (list == null) {
      list = fetchHttpData();
    }
    List<T> result = new ArrayList<T>();
    for (int i = start; list != null && i < size && i < list.size(); ++i) {
      result.add(list.get(i));
    }
    synchronized (lock) { // save into cache
      cacheList = list;
    }
    return result;
  }

  @Override
  public void clearCache() {
    super.clearCache();
    synchronized (lock) {
      cacheList = null;
    }
  }

  protected abstract List<T> fetchHttpData() throws ExecutionException;
}
