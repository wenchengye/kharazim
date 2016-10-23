package com.heqi.image.cache;

import android.graphics.Bitmap;

import com.heqi.base.concurrent.CachedThreadPoolExecutorWithCapacity;

import java.util.concurrent.Executor;

/**
 * Created by wenchengye on 16/10/11.
 */
public class ImageCacheUtils {

  private static final Executor EXECUTOR =
      new CachedThreadPoolExecutorWithCapacity(1, 60 * 1000L);

  private ImageCacheUtils() {
  }

  /**
   * Saves image to cache in asynchronous mode.
   */
  public static void cacheImage(final ImageCache cache, final String key, final Bitmap bitmap) {
    EXECUTOR.execute(new Runnable() {
      @Override
      public void run() {
        cache.put(key, bitmap);
      }
    });
  }
}
