package jp.co.nss.ojt2024.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import jp.co.nss.ojt2024.example.model.CustomUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUser foundUser = userService.findByUsername(username);

        if (foundUser != null) {
            UserBuilder builder = User.withUsername(foundUser.getUsername());
            builder.password("{noop}" + foundUser.getPassword());
            builder.roles("USER");
            return builder.build();
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

}
