package com.heqi.kharazim;

import android.support.test.runner.AndroidJUnit4;

import com.heqi.kharazim.archives.model.HealthConditionResult;
import com.wandoujia.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by overspark on 2017/4/30.
 */

@RunWith(AndroidJUnit4.class)
public class GsonTest {

  @Test
  public void testNull() throws Exception {
    HealthConditionResult obj = new HealthConditionResult();
    Gson gson = new Gson();
    HealthConditionResult obj2 = gson.fromJson(gson.toJson(obj), HealthConditionResult.class);
    assertEquals(obj2.getData_src(), null);
  }
}
