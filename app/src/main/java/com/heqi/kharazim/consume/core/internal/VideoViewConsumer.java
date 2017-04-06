package com.heqi.kharazim.consume.core.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.VideoView;

import com.heqi.kharazim.consume.core.internal.api.ConsumerInternal;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/2/7.
 */

public class VideoViewConsumer implements ConsumerInternal {

  private Context context;
  private VideoView videoView;

  private ConsumerInternalCallback callback;
  private Uri source;

  private Handler uiHandler = new Handler(Looper.getMainLooper());
  private Handler consumerHandler;

  private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
    @Override
    public void onPrepared(MediaPlayer mp) {
      consumerHandler.post(new Runnable() {
        @Override
        public void run() {
          if (callback != null) {
            callback.onPrepared();
          }
        }
      });
    }
  };

  private MediaPlayer.OnCompletionListener completionListener =
      new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
          consumerHandler.post(new Runnable() {
            @Override
            public void run() {
              if (callback != null) {
                callback.onPlayerOver();
              }
            }
          });
        }
      };

  private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
    @Override
    public boolean onError(MediaPlayer mp, final int what, int extra) {
      consumerHandler.post(new Runnable() {
        @Override
        public void run() {
          if (callback != null) {
            callback.onError("video view error : " + what);
          }

        }
      });
      return true;
    }
  };

  public VideoViewConsumer(Context context, VideoView videoView) {
    this.context = context;
    this.videoView = videoView;
    this.videoView.setOnPreparedListener(preparedListener);
    this.videoView.setOnErrorListener(errorListener);
    this.videoView.setOnCompletionListener(completionListener);
    consumerHandler = new Handler(Looper.myLooper());
  }

  @Override
  public void release() {
    this.videoView.setOnPreparedListener(null);
    this.videoView.setOnErrorListener(null);
    this.videoView.setOnCompletionListener(null);
    this.videoView = null;
  }

  @Override
  public void prepare() {
    uiHandler.post(new Runnable() {
      @Override
      public void run() {
        if (KharazimUtils.validateSourceUri(source)) {
          videoView.setVideoURI(source);
        } else {
          if (callback != null) {
            callback.onError("illegal data source : " + (source == null ? "null" : source.toString()));
          }
        }
      }
    });
  }

  @Override
  public void start() {
    uiHandler.post(new Runnable() {
      @Override
      public void run() {
        videoView.start();
      }
    });

  }

  @Override
  public void pause() {
    uiHandler.post(new Runnable() {
      @Override
      public void run() {
        videoView.pause();
      }
    });

  }

  @Override
  public void stop() {
    uiHandler.post(new Runnable() {
      @Override
      public void run() {
        videoView.stopPlayback();
      }
    });
  }

  @Override
  public void seek(final int milliseconds) {
    uiHandler.post(new Runnable() {
      @Override
      public void run() {
        videoView.seekTo(milliseconds);
      }
    });

  }

  @Override
  public void setVolume(float volume) {
    // unused
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

  @Override
  public float getVolume() {
    return 0.f;
  }
}
