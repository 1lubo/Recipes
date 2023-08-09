package recipes.account;

import recipes.recipeData.Recipe;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
	static final String EMAIL_REGEX = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "Email must not be blank")
	@Email(message = "Valid email required", regexp = EMAIL_REGEX)
	private String email;
	@NotBlank
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;

	@OneToMany(mappedBy = "account")
	private List<Recipe> recipes = new ArrayList<>();

	public Account(){}

	public Long getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public List<Recipe> getRecipes() { return recipes;}
}
