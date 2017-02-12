package com.yogyagroup.auditap.data;

import com.yogyagroup.auditap.model.User;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class UserCallback {
    User user;
    String message;
    boolean success;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
