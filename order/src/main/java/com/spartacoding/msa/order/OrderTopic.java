package com.spartacoding.msa.order;

// TODO topic이 많아지면 enum으로 관리하기도 합니다

public enum OrderTopic {

    CREATED("order-created"),
    COMPLETE("order-completed");

    private final String topic;

    OrderTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
