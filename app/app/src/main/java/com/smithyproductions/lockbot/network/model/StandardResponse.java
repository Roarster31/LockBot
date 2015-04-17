package com.smithyproductions.lockbot.network.model;

/**
 * Created by rory on 16/04/15.
 */
public class StandardResponse {
    String message;
    String uuid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
