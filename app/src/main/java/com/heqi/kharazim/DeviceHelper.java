package com.heqi.kharazim;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by wenchengye on 16/8/28.
 */
public class DeviceHelper {

  public static final String FILE_ROOT = "heqi_kharazim";

  public enum KHARAZIM_DIR {
    CRASH;
  }

  private DeviceHelper() {
  }

  public static String getFileRootDirectory() {
    try {
      if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        return null;
      }
    } catch (Exception e) {
      // Catch exception is trying to fix a crash inside of Environment.getExternalStorageState().
      e.printStackTrace();
      return null;
    }
    StringBuilder stringBuilder = new StringBuilder()
        .append(Environment.getExternalStorageDirectory().getAbsolutePath())
        .append("/")
        .append(FILE_ROOT)
        .append("/");
    String rootDir = stringBuilder.toString();
    File file = new File(rootDir);
    if (!file.exists()) {
      if (!file.mkdirs()) {
        return null;
      }
    }
    return rootDir;
  }

  public static String getExternalFileDirectory(KHARAZIM_DIR dir) {
    String rootDir = getFileRootDirectory();
    if (!TextUtils.isEmpty(rootDir)) {
      StringBuilder stringBuilder = new StringBuilder()
          .append(rootDir)
          .append(dir.name().toLowerCase())
          .append("/");
      String content = stringBuilder.toString();
      File contentFile = new File(content);
      if (!contentFile.exists()) {
        if (!contentFile.mkdirs()) {
          return null;
        }
      }
      return content;
    } else {
      return null;
    }
  }
}
