package org.arjunaoverdrive.newsapp.security;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.dao.UserRepository;
import org.arjunaoverdrive.newsapp.exception.UserNotFoundException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFoundException(
                        MessageFormat.format("User with username {0} not found.", username)
                )
        );
        return new AppUserDetails(user);
    }
}
