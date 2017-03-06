package com.heqi.kharazim.explore.consume;

import android.content.Context;
import android.widget.VideoView;

import com.heqi.kharazim.explore.consume.api.Consumer;
import com.heqi.kharazim.explore.consume.api.ConsumerWrapper;
import com.heqi.kharazim.explore.consume.internal.DefaultConsumer;
import com.heqi.kharazim.explore.consume.internal.DefaultConsumerWrapper;
import com.heqi.kharazim.explore.consume.internal.MediaPlayerAudioConsumer;
import com.heqi.kharazim.explore.consume.internal.VideoViewConsumer;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerFactory;
import com.heqi.kharazim.explore.consume.internal.api.ConsumerInternal;

/**
 * Created by overspark on 2017/3/3.
 */

public class DefaultConsumerFactory implements ConsumerFactory {

  private Context context;
  private VideoView videoView;

  public DefaultConsumerFactory(Context context, VideoView videoView) {
    this.context = context;
    this.videoView = videoView;
  }

  @Override
  public ConsumerWrapper buildConsumerWrapper() {
    return new DefaultConsumerWrapper(this.context, this);
  }

  @Override
  public Consumer buildConsumer() {
    return new DefaultConsumer(this);
  }

  @Override
  public ConsumerInternal buildActionConsumerInternal() {
    return new MediaPlayerAudioConsumer(this.context);
  }

  @Override
  public ConsumerInternal buildSoundConsumerInternal() {
    return new MediaPlayerAudioConsumer(this.context);
  }

  @Override
  public ConsumerInternal buildMusicConsumerInternal() {
    return new VideoViewConsumer(this.context, this.videoView);
  }
}
