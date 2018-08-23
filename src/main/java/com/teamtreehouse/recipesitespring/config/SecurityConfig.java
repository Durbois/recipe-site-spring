package com.teamtreehouse.recipesitespring.config;

import com.teamtreehouse.recipesitespring.domain.User;
import com.teamtreehouse.recipesitespring.service.UserService;
import com.teamtreehouse.recipesitespring.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserService userService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService)
        .passwordEncoder(User.PASSWORD_ENCODER);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/assets/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/signup").permitAll()
        .antMatchers("/css/**", "/images/**", "/js/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .successHandler(loginSuccessHandler())
        .failureHandler(loginFailureHandler())
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/login")
        .and()
        .csrf().disable();

    http.headers().frameOptions().disable();
  }

  public AuthenticationSuccessHandler loginSuccessHandler() {
    return (request, response, authentication) -> response.sendRedirect("/profile");
  }

  public AuthenticationFailureHandler loginFailureHandler() {
    return (request, response, exception) -> {
      request.getSession().setAttribute("flash", new FlashMessage("Incorrect username and/or password. Please try again.", FlashMessage.Status.FAILURE));
      response.sendRedirect("/login");
    };
  }

  @Bean
  public EvaluationContextExtension securityExtension() {
    return new EvaluationContextExtensionSupport() {
      @Override
      public String getExtensionId() {
        return "security";
      }

      @Override
      public Object getRootObject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new SecurityExpressionRoot(authentication) {
        };
      }
    };
  }
}