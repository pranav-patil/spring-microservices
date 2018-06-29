package com.emprovise.service.authorizationserver.controller;


import com.emprovise.service.authorizationserver.domain.UserAccount;
import com.emprovise.service.authorizationserver.mapper.UserAccountMapper;
import com.emprovise.service.authorizationserver.model.UserBean;
import com.emprovise.service.authorizationserver.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.security.auth.login.AccountException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @see <a href="https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html">Expression-Based Access Control</a>
 */
@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;
    @Resource(name="tokenStore")
    private TokenStore tokenStore;
    @Resource(name="tokenServices")
    private ConsumerTokenServices tokenServices;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @PreAuthorize("hasAnyRole('SECURITY_ADMIN')")
    @GetMapping(path = "/info", produces = "application/json" )
    public UserBean userInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount account = userAccountService.findAccountByUsername(username);
        return userAccountMapper.mapToUserBean(account);
    }

    @PreAuthorize("hasAnyRole('SECURITY_ADMIN')")
    @GetMapping(path = "/details", produces = "application/json" )
    public Principal userDetails(Principal principal) {
        return principal;
    }

    @PreAuthorize("hasAuthority('SECURITY_REGISTER')")
    @PostMapping(path = "/register", produces = "application/json")
    public ResponseEntity<UserBean> registerUser(@RequestBody UserBean userBean) throws AccountException {
        UserAccount account = userAccountMapper.mapToAccount(userBean);
        account.addRoles("read", "write", "server");
        account.grantAuthority("SECURITY_ADMIN");
        account.grantAuthority("APPLICATION_CLIENT");
        account = userAccountService.register(account);
        return new ResponseEntity<>(userAccountMapper.mapToUserBean(account), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SECURITY_ADMIN')")
    @DeleteMapping(path = "/remove", produces = "application/json")
    public ResponseEntity<?> removeUser() {
        userAccountService.removeUserAccount();
        return new ResponseEntity<>("User removed.", HttpStatus.OK);
    }

    /**
     * @see <a href="http://www.baeldung.com/spring-security-oauth-revoke-tokens">Spring Security OAuth2 – Simple Token Revocation</a>
     * @param clientId
     * @return
     */
    @PreAuthorize("hasRole('SECURITY_ADMIN')")
    @GetMapping(path = "/tokens/{clientId}")
    public List<String> getTokens(@PathVariable String clientId) {
        List<String> tokenValues = new ArrayList<String>();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);
        if (tokens!=null){
            for (OAuth2AccessToken token:tokens){
                tokenValues.add(token.getValue());
            }
        }
        return tokenValues;
    }

    /**
     * @see <a href="http://www.baeldung.com/spring-security-oauth-revoke-tokens">Spring Security OAuth2 – Simple Token Revocation</a>
     */
    @PreAuthorize("hasRole('SECURITY_ADMIN')")
    @PostMapping(path = "/tokens/revoke/{tokenId:.*}")
    public String revokeToken(@PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
        return tokenId;
    }
}
