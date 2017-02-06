package com.heqi.kharazim.explore.consume.internal;

import android.net.Uri;

import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternal;
import com.heqi.kharazim.explore.consume.internal.api.InternalState;

/**
 * Created by overspark on 2017/2/3.
 */

public class ConsumerInternalRepeatWrapper implements ConsumerInternal {

  private ConsumerInternal internal;
  private InternalState state = InternalState.IDLE;
  private int repeatIndex = 0;
  private int repeatSum = 0;
  private int margin = 0;

  private ConsumerInternalRepeatWrapperCallback callback;
  private ConsumerInternalCallback internalCallback = new ConsumerInternalCallback() {
    @Override
    public void onPrepared() {
      state = InternalState.Prepared;
      if (callback != null) {
        callback.onPrepared();
      }
    }

    @Override
    public void onError() {
      state = InternalState.IDLE;
      if (callback != null) {
        callback.onError();
      }
    }

    @Override
    public void onPlayerOver() {
      ++repeatIndex;
      if (callback != null) {
        callback.onRepeat(repeatIndex, repeatSum);
      }

      if (repeatIndex >= repeatSum) {
        state = InternalState.PlayOver;
        if (callback != null) {
          callback.onPlayerOver();
        }
      } else {
        internal.seek(0);
        internal.start();
      }
    }
  };

  public ConsumerInternalRepeatWrapper(ConsumerInternal internal) {
    this.internal = internal;
    this.internal.setCallback(internalCallback);
  }

  public void setSource(Uri uri, int repeatSum, int margin) {
    internal.setSource(uri);
    this.repeatSum = repeatSum;
    this.margin = margin;
    this.state = InternalState.IDLE;
  }

  public int getRepeatIndex() {
    return repeatIndex;
  }

  public int getRepeatSum() {
    return repeatSum;
  }

  public int getMargin() {
    return margin;
  }

  @Override
  public void release() {
    this.internal.release();
  }

  @Override
  public void prepare() {
    repeatIndex = 0;
    this.state = InternalState.Preparing;
    this.internal.prepare();
  }

  @Override
  public void start() {
    this.state = InternalState.Playing;
    this.internal.start();
  }

  @Override
  public void pause() {
    this.state = InternalState.Paused;
    this.internal.pause();
  }

  @Override
  public void stop() {
    this.state = InternalState.IDLE;
    this.internal.stop();
  }

  @Override
  public void seek(int milliseconds) {
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
  public void setSource(Uri uri) {
    setSource(uri, 1, 0);
  }

  @Override
  public void setCallback(ConsumerInternalCallback callback) {
    /** unused method, do nothing */
  }

  @Override
  public InternalState getInternalState() {
    return this.state;
  }

  public void setRepeatWrapperCallback(ConsumerInternalRepeatWrapperCallback callback) {
    this.callback = callback;
  }

  public interface ConsumerInternalRepeatWrapperCallback
      extends ConsumerInternal.ConsumerInternalCallback {
    void onRepeat(int repeatIndex, int repeatSum);
  }
}
