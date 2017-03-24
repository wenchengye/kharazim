package com.heqi.kharazim.consume.core.api;


/**
 * Created by overspark on 2016/12/28.
 */

public interface ConsumerWrapper extends Consumer {

  State getState();

  void addConsumerObserver(ConsumerObserver observer);

  void removeConsumerObserver(ConsumerObserver observer);

}