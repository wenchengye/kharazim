package com.heqi.kharazim.explore.consume.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternal;

/**
 * Created by overspark on 2017/2/7.
 */

public class VideoViewConsumer implements ConsumerInternal {

  private Context context;
  private VideoView videoView;

  private ConsumerInternalCallback callback;
  private Uri source;

  private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
    @Override
    public void onPrepared(MediaPlayer mp) {
      if (callback != null) {
        callback.onPrepared();
      }
    }
  };

  private MediaPlayer.OnCompletionListener completionListener =
      new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
          if (callback != null) {
            callback.onPlayerOver();
          }
        }
      };

  private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
      if (callback != null) {
        callback.onError("video view error : " + what);
      }
      return true;
    }
  };

  public VideoViewConsumer(Context context, VideoView videoView) {
    this.context = context;
    this.videoView = videoView;
    this.videoView.setOnPreparedListener(preparedListener);
    this.videoView.setOnErrorListener(errorListener);
    this.videoView.setOnCompletionListener(completionListener);
  }

  @Override
  public void release() {
    this.videoView.setOnPreparedListener(null);
    this.videoView.setOnErrorListener(null);
    this.videoView.setOnCompletionListener(null);
    this.videoView.stopPlayback();
    this.videoView = null;
  }

  @Override
  public void prepare() {
    if (Const.validateSourceUri(source)) {
      videoView.setVideoURI(source);
    } else {
      if (callback != null) {
        callback.onError("illegal data source : " + (source == null ? "null" : source.toString()));
      }
    }
  }

  @Override
  public void start() {
    videoView.start();
  }

  @Override
  public void pause() {
    videoView.pause();
  }

  @Override
  public void stop() {
    videoView.stopPlayback();
  }

  @Override
  public void seek(int milliseconds) {
    videoView.seekTo(milliseconds);
  }

  @Override
  public int getProgress() {
    return videoView.getCurrentPosition();
  }

  @Override
  public int getDuration() {
    return videoView.getDuration();
  }

  @Override
  public void setSource(Uri uri) {
    source = uri;
  }

  @Override
  public void setCallback(ConsumerInternalCallback callback) {
    this.callback = callback;
  }
}
