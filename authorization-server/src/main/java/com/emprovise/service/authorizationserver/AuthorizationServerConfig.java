package com.emprovise.service.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.client.clientId}")
    private String resourceId;
    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Environment environment;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore());
    }

    /**
     * @see <a href="https://github.com/spring-projects/spring-security-oauth/issues/1222">Upgrading to spring-security@5 - PasswordEncoder</a>
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {

        client.inMemory()
                .withClient("browser")
                .authorizedGrantTypes("refresh_token", "password")
                .authorities("APPLICATION_CLIENT")
                .scopes("ui")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(10000)
                .and()
                .withClient("account-service")
                .secret(environment.getProperty("ACCOUNT_SERVICE_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .authorities("APPLICATION_CLIENT")
                .scopes("server")
                .resourceIds(resourceId)
                .and()
                .withClient("statistics-service")
                .secret(environment.getProperty("STATISTICS_SERVICE_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .authorities("APPLICATION_CLIENT")
                .scopes("server")
                .resourceIds("statistics-service")
                .and()
                .withClient("notification-service")
                .secret(environment.getProperty("NOTIFICATION_SERVICE_PASSWORD"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .authorities("APPLICATION_CLIENT")
                .scopes("server")
                .resourceIds("notification-service")
                .and()
                .withClient("register-app")
                .authorizedGrantTypes("client_credentials")
                .authorities("SECURITY_ADMIN")
                .scopes("read")
                .resourceIds(resourceId)
                .secret(clientSecret);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        ClassPathResource jwtResource = new ClassPathResource("keys/jwtkey.jks");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(jwtResource, "myPass123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }
}