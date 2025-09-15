package com.banyings.designpatterns;

/**
 * Subject interface
 */
public interface NewsSubject {
    void addObserver(NewsObserver observer);
    void removeObserver(NewsObserver observer);
    void notifyObservers();
}