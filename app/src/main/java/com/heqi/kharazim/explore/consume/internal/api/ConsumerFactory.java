package com.heqi.kharazim.explore.consume.internal.api;

import com.heqi.kharazim.explore.consume.api.Consumer;
import com.heqi.kharazim.explore.consume.api.ConsumerWrapper;

/**
 * Created by overspark on 2016/12/28.
 */

public interface ConsumerFactory {

  ConsumerWrapper buildConsumerWrapper();

  Consumer buildConsumer();

  ConsumerInternal buildActionConsumerInternal();

  ConsumerInternal buildSoundConsumerInternal();

  ConsumerInternal buildMusicConsumerInternal();
}
