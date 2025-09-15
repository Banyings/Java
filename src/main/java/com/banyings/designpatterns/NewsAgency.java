package com.banyings.designpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject - News Agency
 */
public class NewsAgency implements NewsSubject {
    private List<NewsObserver> observers;
    private String latestHeadline;
    private String latestContent;
    
    public NewsAgency() {
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void addObserver(NewsObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(NewsObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (NewsObserver observer : observers) {
            observer.update(latestHeadline, latestContent);
        }
    }
    
    public void publishNews(String headline, String content) {
        this.latestHeadline = headline;
        this.latestContent = content;
        notifyObservers();
    }
    
    public int getObserverCount() {
        return observers.size();
    }
}