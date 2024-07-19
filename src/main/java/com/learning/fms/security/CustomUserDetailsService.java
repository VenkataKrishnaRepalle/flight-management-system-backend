package com.learning.fms.security;

import com.learning.fms.repository.UserRepository;

import com.learning.fms.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        var userOptional = userRepository.findById(UUID.fromString(uuid));
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var user = userOptional.get();
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole.getRole()))
                .collect(Collectors.toSet());

        return new User(user.getUuid().toString(), user.getPassword(), authorities);
    }
}
