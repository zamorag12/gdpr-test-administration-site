package com.gdpr.unir.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String SQL_ROLE
            = "select u.username, p.permission_value as authority "
            + "from gdpr.c_security_user u "
            + "inner join gdpr.c_security_role r on u.id_role = r.id "
            + "inner join gdpr.c_security_role_permission rp on rp.id_role = r.id "
            + "inner join gdpr.c_security_permission p on rp.id_permission = p.id "
            + "where u.username = ?";

    private static final String SQL_LOGIN
            = "select u.username as username,p.password as password, active "
            + "from gdpr.c_security_user u "
            + "inner join gdpr.c_security_user_password p on p.id_user = u.id "
            + "where username = ?";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl userDetails = new JdbcDaoImpl();
        userDetails.setDataSource(dataSource);
        userDetails.setUsersByUsernameQuery(SQL_LOGIN);
        userDetails.setAuthoritiesByUsernameQuery(SQL_ROLE);
        return userDetails;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/forgot_password/**").permitAll()
                .antMatchers("/reset_password/**").permitAll()

                .antMatchers("/admin/**").hasAnyRole("ADMIN_ALL")

                .antMatchers("/users/**").hasAnyRole("MASTER_CITY")

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
