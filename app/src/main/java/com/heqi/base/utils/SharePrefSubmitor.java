package com.heqi.base.utils;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * submit SharedPreferences async.
 * <p>
 * Created by wenchengye on 16/8/28.
 */
public class SharePrefSubmitor {
  private static final ExecutorService pool = Executors
      .newSingleThreadExecutor();

  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
  public static void submit(final SharedPreferences.Editor editor) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
      editor.apply();
    } else {
      pool.execute(new Runnable() {
        @Override
        public void run() {
          editor.commit();
        }
      });
    }
  }
}
