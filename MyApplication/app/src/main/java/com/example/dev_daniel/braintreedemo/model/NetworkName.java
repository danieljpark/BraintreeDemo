package com.example.dev_daniel.braintreedemo.model;

/**
 * Created by dev_d on 3/1/15.
 */
public enum NetworkName {
    AMEX("Amex"), DISCOVER("Discover"), JCB("JCB"), MASTERCARD("Mastercard"), VISA("Visa");

    private String friendlyName;

    private NetworkName(String s) {
        friendlyName = s;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
