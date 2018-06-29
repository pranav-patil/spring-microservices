package com.emprovise.service.authorizationserver.domain;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;

    private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

    public UserAccount() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void grantAuthority(String authority) {
        if ( authorities == null ) authorities = new HashSet<>();
        authorities.add(authority);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        authorities.forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority)));
        return grantedAuthorities;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void addRoles(String... roles) {

        if(this.roles == null) {
            this.roles = new HashSet<>();
        }

        if(roles != null) {
            CollectionUtils.addAll(this.roles, roles);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
