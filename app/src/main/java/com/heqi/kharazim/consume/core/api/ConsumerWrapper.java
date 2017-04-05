package com.heqi.kharazim.consume.core.api;


import com.heqi.kharazim.consume.model.ConsumeCourseRecord;

/**
 * Created by overspark on 2016/12/28.
 */

public interface ConsumerWrapper extends Consumer {

  State getState();

  void addConsumerObserver(ConsumerObserver observer);

  void removeConsumerObserver(ConsumerObserver observer);

  ConsumeCourseRecord getRecord();

}
