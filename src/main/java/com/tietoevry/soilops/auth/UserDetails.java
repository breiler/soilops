package com.tietoevry.soilops.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetails extends User {
    private final String thingUuid;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String thingUuid) {
        super(username, password, authorities);
        this.thingUuid = thingUuid;
    }

    public String getThingUuid() {
        return thingUuid;
    }
}
