package com.kspt.buro.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Checks implements GrantedAuthority {
    CHECK, VERIFIED, PAID;

    @Override
    public String getAuthority() {
        return name();
    }
}
