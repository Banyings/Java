package com.banyings.designpatterns;

/**
 * Concrete Observer - News Channel
 */
public class NewsChannel implements NewsObserver {
    private String channelName;
    private String lastHeadline;
    
    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }
    
    @Override
    public void update(String headline, String content) {
        this.lastHeadline = headline;
        System.out.printf("[%s] Breaking News: %s%n", channelName, headline);
        System.out.printf("[%s] %s%n%n", channelName, content);
    }
    
    public String getChannelName() { return channelName; }
    public String getLastHeadline() { return lastHeadline; }
}