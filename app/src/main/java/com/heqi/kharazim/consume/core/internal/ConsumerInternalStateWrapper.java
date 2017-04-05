package com.heqi.kharazim.consume.core.internal;

import android.net.Uri;

import com.heqi.kharazim.consume.core.internal.api.ConsumerInternal;
import com.heqi.kharazim.consume.core.internal.api.ConsumerInternalState;
import com.heqi.kharazim.consume.core.internal.api.InternalState;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/2/7.
 */

public class ConsumerInternalStateWrapper implements ConsumerInternalState {

  private ConsumerInternal consumer;

  private Uri source;
  private InternalState state = InternalState.IDLE;

  private ConsumerInternalCallback callback;
  private ConsumerInternalCallback internalCallback = new ConsumerInternalCallback() {
    @Override
    public void onPrepared() {
      state = InternalState.Prepared;
      if (callback != null) {
        callback.onPrepared();
      }
    }

    @Override
    public void onError(String msg) {
      stop();
      if (callback != null) {
        callback.onError("media player error : " + msg);
      }
    }

    @Override
    public void onPlayerOver() {
      state = InternalState.PlayOver;
      if (callback != null) {
        callback.onPlayerOver();
      }
    }
  };

  public ConsumerInternalStateWrapper(ConsumerInternal consumer) {
    this.consumer = consumer;
    this.consumer.setCallback(internalCallback);
  }

  @Override
  public void release() {
    this.consumer.release();
    state = InternalState.IDLE;
  }

  @Override
  public void prepare() {
    if (state != InternalState.IDLE) return;

    if (KharazimUtils.validateSourceUri(source)) {
      state = InternalState.Preparing;
      consumer.setSource(source);
      consumer.prepare();
    } else {
      if (callback != null) {
        callback.onError("illegal data source : " + (source == null ? "null" : source.toString()));
      }
    }
  }

  @Override
  public void start() {
    if (state == InternalState.Prepared || state == InternalState.Paused
        || state == InternalState.PlayOver) {
      state = InternalState.Playing;
      consumer.start();
    }
  }

  @Override
  public void pause() {
    if (state == InternalState.Playing) {
      state = InternalState.Paused;
      consumer.pause();
    }
  }

  @Override
  public void stop() {
    state = InternalState.IDLE;
    consumer.stop();
  }

  @Override
  public void seek(int milliseconds) {
    if (isPlayableState()) {
      consumer.seek(milliseconds);
    }
  }

  @Override
  public void setVolume(float volume) {
    consumer.setVolume(volume);
  }

  @Override
  public int getProgress() {
    return consumer.getProgress();
  }

  @Override
  public int getDuration() {
    return consumer.getDuration();
  }

  @Override
  public float getVolume() {
    return consumer.getVolume();
  }

  @Override
  public void setSource(Uri uri) {
    stop();
    this.source = uri;
  }

  @Override
  public void setCallback(ConsumerInternalCallback callback) {
    this.callback = callback;
  }

  @Override
  public InternalState getInternalState() {
    return state;
  }

  private boolean isPlayableState() {
    return state == InternalState.Prepared || state == InternalState.Paused
        || state == InternalState.PlayOver;
  }
}
