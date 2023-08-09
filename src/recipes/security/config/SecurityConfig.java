package recipes.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import recipes.security.authentication.CustomBasicAuthenticationEntrypoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomBasicAuthenticationEntrypoint authenticationEntrypoint;
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.debug(false)
			.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic()
			.authenticationEntryPoint(authenticationEntrypoint);
		httpSecurity
			.csrf().disable().headers().frameOptions().disable()
			.and()
			.authorizeHttpRequests( auth -> {
				auth.antMatchers(HttpMethod.POST, "/actuator/**" ).permitAll();
				auth.antMatchers(HttpMethod.POST,"/api/register").permitAll();
				auth.anyRequest().authenticated();
			})
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			return httpSecurity.build();
	}

}
