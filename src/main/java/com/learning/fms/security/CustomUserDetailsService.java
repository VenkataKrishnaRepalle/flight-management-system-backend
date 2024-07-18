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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        var userRoles = userRoleRepository.getByUser_Uuid(user.getUuid());

        Set<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole.getRole()))
                .collect(Collectors.toSet());

        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
