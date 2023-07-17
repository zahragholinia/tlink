package ir.tinyLink.security;

import ir.tinyLink.security.filter.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationProvider authenticationProvider;

    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().
                disable()
                .authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/link/redirect")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
