package com.yogyagroup.auditap.data;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class MessageCallback {
    private String message;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
