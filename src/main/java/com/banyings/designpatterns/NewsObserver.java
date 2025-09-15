package com.banyings.designpatterns;

/**
 * Observer interface
 */
public interface NewsObserver {
    void update(String headline, String content);
}