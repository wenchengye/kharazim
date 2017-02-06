package com.heqi.kharazim.explore.consume.internal.api;

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

  int getProgress();

  int getDuration();

  void setSource(Uri uri);

  void setCallback(ConsumerInternalCallback callback);

  InternalState getInternalState();

  public interface ConsumerInternalCallback {

    void onPrepared();

    void onError();

    void onPlayerOver();
  }

}
