package recipes.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.account.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email.toLowerCase());
		if(account == null){
			throw new UsernameNotFoundException("User not found: " + email);
		}
		return new UserDetailsImpl(account);
	}

	public Account findByUsername(String email) {
		return accountRepository.findByEmail(email);
	}
	public void save(Account account){ accountRepository.save(account);}

	public Account findById(long id) { return accountRepository.findById(id); }
}
