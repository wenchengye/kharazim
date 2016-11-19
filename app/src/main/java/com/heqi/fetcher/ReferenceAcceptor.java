package com.heqi.fetcher;

/**
 * Interface to indicate the class can accept and keep strong reference to any given target
 * instance.
 */
public interface ReferenceAcceptor {
  void onReferenceAccepted(Object target);
}
