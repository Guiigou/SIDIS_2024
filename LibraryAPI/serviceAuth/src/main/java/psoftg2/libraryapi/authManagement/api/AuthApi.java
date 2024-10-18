package psoftg2.libraryapi.authManagement.api;

import org.springframework.web.bind.annotation.*;
import psoftg2.libraryapi.userManagement.api.UserView;
import psoftg2.libraryapi.userManagement.api.UserViewMapper;
import psoftg2.libraryapi.userManagement.model.User;
import psoftg2.libraryapi.userManagement.services.CreateUserRequest;
import psoftg2.libraryapi.userManagement.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.Jwt;
import psoftg2.libraryapi.configuration.SecurityConfig;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/auth")
public class AuthApi {

	private final AuthenticationManager authenticationManager;

	private final JwtEncoder jwtEncoder;

	private final JwtDecoder jwtDecoder;

	private final UserViewMapper userViewMapper;

	private final UserService userService;

	@PostMapping("login")
	public ResponseEntity<UserView> login(@RequestBody @Valid final AuthRequest request) {
		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			final User user = (User) authentication.getPrincipal();

			final Instant now = Instant.now();
			//final long expiry = 1200L; // 2 minutes
			final long expiry = 2592000L; // 1 month is usually too long for a token to be valid. adjust for production
			final String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(joining(" "));

			final JwtClaimsSet claims = JwtClaimsSet.builder().issuer("example.io").issuedAt(now)
					.expiresAt(now.plusSeconds(expiry)).subject(format("%s,%s", user.getId(), user.getUsername()))
					.claim("roles", scope).build();

			final String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(userViewMapper.toUserView(user));
		} catch (final BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("register")
	public UserView register(@RequestBody @Valid final CreateUserRequest request) {
		final var user = userService.create(request);
		return userViewMapper.toUserView(user);
	}

	@GetMapping("/roles")
	public ResponseEntity<List<String>> getUserRoles(@RequestHeader("Authorization") String authorization) {
		String token = authorization.replace("Bearer ", "");

		Jwt jwt = jwtDecoder.decode(token);
		String username = jwt.getClaimAsString("sub");

		User user = userService.loadUserByUsername(username); // Busca o utilizador pelo username
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		List<String> roles = user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(roles);
	}

}
