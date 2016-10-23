package com.heqi.image;

import android.graphics.Bitmap;

import com.heqi.base.concurrent.CachedThreadPoolExecutorWithCapacity;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by wenchengye on 16/10/11.
 */
public class ImageThreadPool {

  private final CachedThreadPoolExecutorWithCapacity executor;
  private static final long CACHE_TIME_MS = 60 * 1000L;

  public ImageThreadPool(int maxThreadNum) {
    executor = new CachedThreadPoolExecutorWithCapacity(maxThreadNum, CACHE_TIME_MS);
  }

  public Future<Bitmap> execute(Callable<Bitmap> callable) {
    return executor.submit(callable);
  }

  public void execute(Runnable task) {
    executor.execute(task);
  }

  public boolean cancel(Runnable runnable, boolean mayInterruptIfRunning) {
    return executor.cancel(runnable, mayInterruptIfRunning);
  }
}
