package com.heqi.kharazim;

import android.content.Context;
import android.os.Debug;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.heqi.kharazim.utils.KharazimUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by overspark on 2017/4/19.
 */

@RunWith(AndroidJUnit4.class)
public class KharazimEncodeTest {

  private static final String TAG = KharazimEncodeTest.class.getSimpleName();

  @Test
  public void simpleEncode() throws Exception {
    Log.d(TAG, KharazimUtils.kharazimEncode("123"));
    assertEquals(KharazimUtils.kharazimEncode("123"), "5e4e0ac941daf16d70273eaa58996dac");
  }
}
