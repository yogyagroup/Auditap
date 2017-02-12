package com.yogyagroup.auditap.data;

import com.yogyagroup.auditap.model.Receipt;

import java.util.ArrayList;

/**
 * Created by jimmy <jimmy.chandra@yogyagroup.com> on 12-02-2017.
 */

public class ReceiptCallback {
    private String message;
    private ArrayList<Receipt> receipts;
    private boolean success;

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(ArrayList<Receipt> receipts) {
        this.receipts = receipts;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
