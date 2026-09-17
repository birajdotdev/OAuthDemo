package np.com.birajstha.oauthdemo.config;

import np.com.birajstha.oauthdemo.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain springSecurityFilterChain(
            HttpSecurity httpSecurity,
            CustomOidcUserService customOidcUserService
    ) {
        httpSecurity.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2.userInfoEndpoint(userInfo ->
                                        userInfo.oidcUserService(customOidcUserService)
                                )
                                .defaultSuccessUrl("/profile", true)
                );

        return httpSecurity.build();
    }
}
