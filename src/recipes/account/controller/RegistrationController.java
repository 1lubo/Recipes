package recipes.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.account.Account;
import recipes.account.UserDetailsServiceImpl;
import recipes.security.exceptions.AccountValidationException;
import recipes.security.exceptions.UserAlreadyExistsException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {
	@Autowired
	UserDetailsServiceImpl accountDetailsService;

	@PostMapping("api/register")
	public ResponseEntity<Object> register(@RequestBody @Valid Account account, BindingResult errors) {
		if (errors.hasErrors()) {
			throw new AccountValidationException(errors.getFieldErrors().get(0).getDefaultMessage());
		}

		Account alreadyExists = accountDetailsService.findByUsername(account.getEmail().toLowerCase());
		if(alreadyExists == null){
			account.setEmail(account.getEmail().toLowerCase());
			account.setPassword(new BCryptPasswordEncoder(13).encode(account.getPassword()));
			accountDetailsService.save(account);
			final Map<String, Object> body = new HashMap<>();
			body.put("result", "OK");
			return new ResponseEntity<>(body, HttpStatus.OK);
		} else {
			throw new UserAlreadyExistsException("User already exist!");
		}
	}
}
