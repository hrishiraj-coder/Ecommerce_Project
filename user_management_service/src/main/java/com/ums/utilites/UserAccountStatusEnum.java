package com.ums.utilites;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAccountStatusEnum {

    REGISTERED("R"),
    ACTIVE("A"),
    LOCKED("L"),
    DISABLED("D");

    private final String name;

    @Override
    public String toString() {
        return super.toString();
    }
}
