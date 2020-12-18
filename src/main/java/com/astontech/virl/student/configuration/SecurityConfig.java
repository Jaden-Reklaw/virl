package com.astontech.virl.student.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //region Fields/Properties
    @Value("${spring.security.authentication.method}")
    private String authenticatedMethod;

    @Value("${spring.security.ldap.domain}")
    private String ldapDomain;

    @Value("${spring.security.ldap.url}")
    private String ldapUrl;

    //constructor injection custom access handler
    CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    //endregion

    //region Base Security Configuration
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if(authenticatedMethod.equals("IN_MEMORY")) {
            auth.inMemoryAuthentication()
                    .withUser("mentor").password("{noop}123").roles("MENTOR")
                    .and()
                    .withUser("mentee").password("{noop}123").roles("MENTEE");
        } else if(authenticatedMethod.equals("LDAP")) {
            auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
        }
    }


    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider authenticationProvider =
                new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapUrl);

        authenticationProvider.setConvertSubErrorCodesToExceptions(true);
        authenticationProvider.setUseAuthenticationRequestCredentials(true);

        return authenticationProvider;
    }
    //endregion

    //region Access Control
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(authenticatedMethod.equals("NONE")) {
            http.authorizeRequests().antMatchers("/").permitAll();
        } else if(authenticatedMethod.equals("LDAP") || authenticatedMethod.equals("IN_MEMORY")) {
            http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login")
                    .successHandler(customSuccessHandler)
                    .permitAll()
                    .and()
                    .logout().logoutSuccessUrl("/");
        }

        //region ADVANCED SETTINGS
        http.csrf().disable();
        //endregion
    }
    //endregion
}
