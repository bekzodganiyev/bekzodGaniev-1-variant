package uz.pdp.b7begzodganievexam.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.b7begzodganievexam.service.AuthService;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider getAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(getEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/book")
                .permitAll()
                .antMatchers("/api/book/add")
                .hasAnyRole("ADMIN","SUPER_ADMIN")
                .antMatchers("/api/book/**")
                .hasAnyRole("SUPER_ADMIN")
                .anyRequest()
                .authenticated()
                .and()

                .oauth2Login()
                .defaultSuccessUrl("/success-url-oauth", true)
                .failureUrl("/error")
                .loginPage("/login").permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/success-url",true)
                .failureUrl("/error")
                .loginPage("/login").permitAll()
                .and()
                .httpBasic();

    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("superAdmin")
//                .password("1")
//                .roles("SUPER_ADMIN")
//                .and()
//                .withUser("Admin")
//                .password("2")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("3")
//                .roles("USER")
//                .and()
//                .passwordEncoder(getEncoder());
//    }

//    @Bean
//    public PasswordEncoder getEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers("/api/book").permitAll()
//                .mvcMatchers()
//    }


}
