package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GetUserPrincipalUC getUserPrincipalUC;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserPrincipalUC.getByUsername(username)
                .map(UserPrincipal::fromSnapshot)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " doesn't exist"));
    }

    @Data
    @Builder
    public static class UserPrincipal implements UserDetails {

        private final String id;
        private final String username;
        private final String password;
        private final RoleEnum role;

        static UserPrincipal fromSnapshot(UserPrincipalSnapshot snap) {
            return new UserPrincipal(
                    snap.externalId(),
                    snap.username(),
                    snap.password(),
                    snap.role());
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
