package com.heqi.kharazim.explore.consume.internal.api;

import android.net.Uri;

/**
 * Created by overspark on 2017/2/7.
 */

public interface ConsumerInternalStateRepeat extends ConsumerInternalState {

  void setSource(Uri uri, int repeatSum, int margin);

  void setRepeatCallback(ConsumerInternalRepeatCallback callback);

  int getRepeatIndex();

  int getRepeatSum();

  int getMargin();

  interface ConsumerInternalRepeatCallback {
    void onRepeat(int repeatIndex, int repeatSum);
  }

}
