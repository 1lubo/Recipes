package recipes.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account save(Account account);
	Account findByEmail(String email);
	Account findById(long id);
}
