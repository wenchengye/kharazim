package com.heqi.kharazim;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by wenchengye on 16/8/28.
 */
public class KharazimUncaughtExceptionHandler implements UncaughtExceptionHandler {
  private static final String LAST_CRASH_LOG_FILE = "last_crash_log.txt";

  private final UncaughtExceptionHandler defaultUEH;
  private final Context context;

  public KharazimUncaughtExceptionHandler(Context context) {
    this.context = context;
    this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
  }

  /**
   * report crash log to server end.
   *
   * @param ex, thrown exception.
   */
  public static void reportCrashDetail(Throwable ex) {
    if (ex != null) {
      StringWriter strWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(strWriter));
      String detail = strWriter.toString();
      detail = detail.replace("\n", "#");
      detail = detail.replace("\t", "#");
      // TODO report detail 2 server
    }
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    saveCrashLog(ex);
    reportCrashDetail(ex);
    this.defaultUEH.uncaughtException(thread, ex);
  }

  /**
   * save exception log to SdCard.
   *
   * @param ex, thrown exception.
   */
  private void saveCrashLog(Throwable ex) {
    if (ex != null) {
      try {
        String dir = DeviceHelper.getExternalFileDirectory(
            DeviceHelper.KHARAZIM_DIR.CRASH);
        PrintStream stream = new PrintStream(dir + LAST_CRASH_LOG_FILE);
        ex.printStackTrace(stream);
        stream.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
