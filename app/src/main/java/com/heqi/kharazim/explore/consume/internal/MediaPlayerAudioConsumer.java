package com.heqi.kharazim.explore.consume.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;

import com.heqi.base.utils.HttpUtil;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternal;
import com.heqi.kharazim.utils.KharazimUtils;

import java.io.IOException;

/**
 * Created by overspark on 2017/2/6.
 */

public class MediaPlayerAudioConsumer implements ConsumerInternal {

  private Context context;
  private MediaPlayer player;

  private ConsumerInternalCallback callback;

  private Uri source;

  private OnPreparedListener preparedListener = new OnPreparedListener() {
    @Override
    public void onPrepared(MediaPlayer mp) {
      if (callback != null) {
        callback.onPrepared();
      }
    }
  };

  private OnErrorListener errorListener = new OnErrorListener() {
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
      if (callback != null) {
        callback.onError("media player error : " + what);
      }
      return true;
    }
  };

  private OnCompletionListener completionListener = new OnCompletionListener() {
    @Override
    public void onCompletion(MediaPlayer mp) {
      if (callback != null) {
        callback.onPlayerOver();
      }
    }
  };

  public MediaPlayerAudioConsumer(Context context) {
    this.context = context;
    player = new MediaPlayer();
    player.setLooping(false);
    player.setOnPreparedListener(preparedListener);
    player.setOnCompletionListener(completionListener);
    player.setOnErrorListener(errorListener);
  }

  @Override
  public void release() {
    player.setOnErrorListener(null);
    player.setOnCompletionListener(null);
    player.setOnPreparedListener(null);
    player.stop();
    player.release();
    player = null;
  }

  @Override
  public void prepare() {
    try {
      if (KharazimUtils.validateSourceUri(source)) {
        if (HttpUtil.isHttpScheme(source.getScheme())) {
          player.setDataSource(source.toString());
        } else {
          player.setDataSource(context, source);
        }
        player.prepareAsync();
      } else {
        if (callback != null) {
          callback.onError("illegal data source : " + (source == null ? "null" : source.toString()));
        }
      }
    } catch (IOException e) {
      if (callback != null) {
        callback.onError("media player io exception");
      }
    }

  }

  @Override
  public void start() {
    player.start();
  }

  @Override
  public void pause() {
    player.pause();
  }

  @Override
  public void stop() {
    player.stop();
    player.reset();
  }

  @Override
  public void seek(int milliseconds) {
    player.seekTo(milliseconds);
  }

  @Override
  public int getProgress() {
    return player.getCurrentPosition();
  }

  @Override
  public int getDuration() {
    return player.getDuration();
  }

  @Override
  public void setSource(Uri uri) {
    this.source = uri;
  }

  @Override
  public void setCallback(ConsumerInternalCallback callback) {
    this.callback = callback;
  }

}
