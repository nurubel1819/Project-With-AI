package com.example.Project_With_AI.auth.config;

import com.example.Project_With_AI.auth.security.CustomUserDetailsService;
import com.example.Project_With_AI.auth.security.RestAccessDeniedHandler;
import com.example.Project_With_AI.auth.security.RestAuthenticationEntryPoint;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Collection;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final RestAccessDeniedHandler restAccessDeniedHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.accessDeniedHandler(restAccessDeniedHandler)
			)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/api/auth/**",
					"/swagger-ui/**",
					"/swagger-ui.html",
					"/v3/api-docs/**"
				).permitAll()
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
			)
			.authenticationProvider(authenticationProvider())
		;

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
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

	@Bean
	public JwtDecoder jwtDecoder(@Value("${app.security.jwt.secret}") String jwtSecret) {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
		return NimbusJwtDecoder.withSecretKey(secretKey)
			.macAlgorithm(MacAlgorithm.HS256)
			.build();
	}

	@Bean
	public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
		return jwt -> {
			String role = jwt.getClaimAsString("role");
			Collection<GrantedAuthority> authorities = role == null
				? List.of()
				: List.of(new SimpleGrantedAuthority("ROLE_" + role));
			return new JwtAuthenticationToken(jwt, authorities);
		};
	}
}
