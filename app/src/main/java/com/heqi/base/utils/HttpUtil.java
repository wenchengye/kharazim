package com.heqi.base.utils;

import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by overspark on 2017/3/11.
 */

public class HttpUtil {

  private static final String HTTP_URI_SCHEME = "http";
  private static final String HTTPS_URI_SCHEME = "https";

  private HttpUtil() {
  }

  public static boolean isHttpScheme(String scheme) {
    return scheme != null && (scheme.toLowerCase().startsWith(HTTP_URI_SCHEME)
        || scheme.toLowerCase().startsWith(HTTPS_URI_SCHEME));
  }

  public static String encodeUri(String url, String charset, boolean flag)
      throws UnsupportedEncodingException {
    int idx = url.indexOf('#');
    if (idx > 0) {
      url = url.substring(0, idx);
    }
    StringBuilder buffer = new StringBuilder();
    char[] block = null;
    int i;
    int last = 0;
    int size = url.length();
    int status = 0;
    boolean escharContained = false;// http://www.sogou.com/web?query=%B0%AE避免这类url中的%被encode
    if (!flag) {
      escharContained = true;
    }
    for (i = 0; i < size; i++) {
      String entity = null;
      char c = url.charAt(i);
      switch (c) {
        case '?':
          if (status != 1)
            status = 1;
          break;
        default:
          if ((c <= '9' && c >= '0') || (c <= 'z' && c >= 'a')
              || (c <= 'Z' && c >= 'A') || c == '-' || c == '_'
              || c == '.' || c == '!' || c == '~' || c == '*'
              || c == '\'' || c == '(' || c == ')' || c == ';'
              || c == '/' || c == ':' || c == '@' || c == '&'
              || c == '=' || c == '+' || c == '$' || c == ',') {
          } else {
            if (!escharContained && c != '%') {
              escharContained = true;
            }
            if (status == 0) {
              entity = Uri.encode(String.valueOf(c));
            } else if (status == 1) {
              entity = Uri.encode(String.valueOf(c));
            }
          }
          break;
      }
      if (entity != null) {
        if (block == null) {
          block = url.toCharArray();
        }
        buffer.append(block, last, i - last);
        buffer.append(entity);
        last = i + 1;
      }
    }
    if (last == 0) {
      return url;
    }
    if (flag && !escharContained) {
      return url;
    }
    if (last < size) {
      if (block == null) {
        block = url.toCharArray();
      }
      buffer.append(block, last, i - last);
    }

    return buffer.toString();
  }

  public static String MD5(String key) {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    return convertToHex(md5.digest(key.getBytes()));
  }

  private static String convertToHex(byte[] byteData) {
    Formatter formatter = new Formatter();
    for (byte b : byteData) {
      formatter.format("%02x", b);
    }
    String ret = formatter.out().toString();
    formatter.close();
    return ret;
  }
}
