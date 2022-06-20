package com.joje.dbee.common.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
//	시큐리티에서 데디터베이스 소스 접근
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(WebSecurity web) {
//		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		web.ignoring().antMatchers("/assets/**");
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
		http
			.authorizeRequests()
				.antMatchers("/dbee/user/**").hasRole("USER")
				.antMatchers("/dbee/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/dbee/account/login")
				.defaultSuccessUrl("/dbee")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/dbee/account/logout"))
				.logoutSuccessUrl("/dbee/account/login")
				.permitAll();
//				.and()
//			.csrf().disable();
	}
	
//	시큐리티 디비 쿼리
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .passwordEncoder(passwordEncoder())
	      .usersByUsernameQuery("select user_id, password, enabled "
	    		  			  + "from tb_user "
	    		  			  + "where user_id = ?")
	      .authoritiesByUsernameQuery("select u.user_id, r.role_name "
	    		  					+ "from tb_user_role ur inner join tb_user u on ur.user_no = u.user_no "
	    		  					+ "inner join tb_role r on ur.role_id = r.role_id "
	    		  					+ "where u.user_id = ?");
	}
	
	//  패스워드 인코더
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
