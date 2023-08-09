package recipes.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import recipes.account.Account;
import recipes.account.repository.AccountRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomBasicAuthenticationEntrypoint extends BasicAuthenticationEntryPoint {
	@Autowired
	AccountRepository accountRepository;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
											 AuthenticationException exception) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		String email = "";
		Account account;
		if (request.getHeader("authorization") != null) {
			String enteredCredentialsString = new String(Base64.getDecoder().decode(request.getHeader("authorization").substring(6).getBytes()));
			email = enteredCredentialsString.substring(0, enteredCredentialsString.indexOf(":"));
		}
		account = accountRepository.findByEmail(email);

		String message = "";
		if(account == null) {
			message = "You are not authorized to access this endpoint";
		}
		final Map<String, Object> body = new HashMap<>();
		body.put("timestamp", String.valueOf(LocalDateTime.now()));
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error", "Unauthorized");
		body.put("message", message);
		body.put("path", request.getRequestURI());


		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}

	@Override
	public void afterPropertiesSet() {
		setRealmName("Recipes");
		super.afterPropertiesSet();
	}
}
