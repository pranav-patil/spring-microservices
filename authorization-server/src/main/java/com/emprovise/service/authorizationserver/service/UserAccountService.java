package com.emprovise.service.authorizationserver.service;

import com.emprovise.service.authorizationserver.domain.UserAccount;
import com.emprovise.service.authorizationserver.repository.UserAccountRepository;
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
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername( username );
    }

    public UserAccount findAccountByUsername(String username) {
        return userAccountRepository.findByUsername( username );
    }

    public UserAccount register(UserAccount userAccount) throws AccountException {
        if ( userAccountRepository.countByUsername( userAccount.getUsername() ) == 0 ) {
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            return userAccountRepository.save(userAccount);
        } else {
            throw new AccountException(String.format("Username [%s] already taken.", userAccount.getUsername()));
        }
    }

    /**
     * @Transactional annotation must be added to successfully remove the date.
     */
    @Transactional
    public void removeUserAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount userAccount = userAccountRepository.findByUsername( username );

        if(userAccount != null) {
            userAccountRepository.delete(userAccount);
        }
    }
}
