package com.emprovise.service.authorizationserver.service;

import com.emprovise.service.authorizationserver.domain.User;
import com.emprovise.service.authorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername( username );
    }

    public User register(User user) throws AccountException {
        if ( userRepository.countByUsername( user.getUsername() ) == 0 ) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new AccountException(String.format("Username [%s] already taken.", user.getUsername()));
        }
    }

    /**
     * @Transactional annotation must be added to successfully remove the date.
     */
    @Transactional
    public void removeUserAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername( username );

        if(user != null) {
            userRepository.delete(user);
        }
    }
}
