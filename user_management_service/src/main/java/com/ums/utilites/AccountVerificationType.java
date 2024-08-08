package com.ums.utilites;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public enum AccountVerificationType {
    VERIFY_MOBILE("verify emailAddress"),
    VERIFY_EMAIL("verify mobileNo");


    private String name;

    @Override
    public String toString() {
        return this.name();
    }
}
