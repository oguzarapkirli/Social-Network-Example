package com.oguzarapkirli.socialnetworkexample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oguzarapkirli.socialnetworkexample.repository.UserRepository;
import com.oguzarapkirli.socialnetworkexample.util.converter.StringToPostTypeConverter;
import com.oguzarapkirli.socialnetworkexample.util.converter.StringToStoryTypeConverter;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
    private final UserRepository repository;
    private final RateLimiter rateLimiter;

    public ApplicationConfig(UserRepository repository, RateLimiterRegistry rateLimiterRegistry) {
        this.repository = repository;
        this.rateLimiter = rateLimiterRegistry.rateLimiter("globalRateLimiter");
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitingInterceptor(rateLimiter))
                .addPathPatterns("/**");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToPostTypeConverter());
        registry.addConverter(new StringToStoryTypeConverter());
    }

    private record RateLimitingInterceptor(RateLimiter rateLimiter) implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (rateLimiter.acquirePermission()) {
                return true;
            } else {
                HashMap<String, String> json = new HashMap<>();
                json.put("message", "Too many requests");
                json.put("status", String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()));
                json.put("hasError", String.valueOf(true));
                String jsonStr = new ObjectMapper().writeValueAsString(json);
                response.getWriter().write(jsonStr);
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json");
                return false;
            }
        }
    }
}
