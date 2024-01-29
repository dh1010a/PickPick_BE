package com.pickpick.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickpick.server.filter.JsonUsernamePasswordAuthenticationFilter;
import com.pickpick.server.filter.JwtAuthenticationProcessingFilter;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.security.UserDetailsServiceImpl;
import com.pickpick.server.security.handler.LoginFailureHandler;
import com.pickpick.server.security.handler.LoginSuccessJWTProvideHandler;
import com.pickpick.server.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final ObjectMapper objectMapper;
	private final UsersRepository usersRepository;
	private final JwtService jwtService;

	 // 스프링 시큐리티 기능 비활성화
//	@Bean
//	public WebSecurityCustomizer configure() {
//		return (web -> web.ignoring()
//				.requestMatchers(toH2Console())
////				.requestMatchers("/static/**")
//		);
//	}

	// 특정 HTTP 요청에 대한 웹 기반 보안 구성
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http	.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/user/signup", "/", "/login", "/album/init").permitAll()
						.anyRequest().authenticated())
//				.formLogin(formLogin -> formLogin
//						.loginPage("/login")
//						.defaultSuccessUrl("/home"))
				.logout((logout) -> logout
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true))
				.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		http
				.addFilterAfter(jsonUsernamePasswordLoginFilter(), LogoutFilter.class) // 추가 : 커스터마이징 된 필터를 SpringSecurityFilterChain에 등록
				.addFilterBefore(jwtAuthenticationProcessingFilter(), JsonUsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	// 인증 관리자 관련 설정
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {//2 - AuthenticationManager 등록
		DaoAuthenticationProvider provider = daoAuthenticationProvider();//DaoAuthenticationProvider 사용
		return new ProviderManager(provider);
	}

    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
        return new LoginSuccessJWTProvideHandler(jwtService, usersRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

	@Bean
	public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
		JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
		jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
		jsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
		jsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
		return jsonUsernamePasswordLoginFilter;
	}

	@Bean
	public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
		JwtAuthenticationProcessingFilter jsonUsernamePasswordLoginFilter = new JwtAuthenticationProcessingFilter(jwtService, usersRepository);

		return jsonUsernamePasswordLoginFilter;
	}

//	@Bean
//	public WebSecurityCustomizer configure() {
//		return (web -> web.ignoring()
//			.requestMatchers(toH2Console())
//		);
//	}
}
