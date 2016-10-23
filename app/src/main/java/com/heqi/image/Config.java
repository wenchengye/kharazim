package com.heqi.image;

import android.content.Context;
import android.content.res.Resources;

/**
 * Image manager configurations.
 *
 * Created by wenchengye on 16/10/11.
 */
public interface Config {
  Context getContext();
  String getFileCacheDir();
  Resources getResources();
  int getFileCacheSize();
  int getMemoryCacheSize();
  int getNetworkThreadPoolSize();
  int getLocalThreadPoolSize();
}
