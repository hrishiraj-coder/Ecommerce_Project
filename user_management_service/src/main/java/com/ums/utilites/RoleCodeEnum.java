package com.ums.utilites;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleCodeEnum {
    CUSTOMER("C"),
    TECHNICIAN("T"),
    STORE_ADMIN("SA"),
    CSR("CSR"),
    STORE_STAFF("SS");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }
}
