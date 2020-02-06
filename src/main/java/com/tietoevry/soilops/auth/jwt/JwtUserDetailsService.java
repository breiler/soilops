package com.tietoevry.soilops.auth.jwt;

import com.tietoevry.soilops.model.User;
import com.tietoevry.soilops.auth.UserDetails;
import com.tietoevry.soilops.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new UserDetails(user.getUsername(), user.getPassword(), Collections.emptyList(), null);
    }

    public UserDetails loadUserByUsername(Claims claims) {
        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            roles = Collections.emptyList();
        }

        String thingUuid = claims.get("thing", String.class);

        User user = userRepository.findByUsername(username);
        Collection<GrantedAuthority> authorityCollection = roles.stream()
                .map(role -> {
                    if (!role.startsWith("ROLE_")) {
                        role = "ROLE_" + role;
                    }
                    return role;
                })
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserDetails(user.getUsername(), user.getPassword(), authorityCollection, thingUuid);
    }
}