package com.joje.dbee.common.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.joje.dbee.common.security.JwtAccessDeniedHandler;
import com.joje.dbee.common.security.JwtAuthenticationEntryPoint;
import com.joje.dbee.repository.account.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtSecurityConfig jwtSecurityConfig;
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/assets/**", "/favicon.ico");
	}
	
//	 권한 계층 구조 설정
	@Bean
	public RoleHierarchyImpl roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_ROOT > ROLE_ADMIN > ROLE_USER");
		return roleHierarchyImpl;
	}
	
//	권한 계층 구조 등록
	@Bean
	public AccessDecisionVoter<? extends Object> roleVoter(){
		RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
		return roleHierarchyVoter;
	}
	
//  시큐리티 설정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.antMatchers("/dbee/user/**").hasRole("USER")
//				.antMatchers("/dbee/admin/**").hasRole("ADMIN")
//				.anyRequest().permitAll()
//				.and()
//			.formLogin()
//				.loginPage("/dbee/account/login")
//				.defaultSuccessUrl("/dbee")
//				.permitAll()
//				.and()
//			.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/dbee/account/logout"))
//				.logoutSuccessUrl("/dbee/account/login")
//				.permitAll();
//				.and()
//			.csrf().disable();

		 http
         // token을 사용하는 방식이기 때문에 csrf를 disable
         	.csrf().disable()

         // Exception을 핸들링할 때 직접 만든 클래스를 추가
         	.exceptionHandling()
         	.authenticationEntryPoint(jwtAuthenticationEntryPoint)
         	.accessDeniedHandler(jwtAccessDeniedHandler)

         // enable h2-console
//         	.and()
//         	.headers()
//         	.frameOptions()
//         	.sameOrigin()

         // 세션을 사용하지 않기 때문에 STATELESS로 설정
         	.and()
         	.sessionManagement()
         	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

         	.and()
         	.authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
         	.antMatchers("/dbee/account/login").permitAll()
         	.antMatchers("/dbee/account/signup").permitAll()

         	.anyRequest().authenticated() // 나머지는 인증 필요

         // JwtSecurityConfig 클래스 적용
         .and()
         .apply(jwtSecurityConfig);
			
		
	}
	
//	시큐리티 디비 쿼리
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	    auth.jdbcAuthentication()
//	      .dataSource(dataSource)
//	      .passwordEncoder(passwordEncoder())
//	      .usersByUsernameQuery("select user_id, password, enabled "
//	    		  			  + "from tb_user "
//	    		  			  + "where user_id = ?")
//	      .authoritiesByUsernameQuery("select u.user_id, r.role_name "
//	    		  					+ "from tb_user_role ur inner join tb_user u on ur.user_no = u.user_no "
//	    		  					+ "inner join tb_role r on ur.role_id = r.role_id "
//	    		  					+ "where u.user_id = ?");
//	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	//  패스워드 인코더
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
