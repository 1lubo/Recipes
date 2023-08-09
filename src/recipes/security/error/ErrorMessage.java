package recipes.security.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
	LocalDateTime timestamp;
	int status;
	String error;
	String message;
	String path;
}
