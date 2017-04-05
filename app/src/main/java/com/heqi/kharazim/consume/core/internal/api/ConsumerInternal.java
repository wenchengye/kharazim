package com.heqi.kharazim.consume.core.internal.api;

import android.net.Uri;

/**
 * Created by overspark on 2016/12/28.
 */

public interface ConsumerInternal {

  void release();

  void prepare();

  void start();

  void pause();

  void stop();

  void seek(int milliseconds);

  void setVolume(float volume);

  int getProgress();

  int getDuration();

  float getVolume();

  void setSource(Uri uri);

  void setCallback(ConsumerInternalCallback callback);

  interface ConsumerInternalCallback {

    void onPrepared();

    void onError(String msg);

    void onPlayerOver();
  }
}
