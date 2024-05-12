//package Security;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwsHeader;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/auth")
//public class configController {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private JwtEncoder  jwtEncoder;
//
//	@GetMapping("/profile")
//	public Authentication authentication(Authentication authentication) {
//
//		System.out.println("i'm running");
//		return authentication;
//	}
//
//	//jwt access token
//
//	@PostMapping("/login")
//	public Map<String, String> login(String username, String password) {
//	    // Your login logic here
//		System.out.println("i'm running");
//		Authentication authentication=authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(username,password)
//			);
//		//generate jwt
//		//claims
//		Instant instant =Instant.now();
//		String scope = authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
//		JwtClaimsSet jwtClaimSet=JwtClaimsSet.builder()
//		.issuedAt(instant)
//		.expiresAt(instant.plus(10,ChronoUnit.MINUTES))
//		.subject(username)
//		.claim("scope",scope)
//		.build();
//		//Algorithm H mac pour decoder
//		JwtEncoderParameters jwtEncoderParameters=
//				JwtEncoderParameters.from(
//						JwsHeader.with(MacAlgorithm.HS512).build(),
//						jwtClaimSet
//						);
//		String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
//		System.out.println("i'm running");
//		return Map.of("access-token",jwt);
// //		return Map.of("access-token",jwt.getTokenValue());
//
//	}
//}
