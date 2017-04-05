package com.heqi.kharazim.consume.core.internal;

import android.net.Uri;

import com.heqi.kharazim.consume.core.internal.api.ConsumerInternalState;
import com.heqi.kharazim.consume.core.internal.api.ConsumerInternalStateRepeat;
import com.heqi.kharazim.consume.core.internal.api.InternalState;

/**
 * Created by overspark on 2017/2/3.
 */

public class ConsumerInternalStateRepeatWrapper implements ConsumerInternalStateRepeat {

  private ConsumerInternalState internal;
  private int repeatIndex = 0;
  private int repeatSum = 0;
  private int margin = 0;

  private ConsumerInternalRepeatCallback repeatCallback;
  private ConsumerInternalCallback callback;
  private ConsumerInternalCallback internalCallback = new ConsumerInternalCallback() {
    @Override
    public void onPrepared() {
      if (callback != null) {
        callback.onPrepared();
      }
    }

    @Override
    public void onError(String msg) {
      if (callback != null) {
        callback.onError(msg);
      }
    }

    @Override
    public void onPlayerOver() {
      ++repeatIndex;
      if (repeatCallback != null) {
        repeatCallback.onRepeat(repeatIndex, repeatSum);
      }

      if (repeatIndex >= repeatSum) {
        if (callback != null) {
          callback.onPlayerOver();
        }
      } else {
        internal.seek(0);
        internal.start();
      }
    }
  };

  public ConsumerInternalStateRepeatWrapper(ConsumerInternalState internal) {
    this.internal = internal;
    this.internal.setCallback(internalCallback);
  }

  @Override
  public void release() {
    this.internal.release();
  }

  @Override
  public void prepare() {
    repeatIndex = 0;
    this.internal.prepare();
  }

  @Override
  public void start() {
    this.internal.start();
  }

  @Override
  public void pause() {
    this.internal.pause();
  }

  @Override
  public void stop() {
    this.internal.stop();
  }

  @Override
  public void seek(int milliseconds) {
    throw new RuntimeException("repeat wrapper don't support seek() method");
  }

  @Override
  public void setVolume(float volume) {
    this.internal.setVolume(volume);
  }

  @Override
  public int getProgress() {
    return this.repeatIndex * this.internal.getDuration() + this.internal.getProgress();
  }

  @Override
  public int getDuration() {
    return this.repeatSum * this.internal.getDuration();
  }

  @Override
  public float getVolume() {
    return this.internal.getVolume();
  }

  @Override
  public void setSource(Uri uri) {
    setSource(uri, 1, 0);
  }

  @Override
  public void setCallback(ConsumerInternalCallback callback) {
    this.callback = callback;
  }

  @Override
  public InternalState getInternalState() {
    return internal.getInternalState();
  }

  @Override
  public void setSource(Uri uri, int repeatSum, int margin) {
    internal.setSource(uri);
    this.repeatSum = repeatSum;
    this.margin = margin;
  }

  @Override
  public void setRepeatCallback(ConsumerInternalRepeatCallback callback) {
    this.repeatCallback = callback;
  }

  @Override
  public int getRepeatIndex() {
    return repeatIndex;
  }

  @Override
  public int getRepeatSum() {
    return repeatSum;
  }

  @Override
  public int getMargin() {
    return margin;
  }
}
