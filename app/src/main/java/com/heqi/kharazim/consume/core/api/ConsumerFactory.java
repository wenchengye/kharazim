package com.heqi.kharazim.consume.core.api;

import com.heqi.kharazim.consume.core.internal.api.ConsumerInternal;

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
