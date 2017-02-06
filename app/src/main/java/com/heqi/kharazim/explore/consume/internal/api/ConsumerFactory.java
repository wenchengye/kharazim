package com.heqi.kharazim.explore.consume.internal.api;

import com.heqi.kharazim.explore.consume.api.Consumer;

/**
 * Created by overspark on 2016/12/28.
 */

public interface ConsumerFactory {

  Consumer buildConsumer();

  ConsumerInternal buildActionConsumerInternal();

  ConsumerInternal buildSoundConsumerInternal();

  ConsumerInternal buildMusicConsumerInternal();
}
