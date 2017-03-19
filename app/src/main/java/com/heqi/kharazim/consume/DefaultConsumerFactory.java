package com.heqi.kharazim.consume;

import android.content.Context;
import android.widget.VideoView;

import com.heqi.kharazim.consume.core.api.Consumer;
import com.heqi.kharazim.consume.core.api.ConsumerFactory;
import com.heqi.kharazim.consume.core.api.ConsumerWrapper;
import com.heqi.kharazim.consume.core.internal.DefaultConsumer;
import com.heqi.kharazim.consume.core.internal.DefaultConsumerWrapper;
import com.heqi.kharazim.consume.core.internal.MediaPlayerAudioConsumer;
import com.heqi.kharazim.consume.core.internal.VideoViewConsumer;
import com.heqi.kharazim.consume.core.internal.api.ConsumerInternal;

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
    return new VideoViewConsumer(this.context, this.videoView);
  }

  @Override
  public ConsumerInternal buildSoundConsumerInternal() {
    return new MediaPlayerAudioConsumer(this.context);
  }

  @Override
  public ConsumerInternal buildMusicConsumerInternal() {
    return new MediaPlayerAudioConsumer(this.context);
  }
}
