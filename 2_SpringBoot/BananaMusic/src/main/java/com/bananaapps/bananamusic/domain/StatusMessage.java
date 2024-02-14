package com.bananaapps.bananamusic.domain;


public class StatusMessage {

    private Integer status;
    private String message;

    public StatusMessage() {
    }

    public StatusMessage(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StatusMessage{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}