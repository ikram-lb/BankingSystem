//package Security;
//
//import javax.crypto.spec.SecretKeySpec;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import com.nimbusds.jose.jwk.source.ImmutableSecret;
//
////import lombok.Value;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled=true)
//public class  SecurityConfig {
//	@Value("${jwt.secret}")
//	private String secretkey;
////	@Bean
//	//Inmemory authentification
//	//UserDetailsService
////	public InMemoryUserDetailsManager inMemoryUserDetailsManager()
////	 {  PasswordEncoder passCoder=passwordEncoder();
////		//Password Encoder
////		return new InMemoryUserDetailsManager(
////		User.withUsername("user1").password(passCoder.encode("1234")).authorities("USER").build(),
////		User.withUsername("ADMIN").password(passCoder.encode("1234")).authorities("USER","ADMIN").build()
////	   );
//
//		 @Bean
//		 public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
//		 String encodedPassword = passwordEncoder.encode("1234");
//		 System.out.println(encodedPassword);
//		 return new InMemoryUserDetailsManager(
//				 User.withUsername("user1").password(encodedPassword).roles("USER").build(),
//				 User.withUsername("user2").password(encodedPassword).roles("USER").build(),
//				 User.withUsername("admin").password(encodedPassword).roles("USER","ADMIN").build()
//		 );
//
//
//	 }
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	//Authentification stateless
////	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////	    return httpSecurity
////	        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////	        .csrf(csrf -> csrf.disable())
////	        .cors(Customizer.withDefaults())
////	        .authorizeHttpRequests(ar -> ar.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll())
////	        .authorizeHttpRequests(ar -> ar.requestMatchers("/auth/login/**").permitAll()) // Update this line
////	        .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
////	        .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
////	        .build();
////	        .formLogin(withDefaults()) // Add this line if you want to enable form-based login
////	        .httpBasic(withDefaults()); // Add this line if you want to enable HTTP Basic authentication
////	}
//
////	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////	    return httpSecurity
////	        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////	        .csrf(csrf -> csrf.disable())
////	        .cors(Customizer.withDefaults())
//////	        .authorizeHttpRequests(ar -> ar.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll())
////	        .authorizeHttpRequests(ar -> ar.requestMatchers("/auth/login/**").permitAll()) // Updated line
////	        .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
////	        .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
////	        .build();
////	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity
//				.formLogin(Customizer.withDefaults())
//				.authorizeHttpRequests(ar -> ar.requestMatchers("/deleteclient/**").hasRole("ADMIN"))
//				.authorizeHttpRequests(ar -> ar.requestMatchers("/admin/**").hasRole("ADMIN"))
//				.authorizeHttpRequests(ar -> ar.requestMatchers("/user/**").hasRole("USER"))
//				.authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
//				.build();
//	}
//
//		//Authentification stateless
////	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////		return httpSecurity
////				.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .csrf(csrf->csrf.disable())
////                 .cors(Customizer.withDefaults())
////                .authorizeHttpRequests(ar->ar.requestMatchers("/login/**").permitAll())
////                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
////                .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults()))
////                .build();
////	}
//
//		// Algorithme symetrique: one secret Key(64 character)
//		// Algorithme Asymetrique: two secret Key(public key +private key)
//
//		@Bean
//		JwtEncoder jwtEncoder () {
////		String secretkey="5Kj9PcQxRv2S1WzL0aFhX3DnM6GtUyYbO8iVlNpAeQsJfTgHdZ";
//			return new NimbusJwtEncoder(new ImmutableSecret<>(secretkey.getBytes()));
//		}
//
//		@Bean
//		JwtDecoder jwtDecoder () {
////		String secretkey="5Kj9PcQxRv2S1WzL0aFhX3DnM6GtUyYbO8iVlNpAeQsJfTgHdZ";
//			SecretKeySpec secretKeySpec = new SecretKeySpec(secretkey.getBytes(), "RSA");
//			return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
//		}
//		@Bean
//		public AuthenticationManager authenticationManager (UserDetailsService userDetailsService){
//
//			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//			daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//			daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//			return new ProviderManager(daoAuthenticationProvider);
//		}
//	}
////	@Bean
////	public CorsFilter corsFilter() {
////	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////	    CorsConfiguration config = new CorsConfiguration();
////	    config.setAllowCredentials(true);
////	    config.addAllowedOrigin("*");
////	    config.addAllowedHeader("*");
////	    config.addAllowedMethod("*"); // Allow all HTTP methods
////	    source.registerCorsConfiguration("/**", config);
////	    return new CorsFilter(source);
////	}
//
//
////	CorsConfigurationSource corsConfigurationSource(){
////		CorsConfiguration corsConfiguration=new CorsConfiguration();
////		corsConfiguration.addAllowedOrigin("*");
////		corsConfiguration.addAllowedMethod("*");
////		corsConfiguration.addAllowedHeader("*");
////		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////		source.registerCorsConfiguration("/**", corsConfiguration);
////		return source;
////	}
//
////	@Bean
////	public FilterRegistrationBean<CorsFilter> corsFilter() {
////	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////	    CorsConfiguration config = new CorsConfiguration();
////	    config.setAllowCredentials(true);
////	    config.addAllowedOrigin("*");
////	    config.addAllowedHeader("*");
////	    config.addAllowedMethod("*");
////	    source.registerCorsConfiguration("/**", config);
////
////	    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
////	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
////	    return bean;
////	}
//
//
//
