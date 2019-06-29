package com.timsimonhughes.atlas.model;

import java.io.Serializable;
import java.nio.channels.Channel;

public class NewsFeed implements Serializable {

    private Channel channel;

    public NewsFeed() {
    }

    public Channel getChannel() {
        return channel;
    }

    public NewsFeed(Channel channel) {
        this.channel = channel;
    }
}
