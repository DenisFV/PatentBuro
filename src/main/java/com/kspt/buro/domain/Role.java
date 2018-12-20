package com.kspt.buro.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, VERIF;

    @Override
    public String getAuthority() {
        return name();
    }
}
